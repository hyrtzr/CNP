package com.hyrt.cnp.base.account.service;


import android.util.Log;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.Dynamic;

import net.oschina.app.AppContext;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by GYH on 14-1-16.
 */
public class DynamicService {

    private CNPClient cnpClient;

    public DynamicService(CNPClient cnpClient){
        this.cnpClient = cnpClient;
    }

    //TODO MODIFY UID
    public Dynamic.Model getBabayDynamicData(RestTemplate restTemplate,String uid, boolean isAll){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("uid",uid);
//        params.put("uid","222");
        String urlKey = "";
        if(isAll){
            urlKey = "dynamic_all";
        }else{
            urlKey = "dynamic_user";
        }

        Log.i("tag", "isAll:"+isAll+" token:"+params.get("token")+" uuid:"+params.get("uuid")+" uid:"+params.get("uid"));

        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"home/"+urlKey+"/?" +
                "token={token}&uuid={uuid}&uid={uid}",
                Dynamic.Model.class, params);
    }

    public Dynamic.Model getRefreshDynamicData(
            RestTemplate restTemplate,String uid,
            String startPosttime, String endPosttime,
            boolean isAll){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("uid",uid);
        params.put("ptime", endPosttime);
        String urlKey = "";
        if(isAll){
            urlKey = "dynamic_all";
        }else{
            urlKey = "dynamic_user";
        }

        Log.i("tag", "isAll:"+isAll+" token:"+params.get("token")+" uuid:"+params.get("uuid")+" uid:"+params.get("uid"));

        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"home/"+urlKey+"/?" +
                        "token={token}&uuid={uuid}&uid={uid}&ptime={ptime}",
                Dynamic.Model.class, params);
    }

    public Dynamic.Model getBabayDynamicMoreData(RestTemplate restTemplate,String uid,String more, boolean isAll){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("uid",uid);
//        params.put("uid","222");
//        params.put("more",more);
        String urlKey = "";
        if(isAll){
            urlKey = "dynamic_all";
        }else{
            urlKey = "dynamic_user";
        }
        String path = "";
        params.put("isMore", "old,"+more);

        if(isAll){
            path = AppContext.getInstance().getString(R.string.path_api)+"home/"+urlKey+"/?" +
                    "token={token}&uuid={uuid}&uid={uid}&isMore={isMore}";
        }else{
            path = AppContext.getInstance().getString(R.string.path_api)+"home/"+urlKey+"/?" +
                    "token={token}&uuid={uuid}&uid={uid}&isMore={isMore}";
        }
        return  restTemplate.getForObject(path, Dynamic.Model.class, params);
    }

    public Dynamic.Model getBabayDynamicData(RestTemplate restTemplate,String uid, String did){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("uid",uid);
        params.put("did", did);
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"home/dynamic_tranlist/?" +
                        "token={token}&uuid={uuid}&uid={uid}&did={did}",
                Dynamic.Model.class, params);
    }

    public Dynamic.Model getBabayDynamicData(RestTemplate restTemplate,String uid, String did, String more){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("uid",uid);
        params.put("did", did);
        params.put("isMore", "old,"+more);
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"home/dynamic_tranlist/?" +
                        "token={token}&uuid={uuid}&uid={uid}&did={did}&isMore={isMore}",
                Dynamic.Model.class, params);
    }

    public Dynamic.Model getBabaywordData(RestTemplate restTemplate,String uid){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("uid",uid);
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"home/dynamic_my?" +
                "token={token}&uuid={uuid}&uid={uid}",
                Dynamic.Model.class, params);
    }
    public Dynamic.Model getBabaywordDataMore(RestTemplate restTemplate,String uid,String more){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("uid",uid);
        params.put("isMore","old,"+more);
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"home/dynamic_my?" +
                "token={token}&uuid={uuid}&uid={uid}&isMore={isMore}",
                Dynamic.Model.class, params);
    }


    public Dynamic.Model3 adddynamiczfData(Dynamic dynamic){
        cnpClient.configureRequest();
        MultiValueMap<String,Object> params = cnpClient.getParams();
        params.set("did", dynamic.get_id());
        params.set("content", dynamic.getContent());

        Dynamic.Model3 result = getRestTemplate().postForObject(
                AppContext.getInstance().getString(R.string.path_api)+"home/dynamic_tran/", params, Dynamic.Model3.class);
        return result;
    }

    protected RestTemplate getRestTemplate() {
        return new RestTemplate(true, new HttpComponentsClientHttpRequestFactory());
    }
}
