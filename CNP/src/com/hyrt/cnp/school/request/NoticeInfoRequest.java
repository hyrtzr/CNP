package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.SchoolNoticeService;

/**
 * Created by GYH on 14-2-26.
 */
public class NoticeInfoRequest extends BaseRequest {

    private String data;
    private String annourceid;

    @Inject
    private SchoolNoticeService schoolNoticeService;
    public NoticeInfoRequest(Class clazz, Context context,String data,String annourceid) {
        super(clazz, context);
        this.data=data;
        this.annourceid=annourceid;
    }

    @Override
    public Base run() {
        if(data.equals("school")){
                return schoolNoticeService.getSchoolNoticeinfo(getRestTemplate(), -1, annourceid);
        }else{
                return schoolNoticeService.getClassroomNoticeinfo(getRestTemplate(), annourceid);
        }
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
    public String getcachekey(){
        return "Noticeinfo"+data+annourceid+System.currentTimeMillis();
    }
}
