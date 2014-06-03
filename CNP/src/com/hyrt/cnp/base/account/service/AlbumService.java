package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.model.Comment;

import net.oschina.app.AppContext;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GYH on 14-4-6.
 */
public class AlbumService {

    private CNPClient cnpClient;

    public AlbumService(CNPClient cnpClient){
        this.cnpClient = cnpClient;
    }
    public Album.Model getAlbumData(RestTemplate restTemplate){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
//        params.put("cid","117");
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"classroom/album/?" +
                "token={token}&uuid={uuid}&cid={cid}",
                Album.Model.class, params);
    }

    public Album.Model getBabayAlbumData(RestTemplate restTemplate,String uid){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("uid",uid);
//        params.put("uid","222");
        return  restTemplate.getForObject(
                AppContext.getInstance().getString(R.string.path_api)+"home/album_list?"+
                "token={token}&uuid={uuid}&uid={uid}",Album.Model.class, params);
    }


    public Album.Model getMyAlbumData(RestTemplate restTemplate){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        return  restTemplate.getForObject(
                AppContext.getInstance().getString(R.string.path_api)+"home/album_list?"+
                        "token={token}&uuid={uuid}",Album.Model.class, params);
    }

    public Album.Model getMyAlbumData(RestTemplate restTemplate, String more){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("isMore", "old,"+more);
        return  restTemplate.getForObject(
                AppContext.getInstance().getString(R.string.path_api)+"home/album_list?"+
                        "token={token}&uuid={uuid}&isMore={isMore}",Album.Model.class, params);
    }

    public Comment.Model3 AddMyAlbumData(RestTemplate restTemplate,String albumName,String describes){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("albumName",albumName);
        params.put("describes",describes);
        return  restTemplate.getForObject(
                AppContext.getInstance().getString(R.string.path_api)+"home/album_add/?"+
                        "token={token}&uuid={uuid}&albumName={albumName}&describes={describes}",Comment.Model3.class, params);
    }

    public BaseTest delAlbum(String paid){
        cnpClient.configureRequest();
//        MultiValueMap<String, Object> params = cnpClient.getParams();
        Map<String, String> params = cnpClient.getParamsforGet();
        params.put("paid", paid);
        BaseTest result =  getRestTemplate().getForObject(
                AppContext.getInstance().getString(R.string.path_api)+"home/album_del/?" +
                        "token={token}&uuid={uuid}&paid={paid}",
                BaseTest.class, params
        );

        return result;
    }

    public BaseTest alterAlbum(String paid, String albumName, String describes){
        cnpClient.configureRequest();
        MultiValueMap<String, Object> params = cnpClient.getParams();
        params.set("paid", paid);
        params.set("albumName", albumName);
        params.set("describes", describes);
        BaseTest result =  getRestTemplate().postForObject(
                AppContext.getInstance().getString(R.string.path_api)+"home/album_edit/",
                cnpClient.getParams(), BaseTest.class);

        return result;
    }

    protected RestTemplate getRestTemplate() {
        return new RestTemplate(true, new HttpComponentsClientHttpRequestFactory());
    }
}
