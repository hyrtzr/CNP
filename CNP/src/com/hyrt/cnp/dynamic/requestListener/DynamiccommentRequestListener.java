package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.ui.DynamicCommentActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-23.
 */
public class DynamiccommentRequestListener extends BaseRequestListener {

    private RequestListener mListener;

    /**
     * @param context
     */
    public DynamiccommentRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        if(mListener == null){
            showMessage(R.string.nodata_title,R.string.nodata_content);
        }else{
            mListener.onRequestFailure(e);
        }

        super.onRequestFailure(e);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){
            Dynamic.Model3 result= (Dynamic.Model3)data;



            if(result.getCode().equals("200")){
                if(mListener == null){
                    DynamicCommentActivity activity = (DynamicCommentActivity)context.get();
                    activity.showSuccess();
                }else{
                    mListener.onRequestSuccess(result);
                }

            }else{
                if(mListener == null){
                    showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);
                }else{
                    mListener.onRequestFailure(null);
                }

            }

        }else{
            if(mListener == null){
                showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);
            }else{
                mListener.onRequestFailure(null);
            }
        }

    }

    @Override
    public DynamiccommentRequestListener start() {
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
