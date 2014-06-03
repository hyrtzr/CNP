package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.request.NotNeedLoginBaseRequest;
import com.hyrt.cnp.base.account.service.StarBabayService;

/**
 * Created by GYH on 14-1-14.
 * 明星宝宝
 */
public class StarBabayRequest extends NotNeedLoginBaseRequest {

    @Inject
    private StarBabayService teacherService;
    private int sid = -1;

    public StarBabayRequest(Class clazz, Context context) {
        super(clazz, context);
    }

    public StarBabayRequest(Class clazz, Context context, int sid) {
        super(clazz, context);
        this.sid = sid;
    }

    @Override
    public Base run() {
        return teacherService.getStarbabayData(getRestTemplate(), sid);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
    public String getcachekey(){
        return "Starbabay"+sid;
    }
}
