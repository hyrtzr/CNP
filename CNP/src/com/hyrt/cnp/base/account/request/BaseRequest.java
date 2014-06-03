package com.hyrt.cnp.base.account.request;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountsException;
import android.app.Activity;
import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.AccountScope;
import com.hyrt.cnp.base.account.AccountUtils;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.utils.AlertUtils;
import com.hyrt.cnp.base.account.utils.LogHelper;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import net.oschina.app.AppContext;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.SocketTimeoutException;

import roboguice.RoboGuice;
import roboguice.inject.ContextScope;

/**
 * Created by yepeng on 14-1-3.
 */
public abstract class BaseRequest extends SpringAndroidSpiceRequest{

    private Context context;

    @Inject
    private AccountScope accountScope;
    @Inject
    private Activity activity;
    @Inject
    private ContextScope contextScope;

    private static final int WEBSERVICES_TIMEOUT = 1000*20;//请求超时时间

    public Context getContext() {
        return context;
    }

    public BaseRequest(Class clazz,Context context) {
        super(clazz);
        this.context = context;
        RoboGuice.getInjector(context).injectMembers(this);
    }

    @Override
    public Base loadDataFromNetwork() throws Exception {
        manageTimeOuts(getRestTemplate());
        Base base = null;
        try{
            final AccountManager manager = AccountManager.get(activity);
            final Account account;
            try {
                account = AccountUtils.getAccount(manager, activity);
            } catch (IOException e) {
                return null;
            } catch (AccountsException e) {
                return null;
            }
            accountScope.enterWith(account, manager);
            try {
                contextScope.enter(activity);
                try {
                    base = run();
                }catch (Exception e) {
                    String msg = e.getMessage();
                    if(msg.contains("java.net.SocketTimeoutException")){
                        ((BaseActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertUtils.getInstance().showCenterToast(AppContext.getInstance(), "请求超时！");
                            }
                        });

                    }
                    LogHelper.i("tag", "msg:"+msg.contains("java.net.SocketTimeoutException"));
                    // Retry task if authentication failure occurs and account is
                    // successfully updated
                    if (AccountUtils.isUnauthorized(e)
                            && AccountUtils.updateAccount(account, activity))
                        return run();
                    else
                        throw e;
                } finally {
                    contextScope.exit(activity);
                }
            } finally {
                accountScope.exit();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return base;
    }

    /**
     * 管理请求超时
     * @param restTemplate
     */
    private void manageTimeOuts(RestTemplate restTemplate) {
        // set timeout for requests
        if(restTemplate == null){
            return;
        }
        ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
        if (factory instanceof HttpComponentsClientHttpRequestFactory) {
            HttpComponentsClientHttpRequestFactory advancedFactory = (HttpComponentsClientHttpRequestFactory) factory;
            advancedFactory.setConnectTimeout(WEBSERVICES_TIMEOUT);
            advancedFactory.setReadTimeout(WEBSERVICES_TIMEOUT);
        } else if (factory instanceof SimpleClientHttpRequestFactory) {
            SimpleClientHttpRequestFactory advancedFactory = (SimpleClientHttpRequestFactory) factory;
            advancedFactory.setConnectTimeout(WEBSERVICES_TIMEOUT);
            advancedFactory.setReadTimeout(WEBSERVICES_TIMEOUT);
        }
    }

    public abstract Base run();

    
    @Override
    public int compareTo(SpiceRequest other) {
    	// TODO Auto-generated method stub
    	return super.compareTo(other);
    }
}
