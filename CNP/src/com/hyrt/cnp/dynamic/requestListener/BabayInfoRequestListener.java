package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.BabyInfo;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.base.account.utils.LogHelper;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.ui.BabayIndexActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-23. / Zoe change on 14-4-11
 */
public class BabayInfoRequestListener extends BaseRequestListener {

    private requestListener mListener;

    /**
     * @param context
     */
    public BabayInfoRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        showMessage(R.string.nodata_title,R.string.nodata_content);
        if(mListener != null){
            mListener.onRequestFailure(e);
        }
        super.onRequestFailure(e);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);

        if(data!=null){
            BabyInfo.Model result= (BabyInfo.Model)data;
            if(mListener == null){
                BabayIndexActivity activity = (BabayIndexActivity)context.get();
                if(result != null){
                    activity.UpdataBabayinfo(result.getData());
                }else{
                    showMessage(R.string.nodata_title,R.string.nodata_content);
                }

            }else{
                mListener.onRequestSuccess(result);
            }

        }else{
            showMessage(R.string.nodata_title,R.string.nodata_content);
            if(mListener != null){
                mListener.onRequestFailure(null);
            }
        }

    }

    @Override
    public BabayInfoRequestListener start() {
        showIndeterminate("加载宝宝资料中...");
        return this;
    }

    public void setListener(requestListener listener){
        this.mListener = listener;
    }

    public static interface requestListener{
        public void onRequestSuccess(Object data);
        public void onRequestFailure(SpiceException e);
    }
}
