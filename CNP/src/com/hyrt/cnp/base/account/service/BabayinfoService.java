package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.BabyInfo;

import net.oschina.app.AppContext;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by GYH on 14-1-16.
 */
public class BabayinfoService {

    private CNPClient cnpClient;

    public BabayinfoService(CNPClient cnpClient){
        this.cnpClient = cnpClient;
    }

    public BabyInfo.Model getBabayinfoData(RestTemplate restTemplate,String userid){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("userid",userid);
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"classroom/baby_info/?" +
                "token={token}&uuid={uuid}&userid={userid}",
                BabyInfo.Model.class, params);
    }
}
