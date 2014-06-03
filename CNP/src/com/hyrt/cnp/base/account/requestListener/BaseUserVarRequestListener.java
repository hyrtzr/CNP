package com.hyrt.cnp.base.account.requestListener;

import java.util.Map;

import roboguice.RoboGuice;
import android.app.Activity;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.model.UtilVar;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by yepeng on 14-1-9.
 */
public class BaseUserVarRequestListener extends BaseRequestListener {


    /**
     * @param context
     */

    private RequestListener mListener;
    private String var;
    public BaseUserVarRequestListener(Activity context, String var) {
        super(context);
        RoboGuice.getInjector(context).injectMembers(this);
        this.var=var;
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        if(mListener != null){
            mListener.onRequestFailure(e);
        }
    }

    @Override
    public void onRequestSuccess(Object userDetail) {
        super.onRequestSuccess(userDetail);

        if(mListener == null){

        }else{
            mListener.onRequestSuccess(((UtilVar)userDetail).getData());
        }

    }

    @Override
    public BaseUserVarRequestListener start() {
        showIndeterminate(R.string.user_info_pg);
        return this;
    }

    public void setListener(RequestListener listener){
        this.mListener = listener;
    }

    public static interface RequestListener{
        public void onRequestSuccess(Map<String, String> data);
        public void onRequestFailure(SpiceException e);
    }
}
