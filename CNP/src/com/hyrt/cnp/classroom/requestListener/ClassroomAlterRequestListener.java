package com.hyrt.cnp.classroom.requestListener;

import android.app.Activity;
import android.content.Context;

import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.base.account.utils.AlertUtils;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by HY on 2014-05-19.
 */
public class ClassroomAlterRequestListener extends BaseRequestListener{

    private Context context;
    private RequestListener mListener;

    public ClassroomAlterRequestListener(Activity context) {
        super(context);
        this.context = context;
        this.mListener = null;
    }

    @Override
    public BaseRequestListener start() {
        showIndeterminate("修改中...");
        return this;
    }

    @Override
    public void onRequestSuccess(Object o) {
        super.onRequestSuccess(o);
        if(mListener != null){
            if(o != null){
                BaseTest bt = (BaseTest) o;
                if("200".equals(bt.getCode())){
                    mListener.onRequestSuccess(bt);
                }else{
                    mListener.onRequestFailure();
                    AlertUtils.getInstance().showCenterToast(context, "修改失败");
                }
            }else{
                mListener.onRequestFailure();
                AlertUtils.getInstance().showCenterToast(context, "修改失败");
            }
        }

    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        if(mListener != null){
            mListener.onRequestFailure();
        }
        AlertUtils.getInstance().showCenterToast(context, "修改失败");
    }

    public void setListener(RequestListener listener){
        this.mListener = listener;
    }

    public static interface RequestListener{
        public void onRequestSuccess(BaseTest data);
        public void onRequestFailure();
    }
}
