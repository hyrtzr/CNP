package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.StarBabay;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.ui.StarBabayActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-14.
 */
public class StarBabayRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public StarBabayRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
//        showMessage(R.string.nodata_title,R.string.nodata_content);
        StarBabayActivity activity = (StarBabayActivity)context.get();
        activity.initData(null);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data != null){
            StarBabayActivity activity = (StarBabayActivity)context.get();
            StarBabay.Model result= (StarBabay.Model)data;
            activity.initData(result);
        }else{
            StarBabayActivity activity = (StarBabayActivity)context.get();
            activity.initData(null);
//            showMessage(R.string.nodata_title,R.string.nodata_content);
        }
    }

    @Override
    public StarBabayRequestListener start() {
        showIndeterminate(R.string.starbaby_pg);
        return this;
    }
}
