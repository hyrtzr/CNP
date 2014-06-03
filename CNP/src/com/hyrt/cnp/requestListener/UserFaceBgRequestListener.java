package com.hyrt.cnp.requestListener;

import android.app.Activity;

import com.hyrt.cnp.FullscreenActivity;
import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by yepeng on 14-1-9.
 */
public class UserFaceBgRequestListener extends BaseRequestListener{



    /**
     * @param context
     */
    public UserFaceBgRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        showMessage(R.string.facebg_msg_title,R.string.facebg_msgerror_content);
    }


    @Override
    public void onRequestSuccess(Object baseTest) {
        super.onRequestSuccess(baseTest);
        if(context != null && context.get()!=null && baseTest != null){
            if(((BaseTest)baseTest).getCode().equals("200")){
                showMessage(R.string.facebg_msg_title,R.string.facebg_msg_content);
                ((FullscreenActivity)context.get()).updateCacheAndUI();
            }
        }
    }

    @Override
    public UserFaceBgRequestListener start() {
        showIndeterminate(R.string.user_facebg_pg);
        return this;
    }
}
