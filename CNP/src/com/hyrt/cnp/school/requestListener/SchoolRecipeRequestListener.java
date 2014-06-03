package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.Recipe;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.ui.SchoolRecipeActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-14.
 */
public class SchoolRecipeRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public SchoolRecipeRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        SchoolRecipeActivity activity = (SchoolRecipeActivity)context.get();
        activity.updateUI(null);
//        showMessage(R.string.nodata_title,R.string.nodata_content);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data != null){
            SchoolRecipeActivity activity = (SchoolRecipeActivity)context.get();
            Recipe.Model result= (Recipe.Model)data;
            activity.updateUI(result);
        }else{
            SchoolRecipeActivity activity = (SchoolRecipeActivity)context.get();
            activity.updateUI(null);
//            showMessage(R.string.nodata_title,R.string.nodata_content);
        }
    }

    @Override
    public SchoolRecipeRequestListener start() {
        showIndeterminate(R.string.schoolrecipe_pg);
        return this;
    }
}
