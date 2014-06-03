package com.hyrt.cnp.base.account.requestListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.hyrt.cnp.base.account.ui.LightAlertDialog;
import com.hyrt.cnp.base.account.ui.LightProgressDialog;
import com.hyrt.cnp.base.account.utils.AlertUtils;
import com.hyrt.cnp.base.account.utils.LogHelper;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.lang.ref.SoftReference;

import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Created by yepeng on 14-1-9.
 *
 * 所有请求回调继承此父类，可转圈,具体使用例子可参照
 * https://github.com/hyrtyp/CNP_Account/blob/master/src/main/java/com/hyrt/cnp/account/requestListener/LoginRequestListener.java
 */
public abstract class BaseRequestListener implements RequestListener {

    protected SoftReference<Activity> context;

    /**
     * Progress dialog last displayed
     */
    protected AlertDialog progress;

    /**
     * @param context
     */
    protected BaseRequestListener(Activity context) {
        this.context = new SoftReference<Activity>(context);
    }

    /**
     * Dismiss and clear progress dialog field
     */
    protected void dismissProgress() {
        if (progress != null) {
            progress.dismiss();
            progress = null;
        }
    }

    /**
     * Show indeterminate progress dialog with given message
     *
     * @param message
     */
    protected void showIndeterminate(final CharSequence message) {
        if(context != null && context.get() != null){
            dismissProgress();
            progress = LightProgressDialog.create(context.get(), message);
            progress.show();
        }
    }

    /**
     * Show indeterminate progress dialog with given message
     *
     * @param resId
     */
    protected void showIndeterminate(final int resId) {
        if(context != null && context.get() != null){
            dismissProgress();
            progress = LightProgressDialog.create(context.get(), resId);
            progress.show();
        }
    }

    /**
     * Get string from context resources
     *
     * @param resId
     * @return string
     */
    protected String getString(int resId) {
        return context.get().getString(resId);
    }


    @Override
    public void onRequestFailure(SpiceException e) {
        String msg = e.getMessage();
        if(msg.contains("Network is not available")){
            AlertUtils.getInstance().showCenterToast(context.get(), "网络加载失败！");
        }
        LogHelper.i("tag", "msg:" + e.getMessage());
        dismissProgress();
    }

    @Override
    public void onRequestSuccess(Object o) {
        dismissProgress();
    }



    public abstract BaseRequestListener start();

    /**
     * 显示弹出提示
     * @param titleId
     * @param contentId
     */
    private AlertDialog mDialog;
    protected void showMessage(int titleId,int contentId) {
        if(context != null && context.get() != null && (mDialog == null || !mDialog.isShowing())){
//            if(mDialog != null){
//                mDialog.dismiss();
//                mDialog = null;
//            }
            mDialog = LightAlertDialog.create(context.get());
            mDialog.setTitle(titleId);
            mDialog.setMessage(context.get().getResources().getString(contentId));
            mDialog.setButton(BUTTON_POSITIVE, getString(android.R.string.ok),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            mDialog.setCanceledOnTouchOutside(true);
            if(context.get()!=null&&!context.get().isFinishing())
            mDialog.show();
        }
    }

}
