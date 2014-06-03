package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.dynamic.ui.CommentListActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-2-24.
 */
public class CommentListRequestListener extends BaseRequestListener {

    private RequestListener mListener;

    /**
     * @param context
     */
    public CommentListRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
//        showMessage(R.string.nodata_title,R.string.nodata_content);
        super.onRequestFailure(e);

        if(mListener == null){
//            CommentListActivity activity = (CommentListActivity)context.get();
//            activity.UpDataUI(null);
        }else{
            mListener.onRequestFailure(e);
        }
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);

        if(data!=null){
            Comment.Model result= (Comment.Model)data;
            if(mListener == null){
//                CommentListActivity activity = (CommentListActivity)context.get();
//                activity.UpDataUI(result);
            }else{
                mListener.onRequestSuccess(result);
            }

        }else{
            if(mListener == null){
//                CommentListActivity activity = (CommentListActivity)context.get();
//                activity.UpDataUI(null);
            }else{
                mListener.onRequestSuccess(null);
            }
        }

    }

    @Override
    public CommentListRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }

    public void setListener(RequestListener listener){
        this.mListener = listener;
    }

    public static  interface RequestListener{
        public void onRequestSuccess(Comment.Model data);
        public void onRequestFailure(SpiceException e);
    }
}
