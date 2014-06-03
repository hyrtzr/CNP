package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.dynamic.ui.HomeInteractiveActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-16. change
 */
public class MyAlbumRequestListener extends BaseRequestListener{

    private RequestListener mListener;

    /**
     * @param context
     */
    public MyAlbumRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
//        showMessage(R.string.nodata_title,R.string.nodata_content);
        super.onRequestFailure(e);
        if(mListener == null){
            HomeInteractiveActivity activity = (HomeInteractiveActivity)context.get();
            activity.upDataAblumUI(null);
        }else{
            mListener.onRequestFailure(e);
        }

    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){
            if(mListener == null){
                HomeInteractiveActivity activity = (HomeInteractiveActivity)context.get();
                activity.upDataAblumUI((Album.Model) data);
            }else{
                mListener.onRequestSuccess(data);
            }

        }else{
            if(mListener == null){
                HomeInteractiveActivity activity = (HomeInteractiveActivity)context.get();
                activity.upDataAblumUI(null);
            }else{
                mListener.onRequestFailure(null);
            }

        }

    }

    @Override
    public MyAlbumRequestListener start() {
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
