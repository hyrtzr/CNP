package com.jingdong.common.frame;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import net.oschina.app.AppContext;
import com.jingdong.common.frame.taskStack.BackStackManager;
import com.jingdong.common.http.HttpGroup;
import com.jingdong.common.http.HttpGroup.OnEndListener;
import com.jingdong.common.http.HttpGroupSetting;
import com.jingdong.common.http.HttpSetting;
import com.jingdong.common.utils.Log;
import android.support.v4.app.Fragment;

/**
 * 全局所有activity的父类，继承此类的好处为： 1.自动调用-所有引用且设置回调销毁类-onDestry方法
 * 2.可获取http请求池子，机制为task-threadPool,排队机制 3.可获取IMyActivity里面的基本操作接口的实现
 * 
 */
public class MyActivity extends Fragment implements IMyActivity {

	private ArrayList<IDestroyListener> destroyListenerList = new ArrayList<IDestroyListener>();
	private ArrayList<PauseListener> pauseListenerList = new ArrayList<PauseListener>();
	private ArrayList<ResumeListener> resumeListenerList = new ArrayList<ResumeListener>();
	private ArrayList<StopListener> stopListenerList = new ArrayList<StopListener>();
	private Handler handler;
	private boolean isDestroy;
	private LayoutInflater inflater;
	private ActionBarActivity mActivity;
	private View frameView;
	private int viewId;
	private SharedPreferences sharedPreferences;
	private ViewGroup mViewGroup;// 控制视图的添加与移除
	private boolean isStoped;
	private static int keyId;
	private int key;

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	static {
		keyId = 0;
	}

	public void addDestroyListener(IDestroyListener paramIDestroyListener) {
		if (this.destroyListenerList != null)
			this.destroyListenerList.add(paramIDestroyListener);
	}

	public void addPauseListener(PauseListener paramPauseListener) {
		if (this.pauseListenerList != null)
			this.pauseListenerList.add(paramPauseListener);
	}

	public void addResumeListener(ResumeListener paramResumeListener) {
		if (this.resumeListenerList != null)
			this.resumeListenerList.add(paramResumeListener);
	}

	public void addStopListener(StopListener paramStopListener) {
		if (this.stopListenerList != null)
			this.stopListenerList.add(paramStopListener);
	}

	public HttpGroup getHttpGroupaAsynPool() {
		return getHttpGroupaAsynPool(HttpGroupSetting.TYPE_JSON);
	}

	public HttpGroup getHttpGroupaAsynPool(int type) {
		HttpGroupSetting localHttpGroupSetting = new HttpGroupSetting();
		localHttpGroupSetting.setType(type);
		return getHttpGroupaAsynPool(localHttpGroupSetting);
	}

	public HttpGroup getHttpGroupaAsynPool(
			final HttpGroupSetting paramHttpGroupSetting) {
		HttpGroup.HttpGroupaAsynPool localHttpGroupaAsynPool = new HttpGroup.HttpGroupaAsynPool(
				paramHttpGroupSetting);
		addDestroyListener(localHttpGroupaAsynPool);
        addPauseListener(localHttpGroupaAsynPool);
		return localHttpGroupaAsynPool;
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		handler = new Handler();
		inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		hideSoftInput();
		this.sharedPreferences = this.mActivity.getSharedPreferences(
				"myAndroidClient", 0);
		if (this.frameView == null) {
			this.frameView = realCreateViewMethod(this.inflater, null);
			if (Log.D)
				Log.d("MyActivity", "v.id -->> " + this.frameView.getId());
			setViewId(this.frameView.getId());
		}
	}

	protected View realCreateViewMethod(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup) {
		if (Log.D)
			Log.d("MyActivity", "子类必须重写该方法 -->> ");
		return null;
	}

	public void setViewId(int paramInt) {
		this.viewId = paramInt;
	}

	public int getViewId() {
		return this.viewId;
	}

