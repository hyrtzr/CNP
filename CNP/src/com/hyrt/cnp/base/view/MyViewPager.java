package com.hyrt.cnp.base.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Zoe on 2014-04-14.
 */
public class MyViewPager extends ViewPager{

    private boolean scrollable = true;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollable(boolean enable){
        this.scrollable = enable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if(scrollable){
            return super.onInterceptTouchEvent(ev);
//        }else{
//            return false;
//        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

}
