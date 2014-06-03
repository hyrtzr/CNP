package com.jingdong.common.utils.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.hyrt.cnp.R;
import com.jingdong.common.utils.DPIUtil;
import com.jingdong.common.utils.Log;

/**
 * 自定义异常图片类
 * @author yepeng
 *
 */
public class ExceptionDrawable extends Drawable {
	private Bitmap bitmap = null;
	private int height;
	private int width;
	protected Paint paint = new Paint();
	private final String text;

	public ExceptionDrawable(Context paramContext, String paramString) {
		this.paint.setColor(Color.RED);
		this.paint.setStyle(Paint.Style.FILL);
		int textSize;
		if (DPIUtil.isBigScreen())
			textSize = 12;
		else
			textSize = DPIUtil.dip2px(12.0F);
		this.paint.setTextSize(textSize);
		this.paint.setTextAlign(Paint.Align.CENTER);
		this.paint.setAntiAlias(true);
		this.text = paramString;
		if (bitmap ==null) {
			bitmap = ((BitmapDrawable) paramContext.getResources().getDrawable(
					R.drawable.spinner_inner)).getBitmap();
			width = bitmap.getWidth();
			height = bitmap.getHeight();
		}
	}

	public void draw(Canvas paramCanvas) {
		Rect localRect = getBounds();
        int centerX = localRect.centerX();
        int centerY = localRect.centerY();
		if (Log.D) {
			Log.d(ExceptionDrawable.class.getSimpleName(),
					"draw bounds.width()-->> " + localRect.width());
			Log.d(ExceptionDrawable.class.getSimpleName(),
					"draw bounds.height()-->> " + localRect.height());
			Log.d(ExceptionDrawable.class.getSimpleName(),
					"draw bounds.left-->> " + localRect.left);
			Log.d(ExceptionDrawable.class.getSimpleName(),
					"draw bounds.top-->> " + localRect.top);
		}
		float x = localRect.right - localRect.width() / 2;
		float y = localRect.bottom - localRect.height() / 2;
		paramCanvas.drawText(this.text, x, y, this.paint);
		if (bitmap != null){

            RectF rectF = new RectF(centerX*3/4,centerY-centerX*1/4,centerX*5/4,centerY+centerX*1/4);
            paramCanvas.drawBitmap(bitmap, null,
                    rectF,
                    this.paint);
        }
	}

	public int getOpacity() {
		return 0;
	}

	public void setAlpha(int paramInt) {
	}

	public void setColorFilter(ColorFilter paramColorFilter) {
	}
}