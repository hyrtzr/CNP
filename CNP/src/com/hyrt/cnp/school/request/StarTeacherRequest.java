package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.request.NotNeedLoginBaseRequest;
import com.hyrt.cnp.base.account.service.TeacherService;

/**
 * Created by GYH on 14-1-14.
 */
public class StarTeacherRequest extends NotNeedLoginBaseRequest {

    @Inject
    private TeacherService teacherService;
    private int sid = -1;

    public StarTeacherRequest(Class clazz, Context context) {
        super(clazz, context);
    }

    public StarTeacherRequest(Class clazz, Context context, int sid) {
        super(clazz, context);
        this.sid = sid;
    }

    @Override
    public Base run() {
        return teacherService.getStarteacherData(getRestTemplate(), sid);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
    public String getcachekey(){
        return "Starteacher"+sid;
    }
}
