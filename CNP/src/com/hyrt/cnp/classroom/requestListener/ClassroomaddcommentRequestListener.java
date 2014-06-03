package com.hyrt.cnp.classroom.requestListener;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.R;
import com.hyrt.cnp.classroom.ui.ClassroomphotoinfoActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-16.
 */
public class ClassroomaddcommentRequestListener extends BaseRequestListener{
    private Context context;
    /**
     * @param context
     */
    public ClassroomaddcommentRequestListener(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        Toast toast = Toast.makeText(context, getString(R.string.nodata_addcommentfial), 0);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        super.onRequestFailure(e);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        Log.i("tag", "data:"+data);
        if(data!=null){
            ClassroomphotoinfoActivity activity = (ClassroomphotoinfoActivity)context;
            Comment.Model3 result= (Comment.Model3)data;
            Log.i("tag", "msg:"+result.getMsg()+" code:"+result.getCode());
            if(result.getCode().equals("200")){
                activity.ShowSuccess();
            }else{
                Toast toast = Toast.makeText(context, getString(R.string.nodata_addcommentfial), 0);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }else{
            Toast toast = Toast.makeText(context, getString(R.string.nodata_addcommentfial), 0);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }

    @Override
    public ClassroomaddcommentRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
