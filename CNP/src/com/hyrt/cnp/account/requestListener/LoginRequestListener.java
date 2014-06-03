package com.hyrt.cnp.account.requestListener;

import static com.hyrt.cnp.base.account.AccountConstants.ACCOUNT_TYPE;
import android.accounts.Account;
import android.app.Activity;
import android.content.Context;

import com.hyrt.cnp.R;
import com.hyrt.cnp.account.LoginActivity;
import com.hyrt.cnp.base.account.model.User;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.base.account.utils.AlertUtils;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by yepeng on 14-1-9.
 */
public class LoginRequestListener extends BaseRequestListener {

    private Context mContext;

    /**
     * @param context
     */
    public LoginRequestListener(Activity context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        showMessage(R.string.login_notice, R.string.error_username);
        super.onRequestFailure(e);
    }

    @Override
    public void onRequestSuccess(Object user) {
        super.onRequestSuccess(user);
            if(user != null){
                try{
                    String strGroupId =  ((User.UserModel) user).getData().getGroupID();
                    int groupId = Integer.parseInt(strGroupId);
                    if(groupId == 2){
                        AlertUtils.getInstance().showCenterToast(mContext, "该用户无法登录");
                    }
                }catch (NumberFormatException e){

                }
            }
            Account account = new Account(((User.UserModel)user).getData().getUsername(), ACCOUNT_TYPE);
            if(context != null && context.get()!=null){
                LoginActivity loginActivity = (LoginActivity) context.get();
                if (loginActivity.isRequestNewAccount()) {
                    loginActivity.getAccountManager()
                            .addAccountExplicitly(account, loginActivity.getPassword(), null);
                } else {
                    loginActivity.getAccountManager().setPassword(account, loginActivity.getPassword());
                }
                loginActivity.finishLogin(account.name,loginActivity.getPassword());
            }
    }

    @Override
    public LoginRequestListener start() {
        showIndeterminate(R.string.user_login_pg);
        return this;
    }

}
