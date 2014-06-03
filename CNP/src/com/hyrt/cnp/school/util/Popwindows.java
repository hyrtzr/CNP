package com.hyrt.cnp.school.util;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.hyrt.cnp.R;

/**
 * Created by GYH on 14-1-13.
 */
public class Popwindows {

    private Context context;

    private ImageView popimge;
    private PopupWindow pop;


    public ImageView getPopimge() {
        return popimge;
    }


    public Popwindows(Context context){
        this.context=context;
    }

    public PopupWindow getPopupWindow(){
        LayoutInflater inflater = LayoutInflater.from(context);
        // 引入窗口配置文件
        View view = inflater.inflate(R.layout.layout_popwindwos, null);
        // 创建PopupWindow对象
        pop = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, false);
        popimge = (ImageView)view.findViewById(R.id.pop_img);
        // 需要设置一下此参数，点击外边可消失
        pop.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
        return pop;
    }


}
