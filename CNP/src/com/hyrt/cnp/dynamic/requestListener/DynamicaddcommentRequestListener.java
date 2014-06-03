package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.ui.DynamicCommentActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-16.
 */
public class DynamicaddcommentRequestListener extends BaseRequestListener{

    private requestListener mListener;
    private Context context;

    private static final String TAG = "DynamicaddcommentRequestListener";

    /**
     * @param context
     */
    public DynamicaddcommentRequestListener(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        android.util.Log.i("tag", "msg:请求失败");
//        showMessage(R.string.nodata_title, R.string.nodata_content);
        super.onRequestFailure(e);
        if(mListener != null) {
            mListener.onRequestFailure(e);
        }else{

        }
        Toast toast = Toast.makeText(context, getString(R.string.nodata_addcommentfial), 0);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        android.util.Log.i("tag", "msg:-data:"+data);
        if(data!=null){
            Comment.Model3 result= (Comment.Model3)data;
            android.util.Log.i("tag", "code:"+result.getCode()+" msg:"+result.getMsg());
            if(result.getCode().equals("200")){
                if(mListener != null){
                    mListener.onRequestSuccess(data);
                }else{
                    DynamicCommentActivity activity = (DynamicCommentActivity)context;
                    activity.showSuccess();
                }
            }else{
                if(mListener != null) {
                    mListener.onRequestFailure(null);
                }else{

                }
                Toast toast = Toast.makeText(context, getString(R.string.nodata_addcommentfial), 0);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

//                showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);

            }
        }else{
            if(mListener != null) {
                mListener.onRequestFailure(null);
            }else{

            }
            Toast toast = Toast.makeText(context, getString(R.string.nodata_addcommentfial), 0);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
//            showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);
        }

    }

    @Override
    public DynamicaddcommentRequestListener start() {
        showIndeterminate("发送中...");
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
