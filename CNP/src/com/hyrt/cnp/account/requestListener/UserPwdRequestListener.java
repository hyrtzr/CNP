package com.hyrt.cnp.account.requestListener;

import net.oschina.app.AppContext;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.hyrt.cnp.R;
import com.hyrt.cnp.account.LoginActivity;
import com.hyrt.cnp.base.account.AccountUtils;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by yepeng on 14-1-9.
 */
public class UserPwdRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public UserPwdRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        showMessage(R.string.pwd_msg_title,R.string.pwd_msgerror_content);
    }


    @Override
    public void onRequestSuccess(Object baseTest) {
        super.onRequestSuccess(baseTest);
        if(context != null && context.get()!=null){
            if(((BaseTest)baseTest).getCode().equals("200")){
                AccountManager.get(context.get()).removeAccount(AccountUtils.getAccount(context.get()),
                        new AccountManagerCallback<Boolean>() {
                            @Override
                            public void run(AccountManagerFuture<Boolean> booleanAccountManagerFuture) {
                                AppContext.getInstance().mUserDetail = null;
                                AppContext.getInstance().uuid = -1;
                                AppContext.getInstance().finishActivitys();

                                Intent intent = new Intent();
                                intent.setClass(context.get(), LoginActivity.class);
                                context.get().startActivity(intent);
                            }
                        }, new AsyncQueryHandler(new ContentResolver(context.get().getApplication()) {
                            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                            @Override
                            public String[] getStreamTypes(Uri url, String mimeTypeFilter) {
                                return super.getStreamTypes(url, mimeTypeFilter);
                            }
                        }) {
                            @Override
                            protected Handler createHandler(Looper looper) {
                                return super.createHandler(looper);
                            }
                        }
                );
                //showMessage(R.string.pwd_msg_title, R.string.pwd_msg_content);
            }else{
                showMessage(R.string.pwd_msg_title,R.string.pwd_msgerror_content);
            } 
        }
    }

    @Override
    public UserPwdRequestListener start() {
        showIndeterminate(R.string.user_pwd_pg);
        return this;
    }
}
