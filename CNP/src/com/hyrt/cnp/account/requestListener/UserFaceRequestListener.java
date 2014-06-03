package com.hyrt.cnp.account.requestListener;

import android.app.Activity;
import android.content.Context;

import com.hyrt.cnp.R;
import com.hyrt.cnp.account.manager.UserFaceActivity;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.base.account.utils.AlertUtils;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by yepeng on 14-1-9.
 */
public class UserFaceRequestListener extends BaseRequestListener {

    private Context context;

    /**
     * @param context
     */
    public UserFaceRequestListener(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        AlertUtils.getInstance().showCenterToast(context, context.getString(R.string.face_msgerror_content));
//        showMessage(R.string.face_msg_title,R.string.face_msgerror_content);
    }


    @Override
    public void onRequestSuccess(Object baseTest) {
        super.onRequestSuccess(baseTest);
        if(context != null && context!=null){
            if(((BaseTest)baseTest).getCode().equals("200")){
                AlertUtils.getInstance().showCenterToast(context, context.getString(R.string.face_msg_content));
//                showMessage(R.string.face_msg_title, R.string.face_msg_content);
                ((UserFaceActivity)context).updateCacheAndUI();
            }else{
                AlertUtils.getInstance().showCenterToast(context, context.getString(R.string.face_msgerror_content));
//                showMessage(R.string.face_msg_title,R.string.face_msgerror_content);
            } 
        }
    }

    @Override
    public UserFaceRequestListener start() {
        showIndeterminate(R.string.user_face_pg);
        return this;
    }
}
