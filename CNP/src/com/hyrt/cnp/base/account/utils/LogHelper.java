package com.hyrt.cnp.base.account.utils;

import android.util.Log;

/**
 * Created by Zoe on 2014-05-12.
 */
public class LogHelper {
    public static void i(String TAG, String msg){
        Log.i(TAG, msg);
    }

    public static void i(String TAG, String msg, Throwable tr){
        Log.i(TAG, msg, tr);
    }

    public static void d(String TAG, String msg){
        Log.d(TAG, msg);
    }

    public static void d(String TAG, String msg, Throwable tr){
        Log.d(TAG, msg, tr);
    }

    public static void e(String TAG, String msg){
       Log.e(TAG, msg);
    }

    public static void e(String TAG, String msg, Throwable tr){
        Log.e(TAG, msg, tr);
    }

    public static void v(String TAG, String msg){
        Log.v(TAG, msg);
    }

    public static void v(String TAG, String msg, Throwable tr){
        Log.v(TAG, msg, tr);
    }

    public static void w(String TAG, String msg){
        Log.w(TAG, msg);
    }

    public static void w(String TAG, String msg, Throwable tr){
        Log.w(TAG, msg, tr);
    }
}
