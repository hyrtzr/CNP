/**
 * Copyright 2012 Novoda Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.novoda.imageloader.core.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jingdong.common.utils.Log;
import com.jingdong.common.utils.cache.GlobalImageCache;
import com.jingdong.common.utils.cache.GlobalImageCache.BitmapDigest;
import com.novoda.imageloader.core.cache.util.LruCache;

import net.oschina.app.common.Util;

import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Iterator;

/**
 * LruBitmapCache overcome the issue with soft reference cache. It is in fact
 * keeping all the certain amount of images in memory. The size of the memory
 * used for cache depends on the memory that the android SDK provide to the
 * application and the percentage specified (default percentage is 25%).
 */
public class LruBitmapCache implements CacheManager {

	public static final int DEFAULT_MEMORY_CACHE_PERCENTAGE = 25;
	private static final int DEFAULT_MEMORY_CAPACITY_FOR_DEVICES_OLDER_THAN_API_LEVEL_4 = 12;
	private LruCache<BitmapDigest, Bitmap> cache;
    private HashSet<SoftReference<Bitmap>> mReusableBitmaps;
	private int capacity;

	/**
	 * It is possible to set a specific percentage of memory to be used only for
	 * images.
	 * 
	 * @param context
	 * @param percentageOfMemoryForCache
	 *            1-80
	 */
	public LruBitmapCache(Context context, int percentageOfMemoryForCache) {
		int memClass = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		this.capacity = calculateCacheSize(memClass, percentageOfMemoryForCache);
        if (Util.hasInBitmap()) {
            this.mReusableBitmaps = new HashSet<SoftReference<Bitmap>>();
        }
		reset();
	}

	/**
	 * Setting the default memory size to 25% percent of the total memory
	 * available of the application.
	 * 
	 * @param context
	 */
	public LruBitmapCache(Context context) {
		this(context, DEFAULT_MEMORY_CACHE_PERCENTAGE);
	}

	/**
	 * Empty constructor for testing purposes
	 */
	protected LruBitmapCache() {
	}

	private void reset() {
		if (cache != null) {
			cache.evictAll();
		}
		cache = new LruCache<BitmapDigest, Bitmap>(capacity) {

			protected void entryRemoved(boolean isRemove,
					GlobalImageCache.BitmapDigest paramAnonymousBitmapDigest,
					Bitmap oldBitmp, Bitmap newBitmap) {
				if (Log.D)
					Log.d(LruBitmapCache.class.getName(),
							"entryRemoved() bitmapDigest -->> "
									+ paramAnonymousBitmapDigest);
				if (paramAnonymousBitmapDigest.isInUsing())
					return;
                //if(Util.hasInBitmap()){
                if(false){
                    // We're running on Honeycomb or later, so add the bitmap
                    //to a SoftReference set for possible use with inBitmap later.
                    mReusableBitmaps.add(new SoftReference<Bitmap>(newBitmap));
                }else if (paramAnonymousBitmapDigest.isAllowRecycle()){
                    try{
                        oldBitmp.recycle();
                    }catch (Throwable throwable){
                        throwable.printStackTrace();
                    }
                }
				if (isRemove)
					GlobalImageCache.remove(paramAnonymousBitmapDigest);
			}

			@Override
			protected long sizeOf(BitmapDigest bitmapDigest, Bitmap bitmap) {
				if (Log.D)
					Log.d(LruBitmapCache.class.getName(),
							"sizeOf() bitmapRowBytes=" + bitmap.getRowBytes()
									+ " bitmapWidth=" + bitmap.getWidth()
									+ " bitmapHeight=" + bitmap.getHeight()
									+ " size=" + 4L
									* (bitmap.getWidth() * bitmap.getHeight())
									+ " -->> ");
				return 4L * (bitmap.getWidth() * bitmap.getHeight());
			}
		};
	}

	@Override
	public Bitmap get(BitmapDigest bitmapDigest) {
		return cache.get(bitmapDigest);
	}

	@Override
	public void put(BitmapDigest bitmapDigest, Bitmap bmp) {
		cache.put(bitmapDigest, bmp);
	}

	@Override
	public void clean() {
		reset();
	}

    /**
     * 当加载大图片时，调用此方法，压缩内存
     */
	public void cleanMost() {
		long l = Math.round(0.1D * this.capacity);
		if (Log.D)
			Log.d(LruBitmapCache.class.getName(), "cleanMost() maxSize -->> "
					+ l);
		this.cache.evict(l);
	}

    /**
     * 计算要持有的内存
     * @param memClass app可支配的总内存
     * @param percentageOfMemoryForCache 要支配内存的百分比
     * @return
     */
	public int calculateCacheSize(int memClass, int percentageOfMemoryForCache) {
		if (memClass == 0) {
			memClass = DEFAULT_MEMORY_CAPACITY_FOR_DEVICES_OLDER_THAN_API_LEVEL_4;
		}
		if (percentageOfMemoryForCache < 0) {
			percentageOfMemoryForCache = 0;
		}
		if (percentageOfMemoryForCache > 81) {
			percentageOfMemoryForCache = 80;
		}
		int capacity = (int) ((memClass * percentageOfMemoryForCache * 1024L * 1024L) / 100L);
		if (capacity <= 0) {
			capacity = 1024 * 1024 * 4;
		}
//		if (capacity > 0x800000)
//			capacity = 0x800000;
		return capacity;
	}

    /**
     * 获取匹配的位图内存
     * @param options 复用内存的配置清单
     * @return
     */
    public Bitmap getBitmapFromReusableSet(BitmapFactory.Options options) {
        Bitmap bitmap = null;

        if (mReusableBitmaps != null && !mReusableBitmaps.isEmpty()) {
            final Iterator<SoftReference<Bitmap>> iterator
                    = mReusableBitmaps.iterator();
            Bitmap item;

            while (iterator.hasNext()) {
                item = iterator.next().get();

                if (null != item && item.isMutable()) {
                    // Check to see it the item can be used for inBitmap.
                    if (canUseForInBitmap(item, options)) {
                        bitmap = item;

                        // Remove from reusable set so it can't be used again.
                        iterator.remove();
                        break;
                    }
                } else {
                    // Remove from the set if the reference has been cleared.
                    iterator.remove();
                }
            }
        }
        return bitmap;
    }

    /**
     * 判断位图内存区域是否可复用
     * @param candidate 可以复用的bitmap
     * @param targetOptions 复用内存的配置清单
     * @return
     */
    private static boolean canUseForInBitmap(
            Bitmap candidate, BitmapFactory.Options targetOptions) {
        int width = targetOptions.outWidth / targetOptions.inSampleSize;
        int height = targetOptions.outHeight / targetOptions.inSampleSize;

        // Returns true if "candidate" can be used for inBitmap re-use with
        // "targetOptions".
        return candidate.getWidth() == width && candidate.getHeight() == height;
    }

}
