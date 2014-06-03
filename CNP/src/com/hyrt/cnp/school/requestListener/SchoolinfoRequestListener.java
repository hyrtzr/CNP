package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.School;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.ui.SchoolInfoActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-14.
 */
public class SchoolinfoRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public SchoolinfoRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        showMessage(R.string.nodata_title, R.string.nodata_content);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data != null){
            SchoolInfoActivity activity = (SchoolInfoActivity)context.get();
            School.Model2 result= (School.Model2)data;
            activity.initData(result);
        }else{
            showMessage(R.string.nodata_title, R.string.nodata_content);
        }
    }

    @Override
    public SchoolinfoRequestListener start() {
        showIndeterminate(R.string.schoolinfo_pg);
        return this;
    }
}
