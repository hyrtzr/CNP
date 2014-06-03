package com.jingdong.common.http;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.jingdong.common.frame.IDestroyListener;
import com.jingdong.common.frame.MyActivity;
import com.jingdong.common.thread.ThreadPool;

import net.oschina.app.AppContext;
import net.oschina.app.AppException;
import net.oschina.app.api.ApiClient;
import net.oschina.app.bean.Entity;
import net.oschina.app.common.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求网络组辅助类
 * 
 * @author yepeng
 * 
 */
public abstract class HttpGroup implements IDestroyListener,MyActivity.PauseListener {

	private static int httpIdCounter = 0;
	// 请求完成数
	private int completesCount;
	// 请求总数
	protected int httpCount;
	private HttpGroupSetting httpGroupSetting;
	private OnGroupEndListener onGroupEndListener;
	private OnGroupErrorListener onGroupErrorListener;
	private OnGroupStartListener onGroupStartListener;
	private boolean isDestroy;
	protected int priority;
	protected int type;
	// 请求控制结束集合
	private List<StopController> stopControllers = new ArrayList<StopController>();

	public static class HttpGroupSync extends HttpGroup {

		public void execute(HttpRequest httprequest) {
		}

		public HttpGroupSync(HttpGroupSetting httpgroupsetting) {
			super(httpgroupsetting);
		}
	}

	public static interface CompleteListener {

		public abstract void onComplete(Bundle bundle);
	}

	public static interface HttpTaskListener {
	}

	public static interface OnAllListener extends OnStartListener,
			OnEndListener, OnErrorListener {
	}

	public static interface OnCommonListener extends OnEndListener,
			OnErrorListener {
	}

	public static interface OnEndListener extends HttpTaskListener {

		public abstract void onEnd(HttpResponse httpresponse);
	}

	public static interface OnErrorListener extends HttpTaskListener {

		public abstract void onError(HttpError httperror);
	}

	public static interface OnGroupEndListener {

		public abstract void onEnd();
	}

	public static interface OnGroupErrorListener {

		public abstract void onError();
	}

	public static interface OnGroupProgressListener {

		public abstract void onProgress(int i, int j);
	}

	public static interface OnGroupStartListener {

		public abstract void onStart();
	}

	public static interface OnReadyListener extends HttpTaskListener {

		public abstract void onReady(HttpSettingParams httpsettingparams);
	}

	public static interface OnStartListener extends HttpTaskListener {

		public abstract void onStart();
	}

	public static interface StopController {

		public abstract boolean isStop();

		public abstract void stop();
	}

	public static interface CustomOnAllListener extends OnAllListener {
	}

	static interface Handler {

		public abstract void run();
	}

	public void setOnGroupEndListener(OnGroupEndListener ongroupendlistener) {
		onGroupEndListener = ongroupendlistener;
	}

	public void setOnGroupErrorListener(
			OnGroupErrorListener ongrouperrorlistener) {
		onGroupErrorListener = ongrouperrorlistener;
	}

	public void setOnGroupStartListener(
			OnGroupStartListener ongroupstartlistener) {
		onGroupStartListener = ongroupstartlistener;
	}

	public static class HttpGroupaAsynPool extends HttpGroup {

		@Override
		public void execute(final HttpRequest httpRequest) {
			Runnable task = new Runnable() {

				public void run() {
					//每次请求时，检查ui是否繁忙，如果繁忙则停止所有子线程
					ThreadPool.sleepForUiThreadBusy();
					if (!httpRequest.isStop())
						httpRequest.noNeedConnectionHandler();
					if (httpRequest.isNeedConnection && !httpRequest.isStop()) {
						Runnable netTask = new Runnable() {

							public void run() {
								if (!httpRequest.isStop()) {
									if (httpCount < 1)
										onStart();
									httpCount = 1 + httpCount;
									httpRequest.needConnectionHandler();
									addCompletesCount();
								}
							}
						};
						if (httpRequest.getHttpSetting().isTopPriority())
							(new Thread(netTask)).start();
						else if (httpRequest.getHttpSetting().getPriority() == HttpGroupSetting.PRIORITY_JSON)
							ThreadPool.getSecondThreadPool().offerTask(netTask,
									httpRequest.getHttpSetting().getPriority());
						else
							ThreadPool.getThirdThreadPool().offerTask(netTask,
									httpRequest.getHttpSetting().getPriority());
					}
				}
			};
			if (httpRequest.getHttpSetting().isTopPriority())
				(new Thread(task)).start();
			else
				ThreadPool.getFirstThreadPool().offerTask(task,
						httpRequest.getHttpSetting().getPriority());
		}

