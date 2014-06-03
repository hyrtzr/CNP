package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.request.NotNeedLoginBaseRequest;
import com.hyrt.cnp.base.account.service.RecipeService;

/**
 * Created by GYH on 14-1-3.
 */
public class SchoolRecipeRequest extends NotNeedLoginBaseRequest{

    @Inject
    private RecipeService schoolListService;
    private int sid = -1;


    public SchoolRecipeRequest(Class clazz, Context context) {
        super(clazz, context);
    }

    public SchoolRecipeRequest(Class clazz, Context context, int sid) {
        super(clazz, context);
        this.sid = sid;
    }

    @Override
    public Base run() {
        return schoolListService.getSchoolRecipeData(getRestTemplate(), sid);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "SchoolRecipe"+sid;
    }
}
