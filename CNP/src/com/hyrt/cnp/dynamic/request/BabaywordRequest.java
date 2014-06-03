package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.DynamicService;

/**
 * Created by GYH on 14-1-23.
 */
public class BabaywordRequest extends BaseRequest {

    @Inject
    private DynamicService schoolListService;
    private String uid;
    private String more;
    public BabaywordRequest(Class clazz, Context context, String uid,String more) {
        super(clazz, context);
        this.uid=uid;
        this.more=more;
    }
    @Override
    public Base run() {
        if(more.equals("1")){
            return schoolListService.getBabaywordData(getRestTemplate(),uid);
        }else{
            return schoolListService.getBabaywordDataMore(getRestTemplate(), uid, more);
        }
          
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "Babyword"+uid+more;
    }
}
