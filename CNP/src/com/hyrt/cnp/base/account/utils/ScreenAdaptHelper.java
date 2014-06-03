package com.hyrt.cnp.base.account.utils;

import android.content.Context;

/**
 * Created by Zoe on 2014-04-02.
 */
public class ScreenAdaptHelper {

    private Context context;
    private static ScreenAdaptHelper mScreenAdaptHelper;

    private ScreenAdaptHelper(Context context) {
        this.context = context;
    }

    public static ScreenAdaptHelper getInstance(Context context){
        if(mScreenAdaptHelper == null){
            mScreenAdaptHelper = new ScreenAdaptHelper(context);
        }
        return mScreenAdaptHelper;
    }

    /**
     *根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int pxToDip(int pxValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dipToPx(int dipValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
