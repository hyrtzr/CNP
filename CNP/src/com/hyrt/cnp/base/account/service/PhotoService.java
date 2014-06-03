package com.hyrt.cnp.base.account.service;

import android.util.Log;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.model.DynamicPhoto;
import com.hyrt.cnp.base.account.model.Photo;

import net.oschina.app.AppContext;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by GYH on 14-1-14.
 */
public class PhotoService {
    private CNPClient cnpClient;

    public PhotoService(CNPClient cnpClient){
        this.cnpClient = cnpClient;
    }

    /**
     * 获取学校图片
     * */

    public Photo.Model getphotolistData(RestTemplate restTemplate,String pkind, int sid){
        HashMap<String, String> params = null;
        if(sid == -1){
            cnpClient.configureRequest();
            params = cnpClient.getParamsforGet();
        }else{
            params = new HashMap<String, String>();
            params.put("sid",sid+"");
        }
        android.util.Log.i("tag", "pkind:"+pkind+" sid:"+params.get("sid"));
        params.put("pkind",pkind);
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"school/photo/?" +
                "sid={sid}&pkind={pkind}",
                Photo.Model.class, params);
    }

    /**
     * 获取班级相册图片
     * */
    //TODO MODIFY CID
     public Photo.Model getClassroomAlbumphotolistData(RestTemplate restTemplate,int paid){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
//        params.put("cid","117");
        params.put("paid",paid+"");
         Log.i("tag", "cid:"+params.get("cid")+" paid:"+paid);
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"classroom/photo_album/?" +
                "token={token}&uuid={uuid}&cid={cid}&paid={paid}",
                Photo.Model.class, params);
    }

    public DynamicPhoto.Model getDynamicAlbumphotolistData(RestTemplate restTemplate, int paid){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("paid",paid+"");
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"home/photo_list/?" +
                        "token={token}&uuid={uuid}&cid={cid}&paid={paid}",
                DynamicPhoto.Model.class, params);
    }

    public DynamicPhoto.Model getDynamicAlbumphotolistData(RestTemplate restTemplate, int paid, String more){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("paid",paid+"");
        params.put("isMore", "old,"+more);
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"home/photo_list/?" +
                        "token={token}&uuid={uuid}&cid={cid}&paid={paid}&isMore={isMore}",
                DynamicPhoto.Model.class, params);
    }
}
