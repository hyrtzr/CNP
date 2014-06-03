package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.Teacher;

import net.oschina.app.AppContext;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by GYH on 14-1-14.
 */
public class TeacherService {

    private CNPClient cnpClient;

    public TeacherService(CNPClient cnpClient){
        this.cnpClient = cnpClient;
    }


    public Teacher.Model getStarteacherData(RestTemplate restTemplate, int sid){
        HashMap<String, String> params = null;
        if(sid == -1){
            cnpClient.configureRequest();
            params = cnpClient.getParamsforGet();
        }else{
            params = new HashMap<String, String>();
            params.put("sid", sid+"");
        }
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"school/teacher/?sid={sid}",
                Teacher.Model.class, params);
    }


    public Teacher.Model2 getStarteacherinfoData(RestTemplate restTemplate,int userid){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid",userid+"");
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"school/teacher_info/?" +
                "userid={userid}",
                Teacher.Model2.class, params);
    }

}
