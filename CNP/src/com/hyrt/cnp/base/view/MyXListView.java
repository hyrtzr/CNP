package com.hyrt.cnp.base.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by HY on 2014-05-15.
 */
public class MyXListView extends XListView{
    public MyXListView(Context context) {
        super(context);
    }

    public MyXListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyXListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
