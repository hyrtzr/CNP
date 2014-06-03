package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.dynamic.ui.BabayIndexActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-23.
 */
public class BabayDynamicRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public BabayDynamicRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
//        showMessage(R.string.nodata_title,R.string.nodata_content);
        super.onRequestFailure(e);
        BabayIndexActivity activity = (BabayIndexActivity)context.get();
        activity.updateUI(null);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){
            BabayIndexActivity activity = (BabayIndexActivity)context.get();
            Dynamic.Model result= (Dynamic.Model)data;
            activity.updateUI(result);
        }else{
            BabayIndexActivity activity = (BabayIndexActivity)context.get();
            activity.updateUI(null);
//            showMessage(R.string.nodata_title,R.string.nodata_content);
        }

    }

    @Override
    public BabayDynamicRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
