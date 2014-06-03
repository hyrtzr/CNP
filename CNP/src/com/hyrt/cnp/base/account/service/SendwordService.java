package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.SendWord;

import net.oschina.app.AppContext;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by GYH on 14-1-8.
 */
public class SendwordService {
    private CNPClient cnpClient;

    public SendwordService(CNPClient cnpClient){
        this.cnpClient = cnpClient;
    }

    public SendWord.Model getSendwordData(RestTemplate restTemplate, int sid){
        HashMap<String, String> params = null;
        if(sid == -1){
            cnpClient.configureRequest();
            params = cnpClient.getParamsforGet();
        }else{
            params = new HashMap<String, String>();
            params.put("sid", sid+"");
        }
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"school/sendword/?sid={sid}",
                SendWord.Model.class,params);
    }
}
