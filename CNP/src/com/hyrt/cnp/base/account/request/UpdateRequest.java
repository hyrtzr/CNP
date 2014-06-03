package com.hyrt.cnp.base.account.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.model.Update;
import com.hyrt.cnp.base.account.service.UpdateService;

/**
 * Created by Zoe on 2014-05-21.
 */
public class UpdateRequest extends NotNeedLoginBaseRequest{

    @Inject
    private UpdateService updateService;

    public UpdateRequest(Context context) {
        super(Update.Model.class, context);
    }

    @Override
    public Base run() {
        return updateService.getNewUpdate();
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String createCacheKey() {
        return "UpdateRequest"+System.currentTimeMillis();
    }
}
