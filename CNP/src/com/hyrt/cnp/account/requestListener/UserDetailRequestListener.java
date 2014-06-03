package com.hyrt.cnp.account.requestListener;

import roboguice.RoboGuice;
import android.app.Activity;

import com.hyrt.cnp.R;
import com.hyrt.cnp.account.manager.UserMainActivity;
import com.hyrt.cnp.base.account.model.UserDetail;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by yepeng on 14-1-9.
 */
public class UserDetailRequestListener extends BaseRequestListener {


    /**
     * @param context
     */
    public UserDetailRequestListener(Activity context) {
        super(context);
        RoboGuice.getInjector(context).injectMembers(this);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
    }

    @Override
    public void onRequestSuccess(Object userDetail) {
        super.onRequestSuccess(userDetail);
        if(context != null && context.get()!=null){
            if(context.get() instanceof  UserMainActivity){
                UserMainActivity userMainActivity = (UserMainActivity) context.get();
                if(userDetail!=null){
                    userMainActivity.updateUI((UserDetail.UserDetailModel)userDetail);
                }
            }
        }
    }

    @Override
    public UserDetailRequestListener start() {
        showIndeterminate(R.string.user_info_pg);
        return this;
    }
}
