package com.hyrt.cnp.base.account.service;

import android.widget.Toast;

import com.hyrt.cnp.base.account.model.PositionInfo;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by Zoe on 2014-04-05.
 */
public class PositionInfoService {

    public PositionInfo.Model getPositionInfo( RestTemplate restTemplate, double lat, double lng){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("lng", lng+"");
        params.put("lat", lat+"");
        android.util.Log.i("tag", "lat:"+lat+" lng:"+lng);
        StringBuffer path = new StringBuffer("http://api.map.baidu.com/geocoder/v2/?" +
                "ak=3KTS4mpYnE8ZSGr4v1fEFmPO&location={lat},{lng}&output=json");
        return restTemplate.getForObject(path.toString(),
                PositionInfo.Model.class, params);
    }
}
