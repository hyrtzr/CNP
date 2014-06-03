package com.hyrt.cnp.base.account.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.service.ClassroomBabayService;

/**
 * Created by GYH on 14-1-16.
 */
public class BaseClassroomBabayRequest extends BaseRequest{

    private boolean isAt = false;

    @Inject
    private ClassroomBabayService schoolListService;


    public BaseClassroomBabayRequest(Class clazz, Context context) {
        super(clazz, context);
    }

    public BaseClassroomBabayRequest(Class clazz, Context context, boolean isAt) {
        super(clazz, context);
        this.isAt = isAt;
    }

    @Override
    public Base run() {
        return schoolListService.getclassroombabayData(getRestTemplate(), isAt);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "classroombabay";
    }
}
