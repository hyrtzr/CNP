package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.model.DynamicPhoto;
import com.hyrt.cnp.base.account.model.Photo;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.AddPhotoService;

import java.io.File;

/**
 * Created by Zoe on 2014-04-11.
 */
public class AddPhotoRequest extends BaseRequest{

    @Inject
    private AddPhotoService addPhotoService;
    private String paid;
    private String photoname;
    private String introduce;
    private File photo_add;
    private DynamicPhoto mPhoto;
    private int type = 0;


    public AddPhotoRequest(Class clazz, Context context,
                           String paid, String photoname,
                           String introduce, File photo_add) {
        super(clazz, context);
        this.paid = paid;
        this.photoname = photoname;
        this.introduce = introduce;
        this.photo_add = photo_add;
    }

    public AddPhotoRequest(Class clazz, Context context, DynamicPhoto photo){
        super(clazz, context);
        this.mPhoto = photo;
        this.type = 1;
    }

    @Override
    public Base run() {
        if(type == 1){
            return addPhotoService.delDynamicPhoto(mPhoto);
        }else{
            return addPhotoService.addPhoto(paid, photoname, introduce, photo_add);
        }
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "addphoto"+System.currentTimeMillis();
    }
}
