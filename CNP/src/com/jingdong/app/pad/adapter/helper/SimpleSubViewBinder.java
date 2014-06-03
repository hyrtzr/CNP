package com.jingdong.app.pad.adapter.helper;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingdong.app.pad.adapter.SimpleBeanAdapter;
import com.jingdong.common.utils.DPIUtil;
import com.jingdong.common.utils.ImageUtil;
import com.jingdong.common.utils.Log;
import com.jingdong.common.utils.cache.GlobalImageCache;

public class SimpleSubViewBinder implements SubViewBinder {
	
	private SimpleImageProcessor imageProcessor;

	public SimpleSubViewBinder(SimpleImageProcessor paramSimpleImageProcessor) {
		this.imageProcessor = paramSimpleImageProcessor;
	}

	/**
	 * 绑定视图元素与数据，如果没有下载图片的话则通知SimpleImageProcessor加载数据，否则通知其展示
	 */
	public void bind(SimpleBeanAdapter.SubViewHolder paramSubViewHolder) {
		if (Log.D)
			Log.d(SimpleSubViewBinder.class.getName(),
					"bind() getPosition-->> "
							+ paramSubViewHolder.getPosition());
		View localView = paramSubViewHolder.getItemView();
		Object localObject = paramSubViewHolder.getShowData();
		if (localView instanceof TextView){
			((TextView) localView).setText(localObject.toString());
        }else if ((localView instanceof ImageView)) {
			if ((localObject instanceof Integer)) {
				if (Log.D)
					Log.d(SimpleSubViewBinder.class.getName(),
							"bind() image id 1 -->> ");
				((ImageView) localView)
						.setImageResource(((Integer) localObject).intValue());
			} else if ((localObject instanceof String)) {
				try {
					if (Log.D)
						Log.d(SimpleSubViewBinder.class.getName(),
								"bind() image id 2 -->> ");
					((ImageView) localView).setImageResource(Integer
							.parseInt((String) localObject));
				} catch (NumberFormatException localNumberFormatException) {
					String str = (String) localObject;
					if (!str.startsWith("http")) {
						if (Log.D)
							Log.d(SimpleSubViewBinder.class.getName(),
									"bind() image uri -->> ");
						((ImageView) localView).setImageURI(Uri.parse(str));
					} else {
						if (Log.D)
							Log.d(SimpleSubViewBinder.class.getName(),
									"bind() image url -->> ");
						GlobalImageCache.BitmapDigest localBitmapDigest = new GlobalImageCache.BitmapDigest(
								str);
						localBitmapDigest
								.setKeepVisibleBitmap(paramSubViewHolder
										.isKeepVisibleBitmap());
						localBitmapDigest.setPosition(paramSubViewHolder.getPosition());
						ViewGroup.LayoutParams localLayoutParams = localView
								.getLayoutParams();
						if ((localLayoutParams == null)
								|| (localLayoutParams.width < 1)
								|| (localLayoutParams.height < 1)) {
							localView.measure(
									DPIUtil.dip2px(ImageUtil.IMAGE_MAX_WIDTH),
									DPIUtil.dip2px(ImageUtil.IMAGE_MAX_HEIGHT));
							localLayoutParams = localView.getLayoutParams();
						}
						if (localLayoutParams.width > 0) {
							if (Log.D)
								Log.d(SimpleSubViewBinder.class.getName(),
										"layoutParams.width -->> "
												+ localLayoutParams.width);
							localBitmapDigest.setWidth(localLayoutParams.width);
						}
						if (localLayoutParams.height > 0) {
							if (Log.D)
								Log.d(SimpleSubViewBinder.class.getName(),
										"layoutParams.height -->> "
												+ localLayoutParams.height);
							localBitmapDigest
									.setHeight(localLayoutParams.height);
						}
						GlobalImageCache.ImageState localImageState = GlobalImageCache
								.getImageState(localBitmapDigest);
						this.imageProcessor.show(paramSubViewHolder,localImageState);
					}

				}
			}
		}
	}



}