package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.Notice;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.ui.SchoolNoticeInfoActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-2-26.
 */
public class NoticeInfoRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public NoticeInfoRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
//        SchoolNoticeActivity activity = (SchoolNoticeActivity)context.get();
//        activity.initData(null);
        showMessage(R.string.nodata_title,R.string.nodata_content);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data != null){
            SchoolNoticeInfoActivity activity = (SchoolNoticeInfoActivity)context.get();
            Notice.Model2 result= (Notice.Model2)data;
            activity.UpDataUI(result);
        }else{
//            SchoolNoticeInfoActivity activity = (SchoolNoticeInfoActivity)context.get();
//            activity.UpDataUI(null);
            showMessage(R.string.nodata_title,R.string.nodata_content);
        }
    }

    @Override
    public NoticeInfoRequestListener start() {
        showIndeterminate(R.string.notice_pg);
        return this;
    }
}
