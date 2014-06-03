package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.dynamic.ui.DynamicCommentActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by Zoe on 2014-04-10.
 */
public class SendDynamicRequestListener extends BaseRequestListener{

    private requestListener mListener;

    private final String TAG = "SendDynamicRequestListener";

    public SendDynamicRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestSuccess(Object o) {
        super.onRequestSuccess(o);
        BaseTest result= (BaseTest)o;
        if(result != null){
            android.util.Log.i("tag", "resultCode:"+result.getCode() +" msg:"+result.getMsg()+" data:"+result.getData());
        }
        if(mListener != null){
            mListener.onRequestSuccess(o);
        }
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        if(mListener != null){
            mListener.onRequestFailure(e);
        }
    }

    @Override
    public BaseRequestListener start() {
        showIndeterminate("发送中...");
        return this;
    }

    public void setListener(requestListener listener){
        this.mListener = listener;
    }

    public static interface requestListener{
        public void onRequestSuccess(Object o);
        public void onRequestFailure(SpiceException e);
    }
}
