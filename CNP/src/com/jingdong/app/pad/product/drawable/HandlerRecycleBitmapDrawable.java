package com.jingdong.app.pad.product.drawable;

/**
 * Created by yepeng on 13-8-8.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.NinePatch;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import com.jingdong.common.utils.ui.ExceptionDrawable;

public class HandlerRecycleBitmapDrawable extends ExceptionDrawable {

    Context context = null;
    private Bitmap bitmap = null;
    private Rect bitmapRect = null;
    private NinePatch np;

    public HandlerRecycleBitmapDrawable(Bitmap paramBitmap, Context paramContext) {
        super(paramContext, "");
        setBitmap(paramBitmap);
        this.context = paramContext;
        this.bitmapRect = new Rect();
    }

    public void draw(Canvas paramCanvas) {
        paramCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        if (this.np != null)
            this.np.draw(paramCanvas, getBounds());
        if ((this.bitmap != null) && (!this.bitmap.isRecycled())){
            try {
                Rect localRect = copyBounds();
                localRect.set(localRect.left, localRect.top, localRect.right, localRect.bottom);
                this.bitmapRect.set(0, 0, this.bitmap.getWidth(), this.bitmap.getHeight());
                paramCanvas.drawBitmap(this.bitmap, this.bitmapRect, localRect, this.paint);
            } catch (Throwable localThrowable) {
                localThrowable.printStackTrace();
            }
        }else{
            super.draw(paramCanvas);
        }

    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap paramBitmap) {
        this.bitmap = paramBitmap;
    }

    public int getOpacity() {
        return 0;
    }

    public void setAlpha(int paramInt) {
    }

    public void setBackGround(Bitmap paramBitmap) {
        if ((paramBitmap != null) && (!paramBitmap.isRecycled())) {
            this.np = new NinePatch(paramBitmap, paramBitmap.getNinePatchChunk(), null);
        } else {
            this.np = null;
        }

    }

    public void setColorFilter(ColorFilter paramColorFilter) {
    }
}
