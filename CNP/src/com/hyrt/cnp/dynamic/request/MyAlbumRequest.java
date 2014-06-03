package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.AlbumService;

/**
 * Created by GYH on 14-1-16.
 */
public class MyAlbumRequest extends BaseRequest{

    private int type = 0;//0:获取专辑列表；1:删除专辑；2:修改专辑
    private String paid, albumName, describes, more="1";

    @Inject
    private AlbumService schoolListService;
    public MyAlbumRequest(Class clazz, Context context) {
        super(clazz, context);
        this.type = 0;
    }

    public MyAlbumRequest(String more, Class clazz, Context context) {
        super(clazz, context);
        this.type = 0;
        this.more = more;
    }

    public MyAlbumRequest(Class clazz, Context context, String paid) {
        super(clazz, context);
        this.paid = paid;
        this.type = 1;
    }

    public MyAlbumRequest(Class clazz, Context context, String paid, String albumName, String describes) {
        super(clazz, context);
        this.type = 2;
        this.paid = paid;
        this.albumName = albumName;
        this.describes = describes;
    }

    @Override
    public Base run() {
        if(type == 1){
            return schoolListService.delAlbum(paid);
        }else if(type == 2){
            return schoolListService.alterAlbum(paid, albumName, describes);
        }else{
            android.util.Log.i("tag", "more:"+more);
            if("1".equals(more)){
                return schoolListService.getMyAlbumData(getRestTemplate());
            }else{
                return schoolListService.getMyAlbumData(getRestTemplate(), more);
            }
        }
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "myalbum11233"+System.currentTimeMillis();
    }
}