	@Override
	public void onAttach(Activity activity) {
		mActivity = (ActionBarActivity)activity;
		if (Log.D)
			Log.d("MyActivity", "onAttach() -->> " + this);
		keyId = 1 + keyId;
		this.key = keyId;
		if (Log.D)
			Log.d("MyActivity", "keyId -->> " + keyId);
		if (Log.D)
			Log.d("MyActivity", "key -->> " + this.key);
		super.onAttach(activity);
	}

	/**
	 * 隐藏键盘
	 */
	public void hideSoftInput() {
		((InputMethodManager) this.mActivity
				.getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(this.mActivity.getWindow()
						.getDecorView().getWindowToken(), 0);
	}

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
		if (Log.D) {
			Log.d("MyActivity", "onCreateView---> " + this);
			Log.d("MyActivity", "v.id -->> " + this.frameView.getId());
			Log.d("MyActivity", "v -->> " + this.frameView);
			Log.d("MyActivity",
					"v.hashCode() -->> " + this.frameView.hashCode());
		}
		return this.frameView;
	}

	/**
	 * 为目标view获取接触焦点
	 * 
	 * @param paramView
	 */
	public void postFocusToView(final View paramView) {
		post(new Runnable() {
			public void run() {
				if ((paramView.isEnabled()) && (paramView.isFocusable()))
					paramView.requestFocus();
			}
		});
	}

	/**
	 * 获取当前fragment的容器
	 * 
	 * @return
	 */
	public static int getRootViewId() {
		return getBaseActivity().getCurrentFragmentViewId();
	}

	public static BaseActivity getBaseActivity() {
		return AppContext.getInstance().getBaseActivity();
	}

	public void putBooleanToPreference(String paramString, Boolean paramBoolean) {
		this.sharedPreferences.edit()
				.putBoolean(paramString, paramBoolean.booleanValue()).commit();
	}

	public void putStringToPreference(String paramString1, String paramString2) {
		this.sharedPreferences.edit().putString(paramString1, paramString2)
				.commit();
	}

	public String getStringFromPreference(String paramString) {
		return this.sharedPreferences.getString(paramString, "");
	}

	public String getStringFromPreference(String paramString1,
			String paramString2) {
		return this.sharedPreferences.getString(paramString1, paramString2);
	}

	/**
	 * 控制此片段不回显
	 */
	public void noShowAgain() {
		BackStackManager.getInstance().getCurrent().setInHistory(false);
	}

	/**
	 * 移除片段
	 * @param paramMyActivity 移除目标
	 */
	public static void remove(MyActivity paramMyActivity) {
		if ((paramMyActivity == null) && (Log.D))
			Log.d("MyActivity", "fragment.=null -->> ");
		if (Log.D)
			Log.d("MyActivity",
					"fragment.getKey()() -->> " + paramMyActivity.getKey());
		FragmentTransaction localFragmentTransaction = getBaseActivity()
				.getSupportFragmentManager().beginTransaction();
		localFragmentTransaction.remove(paramMyActivity);
		localFragmentTransaction.commit();
	}


    @Override
	public void onDestroy() {
		super.onDestroy();
		isDestroy = true;
		if (this.destroyListenerList != null) {
			Iterator<IDestroyListener> destroyIterator = this.destroyListenerList
					.iterator();
			while (destroyIterator.hasNext())
				destroyIterator.next().onDestroy();
		}
		handler = null;
		inflater = null;
		destroyListenerList = null;
		pauseListenerList = null;
		resumeListenerList = null;
		stopListenerList = null;
	}

	public void onPause() {
		super.onPause();
		if (this.pauseListenerList != null) {
			Iterator<PauseListener> pauseIterator = this.pauseListenerList
					.iterator();
			while (pauseIterator.hasNext())
				pauseIterator.next().onPause();
		}
	}

