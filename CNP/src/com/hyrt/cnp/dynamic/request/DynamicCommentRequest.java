package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.DynamicService;

/**
 * Created by GYH on 14-1-16.
 */
public class DynamicCommentRequest extends BaseRequest{

    @Inject
    private DynamicService schoolListService;

    private Dynamic dynamic;
    private String infoid, siteid;
    private int type = 0 ; //0:转发

    public DynamicCommentRequest(Class clazz, Context context,Dynamic dynamic) {
        super(clazz, context);
        this.dynamic=dynamic;
    }

    public DynamicCommentRequest(Class clazz, Context context, String infoid, String siteid) {
        super(clazz, context);
        this.infoid = infoid;
        this.siteid = siteid;
    }

    @Override
    public Base run() {
        return schoolListService.adddynamiczfData(dynamic);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "dynamicComment"+dynamic.get_id()+System.currentTimeMillis();
    }
}
