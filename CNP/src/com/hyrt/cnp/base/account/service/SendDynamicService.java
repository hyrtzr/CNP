package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.BaseTest;

import net.oschina.app.AppContext;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by HY on 2014-04-10.
 */
public class SendDynamicService{

    private CNPClient cnpClient;

    public SendDynamicService(CNPClient cnpClient) {
        this.cnpClient = cnpClient;
    }

    public BaseTest addDynamic(String content, File picUrl, String toUid, String toName){
        cnpClient.configureRequest();
        MultiValueMap<String, Object> params = cnpClient.getParams();
        try{
            StringBuilder sbContent = new StringBuilder(URLEncoder.encode(content, "UTF-8"));
            params.set("content", sbContent.toString());
            if(toName != null){
                StringBuilder sbToName = new StringBuilder(URLEncoder.encode(toName, "UTF-8"));
                params.set("toName", sbToName.toString());
            }
        }catch (UnsupportedEncodingException e){
            params.set("content", content);
            if(toName != null){
                params.set("toName", toName);
            }
        }
        if(picUrl != null){
            Resource face = new FileSystemResource(picUrl);
            params.set("picUrl", face);
        }
        if(toUid != null){
            params.set("toUid", toUid);
        }

        android.util.Log.i("tag",  "content:"+params.get("content"));

        BaseTest result =  getRestTemplate().postForObject(
                AppContext.getInstance().getString(R.string.path_api)+"home/dynamic_add/",
                cnpClient.getParams(),BaseTest.class);
        return result;
    }

    protected RestTemplate getRestTemplate() {
        return new RestTemplate(true, new HttpComponentsClientHttpRequestFactory());
    }
}
