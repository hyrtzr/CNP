package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.utils.LogHelper;

import net.oschina.app.AppContext;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by HY on 2014-05-20.
 */
public class NoticeAlterService {

    private CNPClient cnpClient;

    public NoticeAlterService(CNPClient cnpClient) {
        this.cnpClient = cnpClient;
    }

    public BaseTest addNotice(String title, String content){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("title", title);
        params.put("content", content);
        BaseTest result = getRestTemplate().getForObject(
                AppContext.getInstance().getString(R.string.path_api)+"classroom/notice_add/?" +
                        "token={token}&uuid={uuid}&cid={cid}&title={title}&content={content}",
                BaseTest.class, params
        );
        return result;
    }

    public BaseTest changeNotice(int id, String title, String content){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("id", id+"");
        params.put("title", title);
        params.put("content", content);
        BaseTest result = getRestTemplate().getForObject(
                AppContext.getInstance().getString(R.string.path_api)+"classroom/notice_edit/?" +
                        "token={token}&uuid={uuid}&id={id}&title={title}&content={content}",
                BaseTest.class, params
        );
        return result;
    }

    public BaseTest delNotice(int id){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("id", id+"");
        BaseTest result = getRestTemplate().getForObject(
                AppContext.getInstance().getString(R.string.path_api)+"classroom/notice_del/?" +
                        "token={token}&uuid={uuid}&id={id}&cid={cid}",
                BaseTest.class, params
        );
        return result;
    }

    protected RestTemplate getRestTemplate() {
        return new RestTemplate(true, new HttpComponentsClientHttpRequestFactory());
    }
}
