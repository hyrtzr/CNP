package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;
import android.util.Log;

import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by HY on 2014-04-24.
 */
public class AddPhotoCompleteRequestListener extends BaseRequestListener{

    private RequestListener mListener;

    public AddPhotoCompleteRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestSuccess(Object o) {
        super.onRequestSuccess(o);
        BaseTest result= (BaseTest)o;
        Log.i("AddPhoto", "result:"+result.getCode()+"-"+result.getMsg());
        if(result != null){
            if(mListener != null && result.getCode().equals("200")){
                mListener.onRequestSuccess(o);
            }
        }else{
            if(mListener != null){
                mListener.onRequestFailure(null);
            }
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
        showIndeterminate("上传中...");
        return this;
    }

    public void setListener(RequestListener listener){
        this.mListener = listener;
    }

    public static interface RequestListener{
        public void onRequestSuccess(Object o);
        public void onRequestFailure(SpiceException e);
    }
}