	public void onResume() {
		super.onResume();
		//片段唤醒时，将片段VIEW恢复过去
		if (this.isStoped && this.frameView != null) {
			ViewGroup viewGroup = (ViewGroup)(this.frameView.getParent());
			if(viewGroup == null){
				mViewGroup.removeView(this.frameView);
				mViewGroup.addView(this.frameView);
			}else{
				viewGroup.removeView(this.frameView);
				viewGroup.addView(this.frameView);
			}
			isStoped = false;
		}
		getBaseActivity().setCurrentMyActivity(this);
		if (this.resumeListenerList != null) {
			Iterator<ResumeListener> resumeIterator = this.resumeListenerList
					.iterator();
			while (resumeIterator.hasNext())
				resumeIterator.next().onResume();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		//片段停止时 将片段VIEW移除
		if ((this.frameView != null)) {
			this.mViewGroup = ((ViewGroup) this.frameView.getParent());
			if (this.mViewGroup != null) {
				this.isStoped = true;
				this.mViewGroup.removeView(this.frameView);
			}
		}
		if (this.stopListenerList != null) {
			Iterator<StopListener> stopIterator = this.stopListenerList
					.iterator();
			while (stopIterator.hasNext())
				stopIterator.next().onStop();
		}
	}

	public void removeDestroyListener(IDestroyListener paramIDestroyListener) {
		if (this.destroyListenerList != null)
			this.destroyListenerList.remove(paramIDestroyListener);
	}

	public void removePauseListener(PauseListener paramPauseListener) {
		if (this.pauseListenerList != null)
			this.pauseListenerList.remove(paramPauseListener);
	}

	public void removeResumeListener(ResumeListener paramResumeListener) {
		if (this.resumeListenerList != null)
			this.resumeListenerList.remove(paramResumeListener);
	}

	public void removeStopListener(StopListener paramStopListener) {
		if (this.stopListenerList != null)
			this.stopListenerList.remove(paramStopListener);
	}

	public static abstract interface PauseListener {
		public abstract void onPause();
	}

	public static abstract interface ResumeListener {
		public abstract void onResume();
	}

	public static abstract interface StopListener {
		public abstract void onStop();
	}

	@Override
	public Handler getHandler() {
		return handler;
	}

	@Override
	public ActionBarActivity getThisActivity() {
		return mActivity;
	}

	@Override
	public void post(Runnable paramRunnable) {
		if (!isDestroy && handler != null)
			handler.post(paramRunnable);
	}

	@Override
	public void post(Runnable paramRunnable, int delayedTime) {
		if (!isDestroy && handler != null)
			handler.postDelayed(paramRunnable, delayedTime);
	}


	@Override
	public View inflate(int layoutResourceId) {
		return inflater.inflate(layoutResourceId, null);
	}

    @Override
    public void refresh() {

    }

    @Override
	public void execute(String finalUrl, Map<String, Object> paramMap,
			Class dataClass, OnEndListener onEndListener) {
		HttpGroup httpGroup = getHttpGroupaAsynPool();
		HttpSetting httpSetting = new HttpSetting();
		httpSetting.setFinalUrl(finalUrl);
		httpSetting.setParamMaps(paramMap);
		httpSetting.setCurrentEntity(dataClass);
		httpSetting.setPost(true);
		httpSetting.setType(HttpGroupSetting.TYPE_JSON);
		httpSetting.setPriority(HttpGroupSetting.PRIORITY_JSON);
		httpSetting.setCacheMode(HttpSetting.CACHE_MODE_ONLY_NET);
		httpSetting.setListener(onEndListener);
		httpGroup.add(httpSetting);
	}

	@Override
	public void executeImage(String imageUrl, OnEndListener onEndListener) {
		HttpGroup httpGroup = getHttpGroupaAsynPool();
		HttpSetting httpSetting = new HttpSetting();
		httpSetting.setFinalUrl(imageUrl);
		httpSetting.setType(HttpGroupSetting.TYPE_IMAGE);
		httpSetting.setPriority(HttpGroupSetting.PRIORITY_IMAGE);
		httpSetting.setCacheMode(HttpSetting.CACHE_MODE_ONLY_CACHE);
		httpSetting.setListener(onEndListener);
		httpGroup.add(httpSetting);
	}

}
