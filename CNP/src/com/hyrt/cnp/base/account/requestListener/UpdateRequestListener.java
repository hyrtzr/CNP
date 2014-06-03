package com.hyrt.cnp.base.account.requestListener;

import android.app.Activity;
import android.content.Context;

import com.hyrt.cnp.base.account.model.Update;
import com.hyrt.cnp.base.account.utils.AlertUtils;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by Zoe on 2014-05-21.
 */
public class UpdateRequestListener extends BaseRequestListener{

    private RequestListener mListener;
    private Context context;

    public UpdateRequestListener(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onRequestSuccess(Object o) {
        super.onRequestSuccess(o);
        if(mListener != null){
            if(o != null){
                Update.Model data = (Update.Model) o;
                if("200".equals(data.getCode())){
                    mListener.onRequestSuccess(data.getData());
                }else{
                    mListener.onRequestFailure();
                }
            }else{
                mListener.onRequestFailure();
            }
        }
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
    }

    @Override
    public BaseRequestListener start() {
        return this;
    }

    public void setListener(RequestListener listener){
        this.mListener = listener;
    }

    public static interface RequestListener{
        public void onRequestSuccess(Update update);
        public void onRequestFailure();
    }
}
