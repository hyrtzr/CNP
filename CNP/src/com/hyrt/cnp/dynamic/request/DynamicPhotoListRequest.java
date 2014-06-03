package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.PhotoService;

/**
 * Created by Zoe on 2014-04-12.
 */
public class DynamicPhotoListRequest extends BaseRequest{
    @Inject
    private PhotoService schoolListService;

    private int paid;
    private String more = "1";

    public DynamicPhotoListRequest(Class clazz, Context context,int paid) {
        super(clazz, context);
        this.paid=paid;
    }

    public DynamicPhotoListRequest(Class clazz, Context context,int paid, String more) {
        super(clazz, context);
        this.paid=paid;
        this.more = more;
    }

    @Override
    public Base run() {
        if("1".equals(more)){
            return schoolListService.getDynamicAlbumphotolistData(getRestTemplate(),paid);
        }else{
            return schoolListService.getDynamicAlbumphotolistData(getRestTemplate(),paid, more);
        }
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "dynamicPhotoList"+paid+System.currentTimeMillis();
    }
}
