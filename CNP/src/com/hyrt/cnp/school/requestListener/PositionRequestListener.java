package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.PositionInfo;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by Zoe on 2014-04-05.
 */
public class PositionRequestListener extends BaseRequestListener{

    private RequestListener mListener;

    public PositionRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        if (mListener != null) {
            mListener.onRequestFailure(e);
        }
    }

    @Override
    public void onRequestSuccess(Object o) {
        super.onRequestSuccess(o);
        if (mListener != null) {
            if(o != null){
                mListener.onRequestSuccess(((PositionInfo.Model) o).getData());
            }else{
                mListener.onRequestSuccess(null);
            }
        }
    }

    @Override
    public BaseRequestListener start() {
        return this;
    }

    public void setListener(RequestListener listener){
        this.mListener = listener;
    }

    public static interface RequestListener{
        public void onRequestSuccess(PositionInfo data);
        public void onRequestFailure(SpiceException e);

    }


}
