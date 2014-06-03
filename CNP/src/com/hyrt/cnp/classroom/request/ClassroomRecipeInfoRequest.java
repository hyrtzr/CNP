package com.hyrt.cnp.classroom.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.RecipeInfoService;

/**
 * Created by GYH on 14-1-16.
 */
public class ClassroomRecipeInfoRequest extends BaseRequest{

    @Inject
    private RecipeInfoService schoolListService;

    private String time;
    public ClassroomRecipeInfoRequest(Class clazz, Context context,String time) {
        super(clazz, context);
        this.time=time;
    }
    @Override
    public Base run() {
        return schoolListService.getRecipeDayData(getRestTemplate(),time);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "classroomrecepeinfo"+time;
    }
}
