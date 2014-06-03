package com.hyrt.cnp.classroom.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.RecipeInfo;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.classroom.ui.ClassroomRecipeInfoActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-16.
 */
public class ClassroomRecipeInfoRequestListener extends BaseRequestListener{
    /**
     * @param context
     */
    public ClassroomRecipeInfoRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
//        showMessage(R.string.nodata_title,R.string.nodata_content);
        super.onRequestFailure(e);
        ClassroomRecipeInfoActivity activity = (ClassroomRecipeInfoActivity)context.get();
        activity.updateUI(null);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){
            ClassroomRecipeInfoActivity activity = (ClassroomRecipeInfoActivity)context.get();
            RecipeInfo.Model result= (RecipeInfo.Model)data;
            activity.updateUI(result);
        }else{
//            showMessage(R.string.nodata_title,R.string.nodata_content);
            ClassroomRecipeInfoActivity activity = (ClassroomRecipeInfoActivity)context.get();
            activity.updateUI(null);
        }

    }

    @Override
    public ClassroomRecipeInfoRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
