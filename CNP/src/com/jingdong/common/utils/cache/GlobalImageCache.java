package com.jingdong.common.utils.cache;

import android.graphics.Bitmap;

import com.jingdong.common.utils.Log;
import com.novoda.imageloader.core.cache.LruBitmapCache;
import java.util.HashMap;
import java.util.Map;

import net.oschina.app.AppContext;

public class GlobalImageCache {
	
	public static final int STATE_FAILURE = 2;
	public static final int STATE_LOADING = 1;
	public static final int STATE_NONE = 0;
	public static final int STATE_SUCCESS = 3;
	private static final Map<ImageState, BitmapDigest> digestMap = new HashMap<ImageState, BitmapDigest>();
	private static final Map<BitmapDigest, ImageState> imageMap = new HashMap<BitmapDigest, ImageState>();
	private static LruBitmapCache lruBitmapCache;

	/**
	 * 请求图片请求时，获取ImageState对象，图片加载完毕调用该对象的success方法--》加入到缓存中
	 * @param paramBitmapDigest
	 * @return
	 */
	public static ImageState getImageState(BitmapDigest paramBitmapDigest) {
		ImageState localImageState = (ImageState) imageMap
				.get(paramBitmapDigest);
		if (localImageState == null) {
            localImageState = new ImageState();
			imageMap.put(paramBitmapDigest, localImageState);
			digestMap.put(localImageState, paramBitmapDigest);
		}
		return localImageState;
	}

	public static BitmapDigest getBitmapDigest(ImageState paramImageState) {
		return (BitmapDigest) digestMap.get(paramImageState);
	}

	public static void remove(BitmapDigest paramBitmapDigest) {
		if (Log.D)
			Log.d(GlobalImageCache.class.getName(),
					"remove() bitmapDigest -->> " + paramBitmapDigest);
		ImageState localImageState = (ImageState) imageMap
				.remove(paramBitmapDigest);
		digestMap.remove(localImageState);
	}

	public static class BitmapDigest {

		private boolean allowRecycle = true;
		private int height;
		private boolean inUsing;
		private boolean keepVisibleBitmap;
		private boolean large;
		private Map<String, Object> moreParameter;
		private int position;
		private int round;
		private String url;
		private int width;

		public BitmapDigest(String paramString) {
			this.url = paramString;
		}

		public boolean equals(Object paramObject) {
			boolean isEquals = true;
			if (this == paramObject)
				isEquals = true;
			else if (paramObject == null) {
				isEquals = false;
			} else if (getClass() != paramObject.getClass()) {
				isEquals = false;
			} else {
				BitmapDigest localBitmapDigest = (BitmapDigest) paramObject;
				if (this.allowRecycle != localBitmapDigest.allowRecycle)
					isEquals = false;
				else if (this.height != localBitmapDigest.height)
					isEquals = false;
				else if (this.moreParameter == null) {
					if (localBitmapDigest.moreParameter != null)
						isEquals = false;
				} else if (!this.moreParameter
						.equals(localBitmapDigest.moreParameter))
					isEquals = false;
				else if (this.position != localBitmapDigest.position)
					isEquals = false;
				else if (this.round != localBitmapDigest.round)
					isEquals = false;
				else if (this.url == null) {
					if (localBitmapDigest.url != null)
						isEquals = false;
				} else if (!this.url.equals(localBitmapDigest.url))
					isEquals = false;
				else if (this.width != localBitmapDigest.width)
					isEquals = false;
			}
			return isEquals;
		}

		public int getHeight() {
			return this.height;
		}

		public Object getMoreParameter(String paramString) {
			return this.moreParameter.get(paramString);
		}

		public int getPosition() {
			return this.position;
		}

		public int getRound() {
			return this.round;
		}

		public String getUrl() {
			return this.url;
		}

		public int getWidth() {
			return this.width;
		}

