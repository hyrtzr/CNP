package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;
import android.widget.ImageView;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.ui.MyAddAblumActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 2014/4/6.
 */
public class MyAddAblumRequestListener extends BaseRequestListener {

    private RequestListener mListener;

    /**
     * @param context
     */
    public MyAddAblumRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);

    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){
            Comment.Model3 model3 = (Comment.Model3)data;
            if(model3.getCode().equals("200")){
                if(mListener == null){
                    MyAddAblumActivity activity = (MyAddAblumActivity)context.get();
                    activity.BackAfterSucc();
                }else{
                    mListener.onRequestSuccess(data);
                }

            }else {
                showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);
            }
        }else{
            showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);
        }

    }

    @Override
    public MyAddAblumRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }

    public void setListener(RequestListener listener){
        this.mListener = listener;
    }

    public static interface RequestListener{
        public void onRequestSuccess(Object data);
        public void onRequestFailure(SpiceException e);
    }
}
