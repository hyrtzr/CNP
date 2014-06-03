package com.hyrt.cnp.classroom.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.ClassRoom;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.R;
import com.hyrt.cnp.classroom.ui.ClassroomIndexActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-16.
 */
public class ClassroomInfoRequestListener extends BaseRequestListener{
    /**
     * @param context
     */
    public ClassroomInfoRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        showMessage(R.string.nodata_title,R.string.nodata_content);
        super.onRequestFailure(e);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){
            ClassroomIndexActivity activity = (ClassroomIndexActivity)context.get();
            ClassRoom.Model2 result= (ClassRoom.Model2)data;
            activity.updateUI(result);
        }else{
            showMessage(R.string.nodata_title,R.string.nodata_content);
        }

    }

    @Override
    public ClassroomInfoRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
