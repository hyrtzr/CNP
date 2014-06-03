package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.model.DynamicPhoto;
import com.hyrt.cnp.base.account.model.Photo;

import net.oschina.app.AppContext;

import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Zoe on 2014-04-11.
 */
public class AddPhotoService {

    private CNPClient cnpClient;

    public AddPhotoService(CNPClient cnpClient) {
        this.cnpClient = cnpClient;
    }

    public BaseTest addPhoto(
            String paid, String photoname,
            String introduce, File photo_add){
        cnpClient.configureRequest();
        MultiValueMap<String, Object> params = cnpClient.getParams();
        params.set("paid", paid);
        try {
            StringBuilder sbPhotoname = new StringBuilder(URLEncoder.encode(photoname, "UTF-8"));
            StringBuilder sbIntroduce = new StringBuilder(URLEncoder.encode(introduce, "UTF-8"));
            params.set("photoname", sbPhotoname.toString());
            params.set("introduce", sbIntroduce.toString());
        }catch (UnsupportedEncodingException e){
            params.set("photoname", photoname);
            params.set("introduce", introduce);
        }
        Resource face = new FileSystemResource(photo_add);
        params.set("file", face);
        BaseTest result =  getRestTemplate().postForObject(
                AppContext.getInstance().getString(R.string.path_api)+"home/photo_add/",
                cnpClient.getParams(), BaseTest.class);

        return result;
    }

    public BaseTest delDynamicPhoto(DynamicPhoto photo){
        cnpClient.configureRequest();
        Map<String, String> params = cnpClient.getParamsforGet();
        android.util.Log.i("tag", "pid:"+photo.getPhotoID());
        params.put("pid", photo.getPhotoID() + "");
        BaseTest result =  getRestTemplate().getForObject(
                AppContext.getInstance().getString(R.string.path_api)+"home/photo_del/?"
                +"token={token}&uuid={uuid}&pid={pid}",
                BaseTest.class, params);
        return result;
    }

    protected RestTemplate getRestTemplate() {
        return new RestTemplate(true, new HttpComponentsClientHttpRequestFactory());
    }
}
