package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.dynamic.ui.HomeInteractiveActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-23.
 */
public class MyIndexRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public MyIndexRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
//        showMessage(R.string.nodata_title,R.string.nodata_content);
        super.onRequestFailure(e);
        HomeInteractiveActivity activity = (HomeInteractiveActivity)context.get();
        activity.upDataUI(null, 2);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){
            HomeInteractiveActivity activity = (HomeInteractiveActivity)context.get();
            Dynamic.Model result= (Dynamic.Model)data;
            activity.upDataUI(result, 2);
        }else{
            HomeInteractiveActivity activity = (HomeInteractiveActivity)context.get();
            activity.upDataUI(null, 2);
//            showMessage(R.string.nodata_title,R.string.nodata_content);
        }

    }

    @Override
    public MyIndexRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
