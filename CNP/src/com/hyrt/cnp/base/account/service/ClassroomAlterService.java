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
import java.util.HashMap;

/**
 * Created by Zoe on 2014-05-19.
 */
public class ClassroomAlterService{

    private CNPClient cnpClient;

    public ClassroomAlterService(CNPClient cnpClient) {
        this.cnpClient = cnpClient;
    }

    public BaseTest alterClassrommBanner(File file){
        cnpClient.configureRequest();
        MultiValueMap<String, Object> params = cnpClient.getParams();
        Resource face = new FileSystemResource(file);
        params.set("file", face);
        BaseTest result =  getRestTemplate().postForObject(
                AppContext.getInstance().getString(R.string.path_api)+"classroom/classphoto_add/",
                cnpClient.getParams(), BaseTest.class);
        return result;
    }

    public BaseTest alertClassroomSignature(String content){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("content", content);
        BaseTest result = getRestTemplate().getForObject(
                AppContext.getInstance().getString(R.string.path_api)+"classroom/signature/?" +
                        "token={token}&uuid={uuid}&cid={cid}&content={content}",
                BaseTest.class, params);
        return result;
    }

    protected RestTemplate getRestTemplate() {
        return new RestTemplate(true, new HttpComponentsClientHttpRequestFactory());
    }
}
