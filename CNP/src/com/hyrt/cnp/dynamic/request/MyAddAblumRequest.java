package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.AlbumService;

/**
 * Created by GYH on 2014/4/6.
 */
public class MyAddAblumRequest extends BaseRequest {

    @Inject
    private AlbumService schoolListService;
    private String albumName,describes;
    public MyAddAblumRequest(Class clazz, Context context,String albumName,String describes) {
        super(clazz, context);
        this.albumName=albumName;
        this.describes=describes;
    }
    @Override
    public Base run() {
        android.util.Log.i("tag", "albumName："+albumName+" describes:"+describes);
        return schoolListService.AddMyAlbumData(getRestTemplate(),albumName,describes);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "addalbum"+albumName;
    }
}
