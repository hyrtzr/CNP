package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.request.NotNeedLoginBaseRequest;
import com.hyrt.cnp.base.account.service.SchoolService;

/**
 * Created by GYH on 14-1-3.
 */
public class SchoolinfoRequest extends NotNeedLoginBaseRequest{

    @Inject
    private SchoolService schoolListService;

    private int mSid = -1;


    public SchoolinfoRequest(Class clazz, Context context) {
        super(clazz, context);
    }

    public SchoolinfoRequest(Class clazz, Context context, int sid) {
        super(clazz, context);
        this.mSid = sid;

    }

    @Override
    public Base run() {
        Base base = null;
        if(mSid == -1){
            base = schoolListService.getSchoolinfo(getRestTemplate());
        }else{
            base = schoolListService.getSchoolinfo(getRestTemplate(), mSid);
        }
        return base;
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "Schoolinfo"+mSid+System.currentTimeMillis();
    }
}
