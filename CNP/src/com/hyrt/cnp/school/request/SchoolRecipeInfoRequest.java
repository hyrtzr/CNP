package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.model.Recipe;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.request.NotNeedLoginBaseRequest;
import com.hyrt.cnp.base.account.service.RecipeInfoService;

/**
 * Created by GYH on 14-1-3.
 */
public class SchoolRecipeInfoRequest extends NotNeedLoginBaseRequest{

    @Inject
    private RecipeInfoService schoolListService;
    private Recipe recipe;

    public SchoolRecipeInfoRequest(Class clazz, Context context,Recipe recipe) {
        super(clazz, context);
        this.recipe=recipe;
    }

    @Override
    public Base run() {
        return schoolListService.getRecipeWeekData(getRestTemplate(),recipe);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "RecipeWeek"+recipe.hashCode();
    }
}
