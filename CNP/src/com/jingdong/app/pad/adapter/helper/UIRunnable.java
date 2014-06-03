package com.jingdong.app.pad.adapter.helper;

import android.view.View;
import android.widget.ImageView;
import com.jingdong.app.pad.adapter.SimpleBeanAdapter;
import com.jingdong.common.utils.Log;
import com.jingdong.common.utils.cache.GlobalImageCache;

/**
 * 操作JDPuctDrawable的辅助类
 * 
 * @author yepeng
 * 
 */
public class UIRunnable implements Runnable {

	protected GlobalImageCache.ImageState imageState;
	protected SimpleBeanAdapter.SubViewHolder subViewHolder;

	public UIRunnable(SimpleBeanAdapter.SubViewHolder paramSubViewHolder,
			GlobalImageCache.ImageState paramImageState) {
		this.subViewHolder = paramSubViewHolder;
		this.imageState = paramImageState;
	}

	protected void gc() {
		this.subViewHolder = null;
		this.imageState = null;
	}

	protected SimpleBeanAdapter.SubViewHolder getSubViewHolder() {
		return this.subViewHolder;
	}

	/**
	 * 通过判断视图元素绑定与当前位置元素对比，返回正确的条目，防止图片错位
	 * 
	 * @return
	 */
	protected View getItemView() {
		AdapterHelper localAdapterHelper = getSubViewHolder().getAdapter()
				.getAdapterHelper();
		SimpleBeanAdapter.SubViewHolder localSubViewHolder = getSubViewHolder();
		Object localObject = localSubViewHolder.getAdapter().getItem(
				localSubViewHolder.getPosition());
		if (Log.D) {
			Log.d(UIRunnable.class.getSimpleName(),
					"getItemView() old item -->> "
							+ localSubViewHolder.getItemData());
			Log.d(UIRunnable.class.getSimpleName(),
					"getItemView() new item -->> " + localObject);
		}
		if ((localObject != null)
				&& (localSubViewHolder.getItemData().equals(localObject))) {
			if (Log.D)
				Log.d(UIRunnable.class.getSimpleName(),
						"oldItemData and newItemData is equals -->> ");
			return localAdapterHelper.getItemView(getSubViewHolder()
					.getPosition());
		}else{
            if (Log.D)
                Log.d(UIRunnable.class.getSimpleName(),
                        "oldItemData and newItemData is not equals -->> ");
            return null;
        }

	}

	/**
	 * 获取元素，并附加元素试图
	 */
	public void run() {
		if(getSubViewHolder() ==  null)
			return;
		if (Log.D)
			Log.d(UIRunnable.class.getSimpleName(), "run() position -->> "
					+ getSubViewHolder().getPosition());
		View itemView = this.subViewHolder.getItemView();
		/**
		 * 如果该元素被回收的话，则去adapterView上去找
		 */
		View superItemView = null;
		if (itemView != null || (superItemView = getItemView()) != null) {
			if(superItemView != null)
				itemView = superItemView.findViewById(getSubViewHolder().getItemViewId());
			ImageView imageView;
			JDProductDrawable localJDProductDrawable;
			if ((itemView instanceof ImageView)) {
				imageView = (ImageView) itemView;
				if ((imageView.getDrawable() != null)
						&& ((imageView.getDrawable() instanceof JDProductDrawable))) {
					localJDProductDrawable = (JDProductDrawable) imageView
							.getDrawable();
                    localJDProductDrawable.update(getSubViewHolder(),GlobalImageCache.getBitmapDigest(this.imageState),null);
				} else {
					localJDProductDrawable = new JDProductDrawable(
							this.subViewHolder,
							GlobalImageCache.getBitmapDigest(this.imageState));
					imageView.setImageDrawable(localJDProductDrawable);
				}
				switch (this.imageState.getState()) {
				case GlobalImageCache.STATE_NONE:
					if (Log.D)
						Log.d(UIRunnable.class.getSimpleName(),
								"STATE_NONE position -->> "
										+ getSubViewHolder().getPosition());
					localJDProductDrawable
							.setBitmapState(GlobalImageCache.STATE_NONE);
					localJDProductDrawable.refresh(null);
					break;
				case GlobalImageCache.STATE_LOADING:
					if (Log.D)
						Log.d(UIRunnable.class.getSimpleName(),
								"STATE_LOADING position -->> "
										+ getSubViewHolder().getPosition());
					localJDProductDrawable
							.setBitmapState(GlobalImageCache.STATE_LOADING);
					localJDProductDrawable.refresh(null);
					break;
				case GlobalImageCache.STATE_FAILURE:
					if (Log.D)
						Log.d(UIRunnable.class.getSimpleName(),
								"STATE_FAILURE position -->> "
										+ getSubViewHolder().getPosition());
					localJDProductDrawable
							.setBitmapState(GlobalImageCache.STATE_FAILURE);
					localJDProductDrawable.refresh(null);
					this.imageState.none();
					break;
				case GlobalImageCache.STATE_SUCCESS:
					if (Log.D)
						Log.d(UIRunnable.class.getSimpleName(),
								"STATE_SUCCESS position -->> "
										+ getSubViewHolder().getPosition());
					localJDProductDrawable
							.setBitmapState(GlobalImageCache.STATE_SUCCESS);
					localJDProductDrawable.refresh(this.imageState.getBitmap());
					gc();
					break;
				}
			}
		}else{
            if (Log.D)
                Log.d(UIRunnable.class.getSimpleName(),
                        "run itemview is null");
            gc();
        }
	}

}