package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.ClassRoomBabay;

import net.oschina.app.AppContext;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by GYH on 14-1-17.
 */
public class ClassroomBabayService{

    private CNPClient cnpClient;

    public ClassroomBabayService(CNPClient cnpClient){
        this.cnpClient = cnpClient;
    }

    public ClassRoomBabay.Model getclassroombabayData(RestTemplate restTemplate){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        String path = AppContext.getInstance().getString(R.string.path_api)+"classroom/babylist/?" +
                "token={token}&uuid={uuid}&cid={cid}";
        return  restTemplate.getForObject(path,
                ClassRoomBabay.Model.class, params);
    }

    public ClassRoomBabay.Model getclassroombabayData(RestTemplate restTemplate, boolean isAt){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        String path = AppContext.getInstance().getString(R.string.path_api)+"classroom/babylist/?" +
                "token={token}&uuid={uuid}&cid={cid}";
        if(isAt){
            path+="&at=1";
        }
        return  restTemplate.getForObject(path,
                ClassRoomBabay.Model.class, params);
    }
}
