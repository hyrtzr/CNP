package com.jingdong.app.pad.adapter.helper;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import com.jingdong.common.utils.Log;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 1.适配器辅助，缓存所有的view，能缓解直接去主视图上获取子元素.
 * 2.如果垃圾回收器回收操作时，缓存会失效，只有去主视图上去找相应的元素.
 * 3.所有线程持有的元素，都会冲弱引用上区，防止任务排队，内存溢出
 * 
 * @author yepeng
 * 
 */
public class AdapterHelper {

	private AdapterView<Adapter> adapterView;
	private Map<View, Map<Integer, View>> itemView_subViews_map = new WeakHashMap<View, Map<Integer, View>>();

	private Map<Integer, View> getSubViews(View paramView) {
		return (Map) this.itemView_subViews_map.get(paramView);
	}

	public AdapterView<Adapter> getAdapterView() {
		return this.adapterView;
	}

    /**
     * 根据条目编号获取实际在屏幕上的view
     * @param position
     * @return
     */
	public View getItemView(int position) {
		ChildViewInfo childViewInfo = new  ChildViewInfo(adapterView);
        return adapterView.getChildAt(getItemViewIndex(childViewInfo.firstVisiblePosition,childViewInfo.childCount,position));
	}

	public View getSubView(View paramView, int paramInt) {
		if (Log.D) {
			Log.d("Temp", "getSubViews itemView -->> " + paramView);
			Log.d("Temp", "getSubViews(itemView) -->> "
					+ getSubViews(paramView));
		}
		return (View) getSubViews(paramView).get(Integer.valueOf(paramInt));
	}

	public void putSubViews(View paramView, Map<Integer, View> paramMap) {
		this.itemView_subViews_map.put(paramView, paramMap);
	}

	public void setAdapterView(AdapterView<Adapter> paramAdapterView) {
		this.adapterView = paramAdapterView;
	}

	public static Integer getItemViewIndex(int firstVisiblePosition,
			int childViewCount, int position) {
		int i = position - firstVisiblePosition;
		if ((i >= 0) && (i < childViewCount))
			return new Integer(i);
		return new Integer(-1);

	}

	private static class ChildViewInfo {
		
		private Integer childCount;
		private Integer firstVisiblePosition;

		public ChildViewInfo(AdapterView<Adapter> paramAdapterView) {
			this.firstVisiblePosition = Integer.valueOf(paramAdapterView.getFirstVisiblePosition());
			this.childCount = Integer.valueOf(paramAdapterView.getChildCount());
            if (this.firstVisiblePosition.intValue() < 0)
                this.firstVisiblePosition = Integer.valueOf(0);
            if (this.childCount.intValue() <= 0)
                this.childCount = Integer.valueOf(0);
		}
		
	}
}