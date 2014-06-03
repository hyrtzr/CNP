package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.Teacher;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.ui.StarTeacherInfoActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-14.
 */
public class StarTeacherInfoRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public StarTeacherInfoRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        showMessage(R.string.nodata_title,R.string.nodata_content);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data != null){
            StarTeacherInfoActivity activity = (StarTeacherInfoActivity)context.get();
            Teacher.Model2 result= (Teacher.Model2)data;
            activity.updateUI(result);
        }else{
            showMessage(R.string.nodata_title,R.string.nodata_content);
        }
    }

    @Override
    public StarTeacherInfoRequestListener start() {
        showIndeterminate(R.string.starteacher_pg);
        return this;
    }
}
