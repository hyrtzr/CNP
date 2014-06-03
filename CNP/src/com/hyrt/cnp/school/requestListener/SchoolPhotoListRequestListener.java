package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.Photo;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.ui.SchoolPhotoActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-14.
 */
public class SchoolPhotoListRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    private int id;
    public SchoolPhotoListRequestListener(Activity context,int id) {
        super(context);
        this.id=id;
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
//        showMessage(R.string.nodata_title,R.string.nodata_content);
        SchoolPhotoActivity activity = (SchoolPhotoActivity) context.get();
        switch (id) {
            case 0:
                activity.pages.get(0).initData(null);
                break;
            case 1:
                activity.pages.get(1).initData(null);
                break;
            case 2:
                activity.pages.get(2).initData(null);
                break;
        }
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        SchoolPhotoActivity activity = (SchoolPhotoActivity)context.get();
        if(data != null){

            Photo.Model result= (Photo.Model)data;
            switch (id){
                case 0:
                    activity.pages.get(0).initData(result);
                    break;
                case 1:
                    activity.pages.get(1).initData(result);
                    break;
                case 2:
                    activity.pages.get(2).initData(result);
                    break;
            }
        }else{
            switch (id){
                case 0:
                    activity.pages.get(0).initData(null);
                    break;
                case 1:
                    activity.pages.get(1).initData(null);
                    break;
                case 2:
                    activity.pages.get(2).initData(null);
                    break;
            }
//            showMessage(R.string.nodata_title,R.string.nodata_content);
        }
    }

    @Override
    public SchoolPhotoListRequestListener start() {
        showIndeterminate(R.string.photo_pg);
        return this;
    }
}