		public HttpGroupaAsynPool(HttpGroupSetting httpgroupsetting) {
			super(httpgroupsetting);
		}
	}

	/**
	 * 处理请求数据的通用操作
	 * 
	 * @author yepeng
	 * 
	 */
	public class HttpRequest implements StopController {

		protected HttpSetting httpSetting;
		public boolean isNeedConnection = true;
		private boolean isStop;

		/**
		 * 请求本地的执行过程
		 */
		public void noNeedConnectionHandler() {
			switch (httpSetting.getType()) {
			case HttpGroupSetting.TYPE_JSON:
				Entity result;
				String key = httpSetting.getCacheKey();// 缓存标志
				if (httpSetting.getCacheMode() == HttpSetting.CACHE_MODE_ONLY_NET
						|| (isNetworkConnected() && (!isReadDataCache(key) || httpSetting
								.getCacheMode() == HttpSetting.CACHE_MODE_AUTO))) {
					isNeedConnection = true;
				} else if (httpSetting.getCacheMode() == HttpSetting.CACHE_MODE_ONLY_CACHE) {
					result = (Entity) readObject(key);
					isNeedConnection = false;
					callBack(result);
				}
				break;
			case HttpGroupSetting.TYPE_IMAGE:
				// 加载SD卡中的图片缓存
				String filename = FileUtils.getFileName(httpSetting.getFinalUrl());
				File file = new File(AppContext.getInstance().getCacheDir()+ File.separator + filename);
				if (httpSetting.getCacheMode() == HttpSetting.CACHE_MODE_ONLY_NET
						|| (isNetworkConnected() && (!file.exists() || httpSetting
								.getCacheMode() == HttpSetting.CACHE_MODE_AUTO))) {
					isNeedConnection = true;
				} else if (httpSetting.getCacheMode() == HttpSetting.CACHE_MODE_ONLY_CACHE) {
					isNeedConnection = false;
					callBack(file);
				}
			}
		}

		/**
		 * 请求网络的执行过程
		 */
		public void needConnectionHandler() {
			switch (httpSetting.getType()) {
			case HttpGroupSetting.TYPE_JSON:
				Entity result = null;
				String key = httpSetting.getCacheKey();// 缓存标志
				try {
					result = ApiClient.getDataByAutoType(this);
					if (result != null && !isStop() && !isDestroy) {
						result.setCacheKey(key);
						saveObject(result, key);
					} else if (!isStop() && isDestroy) {
						result = (Entity) readObject(key);
					}
					callBack(result);
				} catch (final AppException e) {
					if (!isStop() && isDestroy)
						result = (Entity) readObject(key);
					if (result == null) {
						exceptionHandler(e);
					} else {
						callBack(result);
					}
				}
				break;
			case HttpGroupSetting.TYPE_IMAGE:
				try {
					String filename = FileUtils.getFileName(httpSetting
							.getFinalUrl());
					File file = new File(AppContext.getInstance().getCacheDir()+ File.separator + filename);
					try {
						ApiClient.getImageFileByNetUrl(httpSetting.getFinalUrl(), file, this);
						callBack(file);
					} catch (Exception e) {
						e.printStackTrace();
						throw AppException.io(e);
					}
				} catch (AppException e) {
					exceptionHandler(e);
				}
				break;
			}
		}

