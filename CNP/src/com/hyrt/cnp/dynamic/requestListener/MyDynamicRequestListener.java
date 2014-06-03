package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.base.account.utils.LogHelper;
import com.hyrt.cnp.dynamic.ui.HomeInteractiveActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-23.
 */
public class MyDynamicRequestListener extends BaseRequestListener {

    private RequestListener mListener;

    private static final String TAG = "MyDynamicRequestListener";

    /**
     * @param context
     */
    public MyDynamicRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
//        showMessage(R.string.nodata_title,R.string.nodata_content);
        super.onRequestFailure(e);
        if(mListener == null){
            HomeInteractiveActivity activity = (HomeInteractiveActivity)context.get();
            activity.upDataUI(null,0);
        }else{
            mListener.onRequestFailure(e);
        }

    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){
            Dynamic.Model result= (Dynamic.Model)data;
            if(mListener == null){
                HomeInteractiveActivity activity = (HomeInteractiveActivity)context.get();
                activity.upDataUI(result,0);
            }else{
                mListener.onRequestSuccess(result);
            }

        }else{
            if(mListener == null){
                HomeInteractiveActivity activity = (HomeInteractiveActivity)context.get();
                activity.upDataUI(null,0);
            }else{
                mListener.onRequestSuccess(null);
            }

//            showMessage(R.string.nodata_title,R.string.nodata_content);
        }

    }

    @Override
    public MyDynamicRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }

    public void setListener(RequestListener listener){
        this.mListener = listener;
    }

    public static interface RequestListener{
        public void onRequestSuccess(Dynamic.Model data);
        public void onRequestFailure(SpiceException e);
    }
}
