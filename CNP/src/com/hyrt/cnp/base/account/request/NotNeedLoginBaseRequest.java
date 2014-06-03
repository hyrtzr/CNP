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
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import roboguice.RoboGuice;
import roboguice.inject.ContextScope;

/**
 * Created by Zoe on 2014-05-05.
 */
public abstract class NotNeedLoginBaseRequest extends SpringAndroidSpiceRequest {
    private Context context;

    @Inject
    private AccountScope accountScope;
    @Inject
    private Activity activity;
    @Inject
    private ContextScope contextScope;

    private static final int WEBSERVICES_TIMEOUT = 1000*20;

    public Context getContext() {
        return context;
    }

    public NotNeedLoginBaseRequest(Class clazz,Context context) {
        super(clazz);
        this.context = context;
        RoboGuice.getInjector(context).injectMembers(this);
    }

    @Override
    public Base loadDataFromNetwork() throws Exception {
        manageTimeOuts(getRestTemplate());
        Base base = run();
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
