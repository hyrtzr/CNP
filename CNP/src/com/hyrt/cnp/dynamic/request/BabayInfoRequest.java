package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.BabayinfoService;

/**
 * Created by GYH on 14-1-23.
 */
public class BabayInfoRequest extends BaseRequest {

    @Inject
    private BabayinfoService schoolListService;
    private String uid;
    public BabayInfoRequest(Class clazz, Context context, String uid) {
        super(clazz, context);
        this.uid=uid;
    }
    @Override
    public Base run() {
            return schoolListService.getBabayinfoData(getRestTemplate(),uid);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "Babayinfo"+uid;
    }
}
