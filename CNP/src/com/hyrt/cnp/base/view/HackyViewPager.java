package com.hyrt.cnp.base.view;

/**
 * Created by GYH on 14-2-19.
 */
import com.hyrt.cnp.base.account.utils.LogHelper;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HackyViewPager extends ViewPager {
	
	private TouchCallback mCallback;

    /*public HackyViewPager(Context context) {
        super(context);
        mCallback = null;
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCallback = null;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }*/
    
	private boolean isLocked;

    public HackyViewPager(Context context) {
        super(context);
        isLocked = false;
        mCallback = null;
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        isLocked = false;
        mCallback = null;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isLocked) {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isLocked) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    public void toggleLock() {
        isLocked = !isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean isLocked() {
        return isLocked;
    }
    
    private long downTime;
    private float downX;
    private float downY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	// TODO Auto-generated method stub
    	if(ev.getAction() == MotionEvent.ACTION_DOWN){
    		downTime = System.currentTimeMillis();
    		downX = ev.getX();
    		downY = ev.getY();
//			LogHelper.i("tag", "按下");
		}else if(ev.getAction() == MotionEvent.ACTION_UP){
			long upTime = System.currentTimeMillis() - downTime;
			float upX = ev.getX();
			float upY = ev.getY();
			float offsetX = Math.abs(Math.abs(downX) - Math.abs(upX));
			float offsetY = Math.abs(Math.abs(downY) - Math.abs(upY));
//			LogHelper.i("tag", "弹起 upTime:"+upTime+" offsetX:"+offsetX+" offsetY:"+offsetY);
			if(upTime<150 && offsetX < 10 && offsetY < 10){
				if(mCallback != null){
					mCallback.onTouch();
				}
				return true;
			}
		}
    	return super.dispatchTouchEvent(ev);
    }
    
    public void setCallback(TouchCallback callback){
    	this.mCallback = callback;
    }
    
    public static interface TouchCallback{
    	public void onTouch();
    }

}
