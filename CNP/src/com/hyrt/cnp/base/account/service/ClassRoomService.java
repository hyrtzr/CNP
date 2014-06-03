package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.ClassRoom;
import com.hyrt.cnp.base.account.utils.LogHelper;

import net.oschina.app.AppContext;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by GYH on 14-1-14.
 */
public class ClassRoomService {

    private CNPClient cnpClient;

    public ClassRoomService(CNPClient cnpClient){
        this.cnpClient = cnpClient;
    }

    public ClassRoom.Model getClassRoomListData(RestTemplate restTemplate, int sid){
        HashMap<String, String> params = null;
        if(sid == -1){
            cnpClient.configureRequest();
            params = cnpClient.getParamsforGet();
        }else{
            params = new HashMap<String, String>();
            params.put("sid", sid+"");
        }
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"school/classroom/?" +
                "sid={sid}",
                ClassRoom.Model.class, params);
    }

    public ClassRoom.Model2 getClassRoomInfoData1(RestTemplate restTemplate){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"classroom/classroom/?" +
                "token={token}&uuid={uuid}&cid={cid}",
                ClassRoom.Model2.class, params);
    }
}
