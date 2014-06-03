package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.SchoolService;

/**
 * Created by GYH on 14-1-3.
 */
public class SchoolListRequest extends BaseRequest{

    @Inject
    private SchoolService schoolListService;


    public SchoolListRequest(Class clazz, Context context) {
        super(clazz, context);
    }
    @Override
    public Base run() {
        return schoolListService.getSchoolList(getRestTemplate());
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
