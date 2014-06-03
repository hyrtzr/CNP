package com.hyrt.cnp.classroom.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.Photo;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.classroom.ui.ClassroomphotolistActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-17.
 */
public class ClassroomPhotoListRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public ClassroomPhotoListRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
//        showMessage(R.string.nodata_title,R.string.nodata_content);
        ClassroomphotolistActivity activity = (ClassroomphotolistActivity)context.get();
        activity.updateUI(null);
        super.onRequestFailure(e);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){
            ClassroomphotolistActivity activity = (ClassroomphotolistActivity)context.get();
            Photo.Model result= (Photo.Model)data;
            activity.updateUI(result);
        }else{
            ClassroomphotolistActivity activity = (ClassroomphotolistActivity)context.get();
            activity.updateUI(null);
//            showMessage(R.string.nodata_title,R.string.nodata_content);
        }

    }

    @Override
    public ClassroomPhotoListRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
