package com.hyrt.cnp.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by GYH on 14-1-23.
 */
public class Mylistview extends ListView{

    public Mylistview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Mylistview(Context context) {
        super(context);
    }

    public Mylistview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
