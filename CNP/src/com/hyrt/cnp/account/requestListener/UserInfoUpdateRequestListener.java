package com.hyrt.cnp.account.requestListener;

import net.oschina.app.AppContext;
import android.app.Activity;

import com.hyrt.cnp.R;
import com.hyrt.cnp.account.manager.UserInfoActivity;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.base.account.utils.LogHelper;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by yepeng on 14-1-9.
 */
public class UserInfoUpdateRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public UserInfoUpdateRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        showMessage(R.string.info_msg_title,R.string.pwd_msgerror_content);
    }


    @Override
    public void onRequestSuccess(Object baseTest) {
        super.onRequestSuccess(baseTest);
        if(context != null && context.get()!=null){
            LogHelper.i("tag", "((BaseTest)baseTest):"+((BaseTest)baseTest).getMsg());
            if(((BaseTest)baseTest).getCode().equals("200")){
                showMessage(R.string.info_msg_title,R.string.info_msg_content);
                ((UserInfoActivity)context.get()).updateUi((BaseTest)baseTest);
                AppContext.getInstance().mUserDetail = null;
            }else{
                showMessage(R.string.info_msg_title,R.string.info_msgerror_content);
            } 
        }
    }

    @Override
    public UserInfoUpdateRequestListener start() {
        showIndeterminate(R.string.user_updateinfo_pg);
        return this;
    }
}
