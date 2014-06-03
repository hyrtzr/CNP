package com.hyrt.cnp.base.account.service;

import android.util.Log;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.model.UserDetail;
import com.hyrt.cnp.base.account.model.UtilVar;
import com.hyrt.cnp.base.account.utils.LogHelper;

import net.oschina.app.AppContext;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.HashMap;

/**
 * Created by yepeng on 13-12-11.
 *
 */
public class UserService{

     private CNPClient cnpClient;

    public UserService(CNPClient cnpClient){
        this.cnpClient = cnpClient;
    }

    public UtilVar getUtilvar(String name){
//        cnpClient.configureRequest();
//        HashMap<String, String> params = cnpClient.getParamsforGet();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name",name);
        return  getRestTemplate().getForObject(AppContext.getInstance().getString(R.string.path_api)+"var/get_var?name={name}",
                UtilVar.class, params);
    }

    public UtilVar getUtilvar(String name, String provinceName){
//        cnpClient.configureRequest();
//        HashMap<String, String> params = cnpClient.getParamsforGet();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name",name);
        params.put("provinceName", provinceName);
        android.util.Log.i("tag", "name:"+name+" provinceName:"+provinceName);
        return  getRestTemplate().getForObject(AppContext.getInstance().getString(R.string.path_api)+"var/get_var?" +
                        "name={name}&provinceName={provinceName}",
                UtilVar.class, params);
    }

    public UtilVar getUtilvar(String name, int province){
//        cnpClient.configureRequest();
//        HashMap<String, String> params = cnpClient.getParamsforGet();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name",name);
        params.put("province", province+"");
        android.util.Log.i("tag", "name:"+name+" province:"+province);
        return  getRestTemplate().getForObject(AppContext.getInstance().getString(R.string.path_api)+"var/get_var?" +
                        "name={name}&province={province}",
                UtilVar.class, params);
    }

    public UserDetail.UserDetailModel getUser(){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        try{
            AppContext.getInstance().uuid = Integer.parseInt(params.get("uuid"));
        }catch (NumberFormatException e){
            AppContext.getInstance().uuid = -1;
        }

        Log.i("Fulls", "token:"+params.get("token")+" uuid:"+params.get("uuid"));
        return  getRestTemplate().getForObject(AppContext.getInstance().getString(R.string.path_api)+"user/info?token={token}&uuid={uuid}",UserDetail.UserDetailModel.class,params);
    }

    public BaseTest modifyUserFace(File faceFile){
        cnpClient.configureRequest();
        MultiValueMap<String, Object> params = cnpClient.getParams();
        Resource face = new FileSystemResource(faceFile);
        params.set("file", face);
        BaseTest result = getRestTemplate().postForObject(AppContext.getInstance().getString(R.string.path_api)+"user/avatar/", cnpClient.getParams(),BaseTest.class);
        return result;
    }

    public BaseTest modifyUserFaceBackground(File faceFile){
        cnpClient.configureRequest();
        MultiValueMap<String, Object> params = cnpClient.getParams();
        Resource face = new FileSystemResource(faceFile);
        params.set("file", face);
        BaseTest result = getRestTemplate().postForObject(AppContext.getInstance().getString(R.string.path_api)+"user/setback/", cnpClient.getParams(),BaseTest.class);
        return result;
    }

    public BaseTest modifyUserPassword(String password,String newPwd,String repeatPwd){
        cnpClient.configureRequest();
        MultiValueMap<String, Object> params = cnpClient.getParams();
        params.set("password",password);
        params.set("new_pwd",newPwd);
        params.set("repeat_pwd",repeatPwd);
        BaseTest result = getRestTemplate().postForObject(AppContext.getInstance().getString(R.string.path_api)+"user/password/", cnpClient.getParams(),BaseTest.class);
        return result;
    }



    protected RestTemplate getRestTemplate() {
        return new RestTemplate(true, new HttpComponentsClientHttpRequestFactory());
    }

    public BaseTest modifyUserInfo(String renname, String birthday,String sex, String national, String bloodType,String ethnic, String intro) {
        cnpClient.configureRequest();
        MultiValueMap<String, Object> params = cnpClient.getParams();
//        if(renname.toString().length() != 0)
            params.set("renname",renname);
//        if(birthday.toString().length() != 0)
            params.set("birthday",birthday);
//        if(sex.toString().length() != 0)
            params.set("sex",sex);
//        if(bloodType.toString().length() != 0)
            params.set("bloodType",bloodType);
//        if(national.toString().length() != 0)
            params.set("nationality",national);
//        if(ethnic.toString().length() != 0)
            params.set("ethnic",ethnic);
//        if(intro.toString().length() != 0)
            params.set("intro",intro);
        LogHelper.i("tag", "sex:"+sex);
        BaseTest result = getRestTemplate().postForObject(AppContext.getInstance().getString(R.string.path_api)+"user/edit/", cnpClient.getParams(),BaseTest.class);
        return result;
    }
}