		/**
		 * 发生异常时，调用此方法
		 * 
		 * @param e
		 */
		private void exceptionHandler(final AppException e) {
			//发生异常时，上传该异常
			/*ExceptionHandler.saveException(e, new CrashManagerListener() {
            });*/
			if (!isStop() && !isDestroy) {
                AppContext.getInstance().runOnUIThread(new Runnable() {

					@Override
					public void run() {
						if (!isStop() && !isDestroy) {
							e.makeToast(AppContext.getInstance());
							if (httpSetting.getOnErrorListener() != null) {
								HttpError httpError = new HttpError(e);
								httpSetting.getOnErrorListener().onError(
										httpError);
							}
						}
					}
				});
				stopControllers.remove(this);
			}
		}

		/**
		 * 请求结束回调函数
		 * 
		 * @param result
		 */
		private void callBack(final Object result) {
			if (!isStop() && !isDestroy) {
                AppContext.getInstance().runOnUIThread(new Runnable() {

					@Override
					public void run() {
						if (!isStop() && !isDestroy) {
							HttpResponse httpresponse = new HttpResponse();
							if (result instanceof Entity)
								httpresponse.setResultObject((Entity) result);
							if (result instanceof byte[])
								httpresponse.setInputData((byte[]) result);
							if (result instanceof File)
								httpresponse.setSaveFile((File) result);
							httpSetting.getOnEndListener().onEnd(httpresponse);
						}
					}
				});
				stopControllers.remove(this);
			}
		}

		/**
		 * 判断缓存数据是否可读
		 * 
		 * @param fileName
		 * @return
		 */
		private boolean isReadDataCache(String fileName) {
			return readObject(fileName) != null;
		}

		/**
		 * 检测网络是否可用
		 * 
		 * @return
		 */
		public boolean isNetworkConnected() {
			ConnectivityManager cm = (ConnectivityManager) AppContext.getInstance().getSystemService(
							Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			return ni != null && ni.isConnectedOrConnecting();
		}

		/**
		 * 保存对象
		 * 
		 * @param ser
		 * @param file
		 * @throws IOException
		 */
		public boolean saveObject(Serializable ser, String file) {
			FileOutputStream fos = null;
			ObjectOutputStream oos = null;
			try {
				fos = AppContext.getInstance().openFileOutput(file,Application.MODE_PRIVATE);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(ser);
				oos.flush();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				try {
					oos.close();
				} catch (Exception e) {
				}
				try {
					fos.close();
				} catch (Exception e) {
				}
			}
		}

		/**
		 * 读取对象
		 * 
		 * @param fileName
		 * @return
		 * @throws IOException
		 */
		public Serializable readObject(String fileName) {
			if (!isExistDataCache(fileName))
				return null;
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			try {
				fis = AppContext.getInstance().openFileInput(fileName);
				ois = new ObjectInputStream(fis);
				return (Serializable) ois.readObject();
			} catch (FileNotFoundException e) {
			} catch (Exception e) {
				e.printStackTrace();
				// 反序列化失败 - 删除缓存文件
				if (e instanceof InvalidClassException
						&& (!isStop() && !isDestroy)) {
					File data = AppContext.getInstance().getFileStreamPath(fileName);
					data.delete();
				}
			} finally {
				try {
					ois.close();
				} catch (Exception e) {
				}
				try {
					fis.close();
				} catch (Exception e) {
				}
			}
			return null;
		}

		/**
		 * 判断缓存是否存在
		 * 
		 * @param fileName
		 * @return
		 */
		private boolean isExistDataCache(String fileName) {
			boolean exist = false;
			try{
				File data = AppContext.getInstance().getFileStreamPath(
                        fileName);
				if (data.exists())
					exist = true;
			}catch(Exception e){
				e.printStackTrace();
			}
			return exist;
		}

		public HttpSetting getHttpSetting() {
			return httpSetting;
		}

		public HttpRequest(HttpSetting httpsetting) {
			httpSetting = httpsetting;

		}

		@Override
		public boolean isStop() {
			return isStop;
		}

		/**
		 * 页面结束时，调用此方法
		 */
		@Override
		public void stop() {
			isStop = true;
			stopControllers.remove(this);
			httpSetting = null;
		}
	}

