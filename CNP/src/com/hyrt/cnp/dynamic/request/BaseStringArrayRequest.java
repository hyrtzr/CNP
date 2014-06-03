package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.model.BaseStringArray;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.BaseStringArrayService;

/**
 * Created by HY on 2014-05-14.
 */
public class BaseStringArrayRequest extends BaseRequest{

    @Inject
    BaseStringArrayService baseStringArrayService;

    private int type = 0;//0:与我相关条数

    public BaseStringArrayRequest(Context context) {
        super(BaseStringArray.class, context);
    }

    public BaseStringArrayRequest(Context context, int type) {
        super(BaseStringArray.class, context);
    }

    @Override
    public Base run() {
        if(type == 0){
            return baseStringArrayService.getAboutmeNums(getRestTemplate());
        }
        return null;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "baseStringArray"+System.currentTimeMillis();
    }
}
