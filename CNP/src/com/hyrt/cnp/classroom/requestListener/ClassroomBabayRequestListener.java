package com.hyrt.cnp.classroom.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.ClassRoomBabay;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.classroom.ui.ClassroomBabayActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-16.
 */
public class ClassroomBabayRequestListener extends BaseRequestListener{

    private requestListener mListener;

    /**
     * @param context
     */
    public ClassroomBabayRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
//        showMessage(R.string.nodata_title,R.string.nodata_content);
        if(mListener == null){
            ClassroomBabayActivity activity = (ClassroomBabayActivity)context.get();
            activity.updateUI(null);
        }else{
            mListener.onRequestFailure(e);
        }

        super.onRequestFailure(e);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){
            ClassRoomBabay.Model result= (ClassRoomBabay.Model)data;
            if(mListener == null){
                ClassroomBabayActivity activity = (ClassroomBabayActivity)context.get();

                activity.updateUI(result);
            }else{
                mListener.onRequestSuccess(result);
            }
        }else{
            if(mListener == null){
                ClassroomBabayActivity activity = (ClassroomBabayActivity)context.get();
                activity.updateUI(null);
            }else{
                mListener.onRequestSuccess(null);
            }

//            showMessage(R.string.nodata_title,R.string.nodata_content);
        }

    }

    public void setListener(requestListener listener){
        this.mListener = listener;
    }

    public static interface requestListener{
        public void onRequestSuccess(ClassRoomBabay.Model data);
        public void onRequestFailure(SpiceException e);
    }

    @Override
    public ClassroomBabayRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