		public int hashCode() {
			int hashValue;
			int moreParameter = 0;
			int url = 0;
			int initHashValue;
			if (allowRecycle) {
				initHashValue = 31 * (1231 + 31);
			} else {
				initHashValue = 31 * (1115 + 31);
			}
			int i2 = 31 * (31 * initHashValue + this.height);
			if (this.moreParameter != null)
				moreParameter = this.moreParameter.hashCode();
			int i3 = 31 * (31 * (31 * (i2 + moreParameter) + this.position) + this.round);
			if (this.url != null)
				url = this.url.hashCode();
			hashValue = 31 * (i3 + url) + this.width;
			return hashValue;
		}

		public boolean isAllowRecycle() {
			return this.allowRecycle;
		}

		public boolean isInUsing() {
			return this.inUsing;
		}

		public boolean isKeepVisibleBitmap() {
			return this.keepVisibleBitmap;
		}

		public boolean isLarge() {
			return this.large;
		}

		public void putMoreParameter(String paramString, Object paramObject) {
			if (this.moreParameter == null)
				this.moreParameter = new HashMap<String, Object>();
			this.moreParameter.put(paramString, paramObject);
		}

		public void setAllowRecycle(boolean paramBoolean) {
			this.allowRecycle = paramBoolean;
		}

		public void setHeight(int paramInt) {
			this.height = paramInt;
		}

		public void setInUsing(boolean paramBoolean) {
			this.inUsing = paramBoolean;
		}

		public void setKeepVisibleBitmap(boolean paramBoolean) {
			this.keepVisibleBitmap = paramBoolean;
		}

		public void setLarge(boolean paramBoolean) {
			this.large = paramBoolean;
		}

		public void setPosition(int paramInt) {
			this.position = paramInt;
		}

		public void setRound(int paramInt) {
			this.round = paramInt;
		}

		public void setUrl(String paramString) {
			this.url = paramString;
		}

		public void setWidth(int paramInt) {
			this.width = paramInt;
		}
	}

	public static class ImageState {

		private boolean available = true;
		private boolean canRecycle = false;
		private int mState = STATE_NONE;

		public void failure() {
			this.mState = STATE_FAILURE;
		}

		/**
		 * 获取缓存中的图片--》目的为附加图片到imageView上
		 * @return
		 */
		public Bitmap getBitmap() {
			Bitmap localBitmap = null;
			GlobalImageCache.BitmapDigest localBitmapDigest = GlobalImageCache
					.getBitmapDigest(this);
			if (localBitmapDigest != null) {
				localBitmap = GlobalImageCache.getLruBitmapCache().get(localBitmapDigest);
			}
			return localBitmap;
		}

		/**
		 * 获取缓存中的图片时，先获取此状态
		 * @return
		 */
		public int getState() {
			if ((this.mState == STATE_SUCCESS) && (getBitmap() == null))
				this.mState = STATE_NONE;
			return this.mState;
		}

		public boolean isAvailable() {
			return this.available;
		}

		public boolean isCanRecycle() {
			return this.canRecycle;
		}

		public void loading() {
			this.mState = STATE_LOADING;
		}

		public void none() {
			this.mState = STATE_NONE;
		}

		public void setAvailable(boolean paramBoolean) {
			this.available = paramBoolean;
			none();
		}

		public void setCanRecycle(boolean paramBoolean) {
			this.canRecycle = paramBoolean;
		}

		/**
		 * 加载图片完成后调用此方法，完成图片的缓存
		 * @param paramBitmap
		 */
		public void success(Bitmap paramBitmap) {
			if (Log.D)
				Log.d(GlobalImageCache.class.getName(), "success() b -->> "
						+ paramBitmap);

			GlobalImageCache.getLruBitmapCache().put(
					GlobalImageCache.getBitmapDigest(this), paramBitmap);
			this.mState = STATE_SUCCESS;
		}

		public String toString() {
			return "ImageState [bitmap=" + getBitmap() + ", mState="
					+ this.mState + "]";
		}
	}

	/**
	 * 获取全局缓存图片对象
	 * @return
	 */
	public static synchronized LruBitmapCache getLruBitmapCache() {
		if (lruBitmapCache == null)
			lruBitmapCache = new LruBitmapCache(AppContext.getInstance(), 30);
		return lruBitmapCache;
	}
}
