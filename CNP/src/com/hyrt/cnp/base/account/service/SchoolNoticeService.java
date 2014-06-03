package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.Notice;

import net.oschina.app.AppContext;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by GYH on 14-1-9.
 */
public class SchoolNoticeService {

    private CNPClient cnpClient;

    public SchoolNoticeService(CNPClient cnpClient){
        this.cnpClient = cnpClient;
    }

    public Notice.Model getNoticelistData(RestTemplate restTemplate, int sid){
        HashMap<String, String>  params = new HashMap<String, String>();
        if(sid != -1){
            params.put("sid", sid+"");
        }
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"school/notice?sid={sid}",
                Notice.Model.class,params);
    }

    public Notice.Model getNoticelistDatamore(RestTemplate restTemplate,String more, int sid){
        HashMap<String, String>  params = new HashMap<String, String>();

        params.put("isMore","old,"+more);
        if(sid != -1){
            params.put("sid", sid+"");
        }
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"school/notice?sid={sid}&isMore={isMore}",
                Notice.Model.class,params);
    }
    /*
    * 获取班级公告列表
    * */
    public Notice.Model getClassroomNoticelistData(RestTemplate restTemplate){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", "e1ac72b3cf9902f6db8c88f42728db82");
//        params.put("uuid", "104");
//        params.put("cid","117");
        android.util.Log.i("tag", "ckid:"+params.get("cid") +" token:"+params.get("token")+" uuid:"+params.get("uuid"));
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"classroom/notice/?" +
                "token={token}&uuid={uuid}&cid={cid}",
                Notice.Model.class,params);
    }
    /*
    * 获取班级公告列表
    * */
    public Notice.Model getClassroomNoticelistDatamore(RestTemplate restTemplate,String more){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("isMore","old,"+more);
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", "e1ac72b3cf9902f6db8c88f42728db82");
//        params.put("uuid", "104");
//        params.put("cid","117");
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"classroom/notice/?" +
                "token={token}&uuid={uuid}&cid={cid}&isMore={isMore}",
                Notice.Model.class,params);
    }

    /*
    * 获取学校公告详细信息
    * */
    public Notice.Model2 getSchoolNoticeinfo(RestTemplate restTemplate, int sid, String annourceid){
        HashMap<String, String> params = null;
        if(sid == -1){
            cnpClient.configureRequest();
            params = cnpClient.getParamsforGet();
        }else{
            params = new HashMap<String, String>();
            params.put("sid", sid+"");
        }
        params.put("annourceid",annourceid);
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"school/notice_info/?sid={sid}&annourceid={annourceid}",
                Notice.Model2.class,params);
    }

    /*
    * 获取班级公告详细信息
    * */
    public Notice.Model2 getClassroomNoticeinfo(RestTemplate restTemplate,String annourceid){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("annourceid",annourceid);
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"classroom/notice_info/?" +
                "token={token}&uuid={uuid}&cid={cid}&annourceid={annourceid}",
                Notice.Model2.class,params);
    }
 }
