package com.hyrt.cnp.base.account.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.hyrt.cnp.R;

/**
 * Created by Zoe on 2014-04-28.
 * 提示框
 */

public class AlertUtils {
    private static AlertUtils mAlertUtils;

    public static AlertUtils getInstance(){
        if(mAlertUtils == null){
            mAlertUtils = new AlertUtils();
        }
        return mAlertUtils;
    }

    public void showCenterToast(Context context, String msg){
        if(context == null){
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
