package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.Notice;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.ui.SchoolNoticeActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-14.
 */
public class SchoolNoticeRequestListener extends BaseRequestListener{
    /**
     * @param context
     */
    public SchoolNoticeRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        SchoolNoticeActivity activity = (SchoolNoticeActivity)context.get();
        activity.initData(null);
//        showMessage(R.string.nodata_title,R.string.nodata_content);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data != null){

            SchoolNoticeActivity activity = (SchoolNoticeActivity)context.get();
            Notice.Model result= (Notice.Model)data;
            activity.initData(result);
        }else{
            SchoolNoticeActivity activity = (SchoolNoticeActivity)context.get();
            activity.initData(null);
//            showMessage(R.string.nodata_title,R.string.nodata_content);
        }
    }

    @Override
    public SchoolNoticeRequestListener start() {
        showIndeterminate(R.string.notice_pg);
        return this;
    }
}
