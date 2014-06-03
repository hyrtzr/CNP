package com.jingdong.app.pad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.jingdong.app.pad.adapter.helper.AdapterHelper;
import com.jingdong.app.pad.adapter.helper.SimpleImageProcessor;
import com.jingdong.app.pad.adapter.helper.SimpleSubViewBinder;
import com.jingdong.app.pad.adapter.helper.SubViewBinder;
import com.jingdong.common.frame.BaseActivity;
import com.jingdong.common.frame.MyActivity;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局基础适配器
 * 
 * @author yepeng
 * 
 */
public class SimpleBeanAdapter extends BaseAdapter {

	// 会占有大量的内存
	private MyActivity context;
    private BaseActivity baseActivity;
	private List data;
	private int layoutId;
	private String[] resKeys;
	private int[] reses;
	private SubViewBinder mViewBinder;
	private AdapterHelper adapterHelper = new AdapterHelper();
	private LayoutInflater inflater;
	private boolean keepVisibleBitmap;

	public SimpleBeanAdapter(MyActivity context, List data, int layoutId,
			String[] resKeys, int[] reses) {
		this.context = context;
		inflater = (LayoutInflater) context.getThisActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.layoutId = layoutId;
		this.data = data;
		this.resKeys = resKeys;
		this.reses = reses;
	}
    public SimpleBeanAdapter(BaseActivity context, List data, int layoutId,
                             String[] resKeys, int[] reses) {
        baseActivity=context;
        if(context != null){
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        this.layoutId = layoutId;
        this.data = data;
        this.resKeys = resKeys;
        this.reses = reses;
    }

	public MyActivity getContext() {
		return context;
	}

    public BaseActivity getBaseActivity(){
        return baseActivity;
    }

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (this.adapterHelper.getAdapterView() == null
				&& (parent instanceof AdapterView)
				&& (this.adapterHelper != null))
			this.adapterHelper.setAdapterView((AdapterView) parent);
		return getViewFromResource(position, convertView, parent, layoutId);
	}

	/**
	 * 将数据与视图元素绑定
	 * 
	 * @param position
	 *            元素位置
	 * @param view
	 *            条目元素
	 */
	private void bindView(int position, View view) {
		if (view != null && getItem(position) != null) {
			for (int i = 0; i < reses.length; i++) {
				View subView = adapterHelper.getSubView(view, new Integer(
						reses[i]));
				if (subView != null) {
					SubViewBinder binder = getViewBinder();
					SubViewHolder subViewHolder = new SubViewHolder();
					try {
						Method method = data.get(position).getClass()
								.getMethod(resKeys[i],(Class[])null);
						Object resultObject = method.invoke(
								data.get(position), (Class[])null);
						if(resultObject != null){
							String showItemResult = resultObject.toString();
							subViewHolder.setShowData(showItemResult);
							subViewHolder.setItemData(getItem(position));
							subViewHolder.setPosition(position);
							subViewHolder.setItemView(subView);
							subViewHolder.setAdapter(this);
							subViewHolder
									.setKeepVisibleBitmap(isKeepVisibleBitmap());
							subViewHolder.setItemViewId(reses[i]);
							binder.bind(subViewHolder);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	public boolean isKeepVisibleBitmap() {
		return keepVisibleBitmap;
	}

	public void setKeepVisibleBitmap(boolean keepVisibleBitmap) {
		this.keepVisibleBitmap = keepVisibleBitmap;
	}

	public SubViewBinder getViewBinder() {
		if (this.mViewBinder == null)
			this.mViewBinder = new SimpleSubViewBinder(
					new SimpleImageProcessor());
		return this.mViewBinder;
	}

	private View getViewFromResource(int position, View paramView,
			ViewGroup paramViewGroup, int layoutId) {
		View localView;
		if (paramView == null)
			localView = createViewFromResource(paramViewGroup, layoutId);
		else
			localView = paramView;
		bindView(position, localView);
		return localView;
	}

	/**
	 * 将新创建的元素，缓存到adapterHelper-》WEAKMAP里面，防止内存溢出与错位
	 * 
	 * @param paramViewGroup
	 * @param layoutId
	 * @return
	 */
	private View createViewFromResource(ViewGroup paramViewGroup, int layoutId) {
		View itemView = inflater.inflate(layoutId, paramViewGroup, false);
		Map<Integer, View> childViewHashMap = new HashMap<Integer, View>();
		for (int j = 0; j < reses.length; j++) {
			childViewHashMap.put(reses[j], itemView.findViewById(reses[j]));
		}
		this.adapterHelper.putSubViews(itemView, childViewHashMap);
		return itemView;
	}

	public AdapterHelper getAdapterHelper() {
		return adapterHelper;
	}

	public static class SubViewHolder {

		private View itemView;
		private int itemViewId;
		private Object itemData;
		private boolean keepVisibleBitmap = false;
		private int position;
		private SimpleBeanAdapter adapter;
		private String showData;

		public Object getItemData() {
			return itemData;
		}

		public void setItemData(Object itemData) {
			this.itemData = itemData;
		}

		public String getShowData() {
			return showData;
		}

		public void setShowData(String showData) {
			this.showData = showData;
		}

		public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		public SubViewHolder() {
		}

		public View getItemView() {
			return this.itemView;
		}

		public boolean isKeepVisibleBitmap() {
			return this.keepVisibleBitmap;
		}

		public void setItemView(View paramView) {
			this.itemView = paramView;
		}

		public void setKeepVisibleBitmap(boolean paramBoolean) {
			this.keepVisibleBitmap = paramBoolean;
		}

		public SimpleBeanAdapter getAdapter() {
			return adapter;
		}

		public void setAdapter(SimpleBeanAdapter adapter) {
			this.adapter = adapter;
		}

		public int getItemViewId() {
			return itemViewId;
		}

		public void setItemViewId(int itemViewId) {
			this.itemViewId = itemViewId;
		}

		public String toString() {
			return "SubViewHolder [itemView=" + this.itemView + ",position"
					+ this.position + ",adapter=" + adapter + "]";
		}

	}

	protected void gc() {
		mViewBinder = null;
		inflater = null;
		adapterHelper = null;
		context = null;
		data = null;
		resKeys = null;
		reses = null;
	}

}