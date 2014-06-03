package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.Comment;

import net.oschina.app.AppContext;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GYH on 14-1-22.
 */
public class CommentService {

    private CNPClient cnpClient;

    public CommentService(CNPClient cnpClient){
        this.cnpClient = cnpClient;
    }

    public Comment.Model getCommentlistData(RestTemplate restTemplate,String infoid,String siteid){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("infoid",infoid);
        params.put("siteid",siteid);
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"classroom/comment?" +
                "token={token}&uuid={uuid}&infoid={infoid}&siteid={siteid}",
                Comment.Model.class,params);
    }

    public Comment.Model getCommentlistData(RestTemplate restTemplate,String infoid,String siteid, String more){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("infoid",infoid);
        params.put("siteid",siteid);
        params.put("isMore", "old,"+more);
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"classroom/comment?" +
                        "token={token}&uuid={uuid}&infoid={infoid}&siteid={siteid}&isMore={isMore}",
                Comment.Model.class,params);
    }

    public Comment.Model getDynamicCommentlistData(RestTemplate restTemplate,String infoid,String siteid){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("infoid",infoid);
        params.put("siteid",siteid);
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"home/comment/?" +
                        "token={token}&uuid={uuid}&infoid={infoid}&siteid={siteid}",
                Comment.Model.class,params);
    }

    public Comment.Model getCommenthomeData(RestTemplate restTemplate,String infoid,String siteid){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("infoid",infoid);
        params.put("siteid",siteid);
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"home/comment?" +
                "token={token}&uuid={uuid}&infoid={infoid}&siteid={siteid}",
                Comment.Model.class,params);
    }

    public Comment.Model getCommenthomeData(RestTemplate restTemplate,String infoid,String siteid, String more){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("infoid",infoid);
        params.put("siteid",siteid);
        params.put("isMore", "old,"+more);
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"home/comment?" +
                        "token={token}&uuid={uuid}&infoid={infoid}&siteid={siteid}&isMore={isMore}",
                Comment.Model.class,params);
    }

    public Comment.Model3 addCommentData(Comment comment){
        cnpClient.configureRequest();
        MultiValueMap<String,Object> params = cnpClient.getParams();
        params.set("infoid", comment.getInfoID()+"");
        params.set("title", comment.getInfoTitle());
        params.set("userid", comment.getUserid() + "");
        params.set("nid", comment.getInfoNurseryId() + "");
        params.set("infocid", comment.getInfoClassroomId() + "");
        params.set("siteid", comment.getSiteid());
        params.set("url", comment.getUrl());
        params.set("lstatus", comment.getLstatus());
        params.set("con", comment.getContent());
        params.set("reply", comment.getReply());
        params.set("rcon", comment.getRecontent());
        params.set("ruid", comment.getReuserId());
        params.set("rename", comment.getReusername());
        params.set("redate", comment.getRedate());
        return  getRestTemplate().postForObject(
                AppContext.getInstance().getString(R.string.path_api)+"classroom/comment_add", params, Comment.Model3.class);
    }

    public Comment.Model3 adddynamicCommentData(Comment comment, int type){
        cnpClient.configureRequest();
        Map<String,String> params = cnpClient.getParamsforGet();
        params.put("infoid", comment.getInfoid2() + "");
        params.put("title", comment.getInfoTitle());
        params.put("userid", comment.getInfoUserId());
        params.put("nid", comment.getInfoNurseryId() + "");
        params.put("infocid", comment.getInfoClassroomId() + "");
        params.put("siteid", comment.getSiteid());
        params.put("url", "null");
        params.put("lstatus", comment.getLstatus());
        params.put("con", comment.getContent());
        params.put("reply", comment.getReply());
        if(type == 1){
            params.put("rcon", comment.getRecontent());
            params.put("ruid", comment.getReuserId());
            params.put("rename", comment.getReusername());
            params.put("redate", comment.getRedate());
        }

        android.util.Log.i("tag", comment.toString()+"");
        String path = "";
        if(type == 1){
            path = AppContext.getInstance().getString(R.string.path_api)+"home/comment_add/?token={token}&uuid={uuid}" +
                    "&infoid={infoid}&title={title}&userid={userid}&nid={nid}&infocid={infocid}" +
                    "&siteid={siteid}&url={url}&lstatus={lstatus}&con={con}&reply={reply}" +
                    "&rcon={rcon}&ruid={ruid}&rename={rename}&redate={redate}";
        }else{
            path = AppContext.getInstance().getString(R.string.path_api)+"home/comment_add/?token={token}&uuid={uuid}" +
                    "&infoid={infoid}&title={title}&userid={userid}&nid={nid}&infocid={infocid}" +
                    "&siteid={siteid}&url={url}&lstatus={lstatus}&con={con}&reply={reply}";
        }
        if(comment.getReusername() != null && comment.getReusername().trim().length() > 0){
            params.put("uname", comment.getReusername());
            path+="&uname={uname}";
        }
        android.util.Log.i("tag", "path:"+path+" uname:"+comment.getReusername());
        return  getRestTemplate().getForObject(
                path, Comment.Model3.class, params);
    }

    protected RestTemplate getRestTemplate() {
        return new RestTemplate(true, new HttpComponentsClientHttpRequestFactory());
    }
}
