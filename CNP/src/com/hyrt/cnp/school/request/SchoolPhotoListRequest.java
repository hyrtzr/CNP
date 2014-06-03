package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.request.NotNeedLoginBaseRequest;
import com.hyrt.cnp.base.account.service.PhotoService;

/**
 * Created by GYH on 14-1-3.
 */
public class SchoolPhotoListRequest extends NotNeedLoginBaseRequest{

    @Inject
    private PhotoService schoolListService;

    private String pkind;
    private int mSid = -1;

    public SchoolPhotoListRequest(Class clazz, Context context,String pkind) {
        super(clazz, context);
        this.pkind=pkind;
    }

    public SchoolPhotoListRequest(Class clazz, Context context,String pkind, int sid) {
        super(clazz, context);
        this.pkind=pkind;
        this.mSid = sid;
    }

    @Override
    public Base run() {
        return schoolListService.getphotolistData(getRestTemplate(),pkind, mSid);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "Schoolphotolist"+pkind+mSid;
    }
}
