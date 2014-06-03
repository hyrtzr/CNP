package com.hyrt.cnp.base.account.requestListener;

import android.app.Activity;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.model.Comment;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-16.
 */
public class BaseClassroomaddcommentRequestListener extends BaseRequestListener{
    /**
     * @param context
     */
    public BaseClassroomaddcommentRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);
        super.onRequestFailure(e);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){
            Comment.Model3 result= (Comment.Model3)data;
            if(result.getCode().equals("200")){

            }else{
                showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);
            }
        }else{
            showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);
        }

    }

    @Override
    public BaseClassroomaddcommentRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
