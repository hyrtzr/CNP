package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.BabyInfo;
import com.hyrt.cnp.base.account.model.BaseStringArray;

import net.oschina.app.AppContext;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by HY on 2014-05-14.
 */
public class BaseStringArrayService {

    private CNPClient cnpClient;

    public BaseStringArrayService(CNPClient cnpClient){
        this.cnpClient = cnpClient;
    }

    public BaseStringArray getAboutmeNums(RestTemplate restTemplate){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"home/tips_num?" +
                        "token={token}&uuid={uuid}",
                BaseStringArray.class, params);
    }
}
