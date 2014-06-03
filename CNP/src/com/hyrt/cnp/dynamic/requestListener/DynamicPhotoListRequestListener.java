package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.DynamicPhoto;
import com.hyrt.cnp.base.account.model.Photo;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by Zoe on 2014-04-12.
 */
public class DynamicPhotoListRequestListener extends BaseRequestListener{

    private RequestListener mListener;

    /**
     * @param context
     */
    public DynamicPhotoListRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        if(mListener != null){
            mListener.onRequestFailure(e);
        }
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){
            DynamicPhoto.Model result= (DynamicPhoto.Model)data;
            if(mListener != null){
                mListener.onRequestSuccess(result);
            }
        }else{
            if(mListener != null){
                mListener.onRequestSuccess(null);
            }
        }

    }

    @Override
    public DynamicPhotoListRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }

    public void setListener(RequestListener listener){
        this.mListener = listener;
    }

    public static interface RequestListener{
        public void onRequestSuccess(DynamicPhoto.Model data);
        public void onRequestFailure(SpiceException e);
    }
}
