package com.jingdong.app.pad.adapter.helper;

import android.graphics.Bitmap;

import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.app.pad.adapter.SimpleBeanAdapter;
import com.jingdong.common.http.HttpError;
import com.jingdong.common.http.HttpGroup;
import com.jingdong.common.http.HttpResponse;
import com.jingdong.common.utils.ImageUtil;
import com.jingdong.common.utils.Log;
import com.jingdong.common.utils.cache.GlobalImageCache;

/**
 * 从服务器端加载图片,加载完毕后回调ImageProcessor.create方法 由SimpleImagePressor触发
 * 
 */
public class ImageLoader implements HttpGroup.OnCommonListener {

	private MySimpleAdapter.ImageProcessor imageProcessor;
	private GlobalImageCache.ImageState imageState;
	private SimpleBeanAdapter.SubViewHolder subViewHolder;

	public ImageLoader(SimpleBeanAdapter.SubViewHolder paramSubViewHolder,
			GlobalImageCache.ImageState paramImageState,
			MySimpleAdapter.ImageProcessor paramImageProcessor) {
		this.imageState = paramImageState;
		this.imageProcessor = paramImageProcessor;
		this.subViewHolder = new SimpleBeanAdapter.SubViewHolder();
		this.subViewHolder.setPosition(paramSubViewHolder.getPosition());
		this.subViewHolder.setItemData(paramSubViewHolder.getItemData());
		this.subViewHolder.setShowData(paramSubViewHolder.getShowData());
		this.subViewHolder.setItemViewId(paramSubViewHolder.getItemViewId());
		this.subViewHolder.setKeepVisibleBitmap(paramSubViewHolder
				.isKeepVisibleBitmap());
		this.subViewHolder.setAdapter(paramSubViewHolder.getAdapter());
	}

	/**
	 * 加载图片
	 */
	public void load() {
		GlobalImageCache.BitmapDigest localBitmapDigest = GlobalImageCache
				.getBitmapDigest(this.imageState);
		if (localBitmapDigest != null) {
			this.imageState.loading();
			String str = localBitmapDigest.getUrl();
			if (Log.D) {
				Log.d(ImageLoader.class.getSimpleName(), "load() url -->> "
						+ str);
				Log.d(ImageLoader.class.getSimpleName(),
						"load() position -->> "
								+ this.subViewHolder.getPosition());
			}

            if(subViewHolder.getAdapter().getContext()==null){
                subViewHolder.getAdapter().getBaseActivity().executeImage(str,this);
            }else{
                subViewHolder.getAdapter().getContext().executeImage(str, this);
            }
		}
	}

	/**
	 * 加载完毕后，通知ImageProcessor附加数据
	 */
	public void onEnd(final HttpResponse paramHttpResponse) {

		if (Log.D)
			Log.d(ImageLoader.class.getSimpleName(), "onEnd() position -->> "
					+ ImageLoader.this.subViewHolder.getPosition());
		GlobalImageCache.BitmapDigest localBitmapDigest = GlobalImageCache
				.getBitmapDigest(ImageLoader.this.imageState);
		if (localBitmapDigest == null) {
			if (Log.D)
				Log.d(ImageLoader.class.getSimpleName(),
						"onEnd() bitmapDigest is null position -->> "
								+ ImageLoader.this.subViewHolder.getPosition());
			gc();
		} else {
			Bitmap localBitmap = ImageLoader.this.imageProcessor.create(
					ImageUtil.InputWay.createInputWay(paramHttpResponse),
					localBitmapDigest);
			if (localBitmap == null) {
				if (Log.D)
					Log.d(ImageLoader.class.getSimpleName(),
							"onEnd() bitmap is null position -->> "
									+ ImageLoader.this.subViewHolder
											.getPosition());
				ImageLoader.this.imageState.none();
				gc();
			} else {
				ImageLoader.this.imageState.success(localBitmap);
				ImageLoader.this.imageProcessor.show(
						ImageLoader.this.subViewHolder,
						ImageLoader.this.imageState);
				gc();
			}
		}
	}

	public void onError(HttpError paramHttpError) {
		if (Log.D)
			Log.d(ImageLoader.class.getSimpleName(), "onError() position -->> "
					+ this.subViewHolder.getPosition());
		this.imageState.failure();
        if(subViewHolder.getAdapter().getCount()>subViewHolder.getPosition()){
            this.imageProcessor.show(this.subViewHolder, this.imageState);
        }
		gc();
	}

	public void gc() {
		this.imageState = null;
		this.imageProcessor = null;
		this.subViewHolder = null;
	}
}
