package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;
import android.content.Context;

import com.hyrt.cnp.base.account.model.BaseStringArray;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by Zoe on 2014-05-14.
 */
public class BaseStringArrayRequestListener extends BaseRequestListener{

    private OnRequestListener mListener;

    public BaseStringArrayRequestListener(Activity context) {
        super(context);
        if(mListener != null){
            mListener = null;
        }
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        if(mListener != null){
            mListener.onRequestFailure();
        }
    }

    @Override
    public void onRequestSuccess(Object o) {
        if(mListener != null){
            if(o != null){
                BaseStringArray bsa = (BaseStringArray) o;
                if("200".equals(bsa.getCode())){
                    mListener.onRequestSuccess(bsa);
                }else{
                    mListener.onRequestFailure();
                }
            }else{
                mListener.onRequestFailure();
            }
        }

        super.onRequestSuccess(o);
    }

    @Override
    public BaseRequestListener start() {
        return this;
    }

    public void setListener(OnRequestListener listener){
        this.mListener = listener;
    }

    public static interface OnRequestListener{
        public void onRequestSuccess(BaseStringArray bsa);
        public void onRequestFailure();

    }
}
