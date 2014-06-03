package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.School;

import net.oschina.app.AppContext;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by GYH on 14-1-3.
 */
public class SchoolService {
    private CNPClient cnpClient;

    public SchoolService(CNPClient cnpClient){
        this.cnpClient = cnpClient;
    }

    //TODO after modify
    public School.Model getSchoolList(RestTemplate restTemplate){
        cnpClient.configureRequest();
//        HashMap<String, String> params = cnpClient.getParamsforGet();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name","");
        params.put("property","");
        params.put("scale","");
        params.put("city","");
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"school/search/?" +
                "token={token}&uuid={uuid}&",
                School.Model.class,params);
    }


    public School.Model2 getSchoolinfo(RestTemplate restTemplate){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"school/nursery_info/?" +
                        "sid={sid}",
                School.Model2.class, params);
    }

    public School.Model2 getSchoolinfo(RestTemplate restTemplate, int sid){
//        cnpClient.configureRequest();
//        HashMap<String, String> params = cnpClient.getParamsforGet();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sid", sid+"");
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"school/nursery_info/?sid={sid}",
                School.Model2.class, params);
    }

}
