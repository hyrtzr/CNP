package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.model.Update;

import net.oschina.app.AppContext;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Zoe on 2014-05-21.
 */
public class UpdateService{

    public Update.Model getNewUpdate(){
        Update.Model result = getRestTemplate().getForObject(
                AppContext.getInstance().getString(R.string.path_api)+"syn/update/", Update.Model.class);
        return result;
    }

    protected RestTemplate getRestTemplate() {
        return new RestTemplate(true, new HttpComponentsClientHttpRequestFactory());
    }
}
