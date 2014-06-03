package com.jingdong.app.pad.utils;

import java.util.ArrayList;
import java.util.Map;


import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.IDestroyListener;
import com.jingdong.common.frame.MyActivity;
import com.jingdong.common.http.HttpError;
import com.jingdong.common.http.HttpGroup;
import com.jingdong.common.http.HttpGroupSetting;
import com.jingdong.common.http.HttpGroup.OnAllListener;
import com.jingdong.common.http.HttpResponse;
import com.jingdong.common.http.HttpSetting;
import com.jingdong.common.thread.ThreadPool;
import com.jingdong.common.utils.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ListView;

public abstract class NextPageLoader implements OnAllListener, IDestroyListener {

	private MySimpleAdapter adapter;
	// 是否有通知视图更新的标志位
	private boolean hasNotify;
	// 是否被回收的标志位
	private boolean isFinishing;
	// 用户是否在滑动的标志位
	private boolean isHolding = false;
	// 是否需要翻页
	private boolean isPaging = true;
	// 是否允许再次加载数据，到最后一页时此标志为false
	private boolean loadedLastPage;
	// 防止重复请求，数据重复累加
	private boolean loadedShow = false;
	private MyActivity myActivity;
	// 列表滚动监听-->
	private OnScrollLastListener onScrollLastListener;
	protected Integer pageNum = Integer.valueOf(1);
	protected String pageNumParamKey = "page";
	protected Integer pageSize = Integer.valueOf(10);
	protected String pageSizeParamKey = "pagesize";
	private int pageitemSize = 0;
	// 外部回调onScroll滚动类
	private MyOnScrollListener myScrollListener;
	// 避免重复提交同一次请求的标志位
	private boolean loading;
	// 下一页的数据
	private ArrayList nextItemList;
	// 请求接口
	protected String finalUrl;
	// 请求参数
	private Map<String, Object> paramMaps;
	// 请求-》线程池 核心类
	protected HttpGroup httpGroup;
	// 当前显示的数据
	protected ArrayList showItemList = new ArrayList();
	// 列表view
	private AdapterView adapterView;
	// 要获取的结果类型
	private Class targetClass;

	public NextPageLoader(MyActivity paramMyActivity, AdapterView adapterView,
			String finalUrl, Class targetClass) {
		this.myActivity = paramMyActivity;
		paramMyActivity.addDestroyListener(this);
		this.httpGroup = this.myActivity.getHttpGroupaAsynPool();
		this.adapterView = adapterView;
		this.finalUrl = finalUrl;
		this.targetClass = targetClass;
	}

	/**
	 * 
	 * @param paramMyActivity
	 *            当前活动
	 * @param adapterView
	 *            列表视图控件
	 * @param finalUrl
	 *            请求列表地址
	 * @param paramMaps
	 *            请求参数
	 * @param targetClass
	 *            请求结果类型
	 */
	public NextPageLoader(MyActivity paramMyActivity, AdapterView adapterView,
			String finalUrl, Map<String, Object> paramMaps, Class targetClass) {
		this(paramMyActivity, adapterView, finalUrl, targetClass);
		this.paramMaps = paramMaps;
	}

	/**
	 * 每次加载数据时，需调用此方法， 以便数据加载完毕后能更新视图. 防止两个重复加载的请求结果都追加到列表上
	 */
	private void applyLoadedShow() {
		this.loadedShow = true;
	}

	private boolean isLoadedLastPage() {
		return this.loadedLastPage;
	}

	private boolean loadedShow() {
		boolean show = false;
		if (this.loadedShow) {
			this.loadedShow = false;
			show = true;
		}
		return show;
	}

	/**
	 * 加载下一页的数据，并同时更新视图
	 */
	private void tryShowNextPage() {
		if (!this.loadedLastPage)
			if ((this.nextItemList == null)) {// 如果下一页的数据为空，去加载
				if (loadingLock())
					loading();
			} else {// 否则的话将数据附加到视图上
				if (Log.D)
					Log.d("Temp", "show do -->> ");
				showNextPage(this.nextItemList);
			}
	}

	public ArrayList<?> getAllProductList() {
		return this.showItemList;
	}

	public Integer getListSize() {
		Integer itemSize = new Integer(0);
		if (this.showItemList != null)
			itemSize = Integer.valueOf(this.showItemList.size());
		return itemSize;
	}

