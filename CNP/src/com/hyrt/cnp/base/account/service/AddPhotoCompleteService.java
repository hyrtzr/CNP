package com.hyrt.cnp.base.account.service;

import android.util.Log;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.BaseTest;

import net.oschina.app.AppContext;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by Zoe on 2014-04-24.
 */
public class AddPhotoCompleteService{
    private CNPClient cnpClient;

    public AddPhotoCompleteService(CNPClient cnpClient) {
        this.cnpClient = cnpClient;
    }

    public BaseTest addDynamic(String paid, String photoname, List<String> msgs){
        cnpClient.configureRequest();
        StringBuffer fstr = new StringBuffer("");
        for(int i=0; i<msgs.size(); i++){
            fstr.append(msgs.get(i));
            fstr.append("**");
        }
        String fstr2 = fstr.toString();
        fstr2 = fstr2.substring(0, fstr2.length()-2);
        Log.i("tag", "fstr2:"+fstr2);
        MultiValueMap<String, Object> params = cnpClient.getParams();
        params.set("paid", paid);
        params.set("photoname", photoname);
//        try {
//            String fstr3 = URLEncoder.encode(fstr2, "UTF-8");
//            params.set("fstr", fstr3);
//        }catch (UnsupportedEncodingException e) {
            params.set("fstr", fstr2);
//        }

        return  getRestTemplate().postForObject(
                AppContext.getInstance().getString(R.string.path_api)+"home/photo_dynamic/", cnpClient.getParams(), BaseTest.class);
    }

    protected RestTemplate getRestTemplate() {
        return new RestTemplate(true, new HttpComponentsClientHttpRequestFactory());
    }
}
