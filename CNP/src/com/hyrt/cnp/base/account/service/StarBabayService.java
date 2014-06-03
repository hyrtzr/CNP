package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.StarBabay;

import net.oschina.app.AppContext;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by GYH on 14-1-14.
 * 明星宝宝
 */
public class StarBabayService {

    private CNPClient cnpClient;

    public StarBabayService(CNPClient cnpClient){
        this.cnpClient = cnpClient;
    }

    public StarBabay.Model getStarbabayData(RestTemplate restTemplate, int sid){
        HashMap<String, String> params = null;
        if(sid == -1){
            cnpClient.configureRequest();
            params = cnpClient.getParamsforGet();
        }else{
            params = new HashMap<String, String>();
            params.put("sid", sid+"");
        }
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"school/babay/?sid={sid}",
                StarBabay.Model.class, params);
    }
}