	public Integer getPageNum() {
		return this.pageNum;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public int getPageitemSize() {
		if (this.pageitemSize != 0)
			return this.pageitemSize;
		else
			this.pageitemSize = getPageSize().intValue();
		return this.pageitemSize;
	}

	public Object getItemAt(int paramInt) {
		Object product = null;
		if (this.adapter != null)
			product = this.adapter.getItem(paramInt);
		return product;
	}

	protected void handleParamsBeforeLoading() {
		this.paramMaps.put(this.pageNumParamKey, this.pageNum);
		this.paramMaps.put(this.pageSizeParamKey, getPageSize());
	}

	public boolean isPaging() {
		return this.isPaging;
	}

	/**
	 * 持有此类着，当数据发生变化时，通知适配器更新视图
	 */
	public void notifyDataSetChanged() {
		// 如果用户不是在滑动中则更新视图
		if (!this.isHolding) {
			if (this.adapter != null)
				this.adapter.notifyDataSetChanged();
		} else {
			this.hasNotify = true;
		}
	}

	protected abstract MySimpleAdapter createAdapter(
			MyActivity paramMyActivity, AdapterView paramAdapterView,
			ArrayList<?> paramArrayList);

	/**
	 * 滚动状态监听
	 * 
	 * @author yepeng
	 * 
	 */
	class OnScrollLastListener implements OnScrollListener {

		private int firstVisibleItem;
		private int totalItemCount;
		private int visibleItemCount;

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			this.firstVisibleItem = firstVisibleItem;
			this.totalItemCount = totalItemCount;
			this.visibleItemCount = visibleItemCount;
			if (NextPageLoader.this.myScrollListener != null)
				NextPageLoader.this.myScrollListener.onScroll(view,
						firstVisibleItem, visibleItemCount, totalItemCount);
		}

		/**
		 * 检查是否滑动到最后
		 */
		public void checkLast() {
			if (this.firstVisibleItem + this.visibleItemCount == this.totalItemCount)
				onScrollLast();
		}

		public void onScrollFling() {
		};

		/**
		 * 空闲时启动休眠的线程池，重置用户动作为空闲
		 */
		public void onScrollIdle() {
			if (Log.D)
				Log.d(NextPageLoader.class.getName(), "onScrollIdle() -->>");
			isHolding = false;
			ThreadPool.setUiThreadBusy(false);
			if (!isFinishing) {
				if (hasNotify) {// 如果有通知的话则通知数据变化
					hasNotify = false;
					adapter.notifyDataSetChanged();
				}
				checkLast();
			}
		};

		/**
		 * 滑动到最后则加载下一页的数据
		 */
		public void onScrollLast() {
			if (!isFinishing) {
				if (!isLoadedLastPage()) {
					tryShowNextPage();
				}
			}
		};

		/**
		 * 开始滑动时，暂停线程池
		 */
		public void onScrollStart() {
			if (Log.D)
				Log.d(NextPageLoader.class.getName(), "onScrollStart() -->>");
			ThreadPool.setUiThreadBusy(true);
		};

		/**
		 * 根据滚动状态调用不同的回调方法，完成对线程池的休眠与启动
		 */
		public void onScrollStateChanged(AbsListView paramAbsListView,
				int changeState) {
			switch (changeState) {
			case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				onScrollStart();
			case OnScrollListener.SCROLL_STATE_FLING:
				onScrollFling();
			case OnScrollListener.SCROLL_STATE_IDLE:
				onScrollIdle();
			}
		}

	}