	public HttpGroup(HttpGroupSetting httpgroupsetting) {
		httpCount = 0;
		completesCount = 0;
		httpGroupSetting = httpgroupsetting;
		priority = httpgroupsetting.getPriority();
		type = httpgroupsetting.getType();
	}

	/**
	 * 请求数据的入口
	 * 
	 * @param httpSetting
	 *            请求的配置清单
	 * @return
	 */
	public HttpRequest add(final HttpSetting httpSetting) {
		httpIdCounter = 1 + httpIdCounter;
		httpSetting.setId(httpIdCounter);
		final HttpRequest httpRequest = new HttpRequest(httpSetting);
		final OnReadyListener onReadyListener = httpSetting
				.getOnReadyListener();
		// 如果准备不为空的话则，执行准备流程
		if (onReadyListener != null)
			new Thread() {
				public void run() {
					onReadyListener.onReady(httpSetting);
					if (httpSetting.isReady()) {
						add2(httpRequest);
					} else {
						HttpError httperror = new HttpError(new Exception(
								"no ready"));
						httpSetting.onError(httperror);
					}
				}
			}.start();
		else
			add2(httpRequest);
		return httpRequest;
	}

	public void add2(HttpRequest httprequest) {
		HttpSetting httpsetting;
		httpsetting = httprequest.getHttpSetting();
		if (httpsetting.getType() == 0)
			httpsetting.setType(type);
		if (httpsetting.getPriority() == 0)
			httpsetting.setPriority(priority);
		// 将每次请求放入停止控制器中
		stopControllers.add(httprequest);
		execute(httprequest);
	}

	/**
	 * 累加请求数
	 */
	protected void addCompletesCount() {
		completesCount = 1 + completesCount;
		if (completesCount == httpCount)
			onEnd();
	}

	/**
	 * 执行请求的核心回调接口
	 * 
	 * @param httprequest
	 */
	public abstract void execute(HttpRequest httprequest);

    /**
     * 页面不是当前页面时，调用此方法
     */
    @Override
    public void onPause() {
        if (!isDestroy)
            AppContext.getInstance().runOnUIThread(new Runnable() {

                @Override
                public void run() {
                    if (onGroupEndListener != null)
                        onGroupEndListener.onEnd();
                }
            });
    }

    /**
	 * 活动结束时调用此方法，更新标志位
	 */
	@Override
	public void onDestroy() {
		isDestroy = true;
		for (int i = 0; i < stopControllers.size(); i++) {
			stopControllers.get(i).stop();
		}
		stopControllers = null;
		httpGroupSetting = null;
		onGroupEndListener = null;
		onGroupErrorListener = null;
		onGroupStartListener = null;
	}

	/**
	 * http请求队列开始结束时调用此方法
	 */
	protected void onEnd() {
        if (!isDestroy)
            AppContext.getInstance().runOnUIThread(new Runnable() {

                @Override
                public void run() {
                    if (onGroupEndListener != null)
                        onGroupEndListener.onEnd();
                    completesCount = 0;
                    httpCount = 0;
                }
            });

	}

	/**
	 * 请求群出现错误，回调的接口
	 */
	protected void onError() {
		if (onGroupErrorListener != null)
			onGroupErrorListener.onError();
	}

	/**
	 * http请求队列开始时调用此方法
	 */
	protected void onStart() {
		if (!isDestroy)
            AppContext.getInstance().runOnUIThread(new Runnable() {

				@Override
				public void run() {
					if (onGroupStartListener != null)
						onGroupStartListener.onStart();
				}
			});
	}

}