package com.jingdong.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.jingdong.common.http.HttpResponse;
import com.jingdong.common.utils.cache.GlobalImageCache;
import com.novoda.imageloader.core.bitmap.BitmapUtil;

import java.io.File;

public class ImageUtil {

	public static final float IMAGE_MAX_HEIGHT = 666.0F;
	public static final float IMAGE_MAX_WIDTH = 666.0F;

	private static Bitmap createBitmap(InputWay paramInputWay, int width,
			int height) {
		Bitmap targetBitmap = null;
		if (Log.D)
			Log.d(ImageUtil.class.getSimpleName(), "createBitmap() width="
					+ width + " height=" + height + " -->> ");
		if (width > DPIUtil.dip2px(IMAGE_MAX_WIDTH))
			width = DPIUtil.dip2px(IMAGE_MAX_WIDTH);
		if (height > DPIUtil.dip2px(IMAGE_MAX_HEIGHT))
			height = DPIUtil.dip2px(IMAGE_MAX_HEIGHT);
		if ((width == 0) && (height == 0)) {
			width = DPIUtil.dip2px(IMAGE_MAX_WIDTH);
			height = DPIUtil.dip2px(IMAGE_MAX_HEIGHT);
		}
		BitmapUtil localBitmapUtil = new BitmapUtil();
		if (Log.D)
			Log.d(ImageUtil.class.getSimpleName(),
					"createBitmap() bitmap -->> " + targetBitmap);
		if (paramInputWay.getFile() != null) {
                targetBitmap = localBitmapUtil.decodeFileAndScale(
                        paramInputWay.getFile(), width, height, false);
		}
		return targetBitmap;
	}
	
	public static Bitmap createBitmap(InputWay paramInputWay,
			GlobalImageCache.BitmapDigest paramBitmapDigest) {
		if (paramBitmapDigest.isLarge()) {
			if (Log.D)
				Log.d(ImageUtil.class.getSimpleName(),
						"createBitmap() bitmapDigest isLarge let cleanMost  -->> ");
			GlobalImageCache.getLruBitmapCache().cleanMost();
		}
		return createBitmap(paramInputWay, paramBitmapDigest.getWidth(),
				paramBitmapDigest.getHeight());
	}

	public static Bitmap getBitmap(HttpResponse paramHttpResponse,
			int width, int height) {
		byte[] arrayOfByte = paramHttpResponse.getInputData();
		File localFile = paramHttpResponse.getSaveFile();
		InputWay localInputWay = new InputWay();
		localInputWay.setByteArray(arrayOfByte);
		localInputWay.setFile(localFile);
		return createBitmap(localInputWay, width, height);
	}
	
	private static Bitmap drawableToBitmap(Drawable drawable)
    {
        int i = drawable.getIntrinsicWidth();
        int j = drawable.getIntrinsicHeight();
        android.graphics.Bitmap.Config config;
        Bitmap bitmap;
        Canvas canvas;
        if(drawable.getOpacity() != -1)
            config = android.graphics.Bitmap.Config.ARGB_8888;
        else
            config = android.graphics.Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(i, j, config);
        canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, i, j);
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getBitmapFromByteArray(byte abyte0[], int i, int j)
    {
        android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(abyte0, 0, abyte0.length, options);
        options.inJustDecodeBounds = false;
        boolean flag = false;
        if(options.outHeight < options.outWidth)
            flag = true;
        int k;
        if(flag)
            k = options.outHeight / j;
        else
            k = options.outWidth / i;
        if(k <= 0)
            k = 1;
        options.inSampleSize = k;
        return BitmapFactory.decodeByteArray(abyte0, 0, abyte0.length, options);
    }

    public static Bitmap getBitmapFromFile(String s, int i, int j)
    {
        android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(s, options);
        options.inJustDecodeBounds = false;
        boolean flag = false;
        if(options.outHeight < options.outWidth)
            flag = true;
        int k;
        if(flag)
            k = options.outHeight / j;
        else
            k = options.outWidth / i;
        if(k <= 0)
            k = 1;
        options.inSampleSize = k;
        return BitmapFactory.decodeFile(s, options);
    }

    public static Bitmap getRoundedCornerBitmap(Drawable drawable, float f)
    {
        Bitmap bitmap = drawableToBitmap(drawable);
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectf = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff424242);
        canvas.drawRoundRect(rectf, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmap1;
    }

    public static Bitmap toRoundCorner(Bitmap bitmap)
    {
        return toRoundCorner(bitmap, 6);
    }

    public static Bitmap toRoundCorner(Bitmap bitmap, int i)
    {
        int j = DPIUtil.dip2px(i);
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectf = new RectF(rect);
        float f = j;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff424242);
        canvas.drawRoundRect(rectf, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();
        return bitmap1;
    }

	public static class InputWay {
		private byte[] byteArray;
		private File file;

		public static InputWay createInputWay(HttpResponse paramHttpResponse) {
			InputWay localInputWay = new InputWay();
			localInputWay.setByteArray(paramHttpResponse.getInputData());
			localInputWay.setFile(paramHttpResponse.getSaveFile());
			return localInputWay;
		}

		public byte[] getByteArray() {
			return this.byteArray;
		}

		public File getFile() {
			return this.file;
		}

		public void setByteArray(byte[] paramArrayOfByte) {
			this.byteArray = paramArrayOfByte;
		}

		public void setFile(File paramFile) {
			this.file = paramFile;
		}

		public String toString() {
			return "InputWay[file="
					+ this.file +", byteArray=" + this.byteArray + "]";
		}
	}
}