	/**
	 * 将封装好的数据，展现都视图上
	 * 
	 * @param nextItemList
	 */
	private void showNextPage(ArrayList nextItemList) {
		if (!isFinishing) {
			if (this.nextItemList != null){
				this.showItemList.addAll(this.nextItemList);
			}
			if (nextItemList != null && nextItemList.size() < getPageitemSize()) {// 如果当前获取数据数比每页数据数小的话，则说明到最后一页了
				loadedLastPage = true;
			} else {
				if (!isPaging) {// 如果不是翻页数据的话，则说明就一页
					loadedLastPage = true;
				} else if (!loadedLastPage) {// 如果允许加载的话，则加载下一页的数据
					pageNum++;
					loading();
				}
			}
			this.nextItemList = null;
			if (!isFinishing) {
				if (adapter == null) {
					adapter = createAdapter(myActivity, adapterView,
							showItemList);
					if (onScrollLastListener == null) {
						onScrollLastListener = new OnScrollLastListener();
					}
					if (adapterView instanceof ListView && adapterView != null) {
						((ListView) adapterView)
								.setOnScrollListener(onScrollLastListener);
					} else if (adapterView instanceof GridView
							&& adapterView != null) {
						((GridView) adapterView).setOnScrollListener(onScrollLastListener);
					} else if (adapterView instanceof Gallery
							&& adapterView != null) {
						((Gallery) adapterView)
								.setOnItemSelectedListener(new OnItemSelectedListener() {

									@Override
									public void onItemSelected(
											AdapterView<?> parent, View view,
											int position, long id) {
										if (!isFinishing) {
											// 当滑动到倒数第二个条目时，加载下一页的数据
											if (position >= showItemList.size() - 2) {
												if (!isLoadedLastPage())
													tryShowNextPage();
											}
										}

									}

									@Override
									public void onNothingSelected(
											AdapterView<?> parent) {

									}
								});
					}
					// 当用户触摸时，更新用户动作，防止新的通知更改视图数据
					adapterView.setOnTouchListener(new OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {
							switch (event.getAction()) {
							case MotionEvent.ACTION_DOWN:
								isHolding = true;
								break;
							}
							return false;
						}
					});
					adapterView.setAdapter(adapter);
				} else {
					adapter.notifyDataSetChanged();
				}

			}
		}
	}

	/**
	 * 获取请求配置对象
	 * 
	 * @return
	 */
	protected HttpSetting getHttpSetting() {
		HttpSetting httpSetting = new HttpSetting();
		httpSetting.setFinalUrl(finalUrl);
		httpSetting.setParamMaps(paramMaps);
		httpSetting.setCurrentEntity(targetClass);
		httpSetting.setPost(true);
		httpSetting.setType(HttpGroupSetting.TYPE_JSON);
		httpSetting.setPriority(HttpGroupSetting.PRIORITY_JSON);
		httpSetting.setCacheMode(HttpSetting.CACHE_MODE_ONLY_NET);
		return httpSetting;
	}

	/**
	 * 获得参数
	 * 
	 * @return
	 */
	public Map<String, Object> getParams() {
		return this.paramMaps;
	}

	/**
	 * 加载数据
	 */
	public void loading() {
		loadingLock();
		HttpSetting localHttpSetting = getHttpSetting();
		handleParamsBeforeLoading();
		localHttpSetting.setListener(this);
		this.httpGroup.add(localHttpSetting);
	}

	private synchronized boolean loadingLock() {
		if (!loading) {
			loading = true;
		}
		return loading;
	}

	private synchronized void loadingUnLock() {
		loading = false;
	}

	/**
	 * 列表滚动的回调方法
	 * 
	 * @author yepeng
	 * 
	 */
	public static abstract interface MyOnScrollListener {
		public abstract void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount);
	}

	/**
	 * 将请求的结果转为集合的回调接口
	 * 
	 * @param paramHttpResponse
	 * @return
	 */
	protected abstract ArrayList toList(HttpResponse paramHttpResponse);

	public void setMyOnScrollListener(MyOnScrollListener paramMyOnScrollListener) {
		this.myScrollListener = paramMyOnScrollListener;
	}

	/**
	 * 再次加工数据，同时通知变化更新视图
	 * 
	 * @param paramModifyDataRunnable
	 */
	public void modifyData(ModifyDataRunnable paramModifyDataRunnable) {
		if (!this.isHolding) {
			paramModifyDataRunnable.modifyData(this.showItemList);
			if (this.adapter != null)
				this.adapter.notifyDataSetChanged();
		}
	}

	public void setPageNumParamKey(String paramString) {
		this.pageNumParamKey = paramString;
	}

	public void setPageSize(int paramInt) {
		this.pageSize = Integer.valueOf(paramInt);
	}

	public void setPageSizeParamKey(String paramString) {
		this.pageSizeParamKey = paramString;
	}

	public void setPageitemSize(int paramInt) {
		this.pageitemSize = paramInt;
	}

	public void setPaging(boolean paramBoolean) {
		this.isPaging = paramBoolean;
	}

	public Class getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class targetClass) {
		this.targetClass = targetClass;
	}

	protected abstract void showError();

	public void showThisPage() {
		applyLoadedShow();
		tryShowNextPage();
	}

	/**
	 * 销毁的回调方法
	 */
	@Override
	public void onDestroy() {
		this.isFinishing = true;
		this.myActivity = null;
		this.adapterView = null;
		this.adapter = null;
		this.showItemList = null;
		this.nextItemList = null;
		this.httpGroup = null;
		this.paramMaps = null;
		this.targetClass = null;
	}

	@Override
	public void onStart() {

	}

	/**
	 * 数据加载完毕后的操作
	 */
	@Override
	public void onEnd(HttpResponse httpresponse) {
		final ArrayList result = toList(httpresponse);
		myActivity.post(new Runnable() {
			public void run() {

				if (NextPageLoader.this.isFinishing) {
					if (Log.D)
						Log.d("Temp", "onEnd -->>isFinishing "
								+ NextPageLoader.this.isFinishing);
				} else {
					if (result == null) {
						pageNum--;
						NextPageLoader.this.showError();
					} else {
						if (Log.D)
							Log.d("Temp", "show itemList -->> " + result.size());
						NextPageLoader.this.nextItemList = result;
						if (NextPageLoader.this.loadedShow()) {// 当加载完毕，同步视图
							if (Log.D)
								Log.d("Temp", "show now -->> ");
							NextPageLoader.this.showNextPage(result);
						}
					}
					NextPageLoader.this.loadingUnLock();
				}
			}
		});
	}

	@Override
	public void onError(HttpError httperror) {
		loadingUnLock();
		showError();
	}

	public static abstract interface ModifyDataRunnable {
		public abstract void modifyData(ArrayList<Object> paramArrayList);
	}

}
