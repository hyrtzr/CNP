package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.request.NotNeedLoginBaseRequest;
import com.hyrt.cnp.base.account.service.SchoolNoticeService;

/**
 * Created by GYH on 14-2-26.
 */
public class NotNeedLoginNoticeInfoRequest extends NotNeedLoginBaseRequest {

    private String data;
    private String annourceid;
    private int sid = -1;

    @Inject
    private SchoolNoticeService schoolNoticeService;
    public NotNeedLoginNoticeInfoRequest(Class clazz, Context context, String data, int sid, String annourceid) {
        super(clazz, context);
        this.data=data;
        this.annourceid=annourceid;
        this.sid = sid;
    }

    @Override
    public Base run() {
            return schoolNoticeService.getSchoolNoticeinfo(getRestTemplate(), sid, annourceid);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
    public String getcachekey(){
        return "Noticeinfo"+data+annourceid+sid;
    }
}
