package com.jingdong.app.pad.adapter.helper;

import android.graphics.Bitmap;
import com.jingdong.app.pad.adapter.SimpleBeanAdapter;
import com.jingdong.common.utils.ImageUtil;
import com.jingdong.common.utils.Log;
import com.jingdong.common.utils.cache.GlobalImageCache;
import com.jingdong.app.pad.adapter.MySimpleAdapter;

/**
 * ImageLoader调用者，ImageProcessor回调类的实现者，UIRunnable调用者
 * 
 * @author yepeng
 * 
 */
public class SimpleImageProcessor implements MySimpleAdapter.ImageProcessor {

	public Bitmap create(ImageUtil.InputWay paramInputWay,
			GlobalImageCache.BitmapDigest paramBitmapDigest) {
		return ImageUtil.createBitmap(paramInputWay, paramBitmapDigest);
	}

	protected void loadImage(
			SimpleBeanAdapter.SubViewHolder paramSubViewHolder,
			GlobalImageCache.ImageState paramImageState) {
		new ImageLoader(paramSubViewHolder, paramImageState, this).load();
	}

	public void show(SimpleBeanAdapter.SubViewHolder paramSubViewHolder,
			GlobalImageCache.ImageState paramImageState) {
		if (Log.D)
			Log.d(SimpleImageProcessor.class.getSimpleName(),
					"show() position = " + paramSubViewHolder.getPosition()
							+ " -->> ");
		if (paramSubViewHolder.getAdapter() != null) {
			int adapterViewChildCount = paramSubViewHolder.getAdapter()
					.getAdapterHelper().getAdapterView().getChildCount();
			if (paramSubViewHolder.getItemView() == null
					&& adapterViewChildCount < 1) {
				if (Log.D)
					Log.d(SimpleImageProcessor.class.getSimpleName(),
							"show() sleep 200 -->> ");
				try {
					Thread.sleep(200L);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (Log.D)
			Log.d(SimpleImageProcessor.class.getSimpleName(),
					"show() uiThread true -->> ");
		provideUIRunnable(paramSubViewHolder, paramImageState).run();
		switch (paramImageState.getState()) {
		case GlobalImageCache.STATE_NONE:
			if (Log.D)
				Log.d(SimpleImageProcessor.class.getSimpleName(),
						"STATE_NONE position = "
								+ paramSubViewHolder.getPosition() + " -->> ");
			loadImage(paramSubViewHolder, paramImageState);

		}
	}

	protected UIRunnable provideUIRunnable(
			SimpleBeanAdapter.SubViewHolder paramSubViewHolder,
			GlobalImageCache.ImageState paramImageState) {
		return new UIRunnable(paramSubViewHolder, paramImageState);
	}
}