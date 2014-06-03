package com.hyrt.cnp.classroom.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.AlbumService;

/**
 * Created by GYH on 14-1-16.
 */
public class ClassroomAlbumRequest extends BaseRequest{

    @Inject
    private AlbumService schoolListService;
    private String Category;
    private String uid;
    public ClassroomAlbumRequest(Class clazz, Context context,String Category) {
        super(clazz, context);
        this.Category=Category;
    }
    public ClassroomAlbumRequest(Class clazz, Context context,String Category,String uid) {
        super(clazz, context);
        this.Category=Category;
        this.uid=uid;
    }
    @Override
    public Base run() {
        if(Category.equals("BabayIndexActivity")){
            return schoolListService.getBabayAlbumData(getRestTemplate(),uid);
        }else{
            return schoolListService.getAlbumData(getRestTemplate());
        }

    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "classroomalbum"+uid;
    }
}
