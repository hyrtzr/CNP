package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.request.NotNeedLoginBaseRequest;
import com.hyrt.cnp.base.account.service.SchoolNoticeService;

 /**
 * Created by GYH on 14-1-9.
 */
public class SchoolNoticeListRequest extends BaseRequest {

    private String data;
    private String more;
    private int sid = -1;

    @Inject
    private SchoolNoticeService schoolNoticeService;
    public SchoolNoticeListRequest(Class clazz, Context context,String data,String more) {
        super(clazz, context);
        this.data=data;
        this.more=more;
    }

     public SchoolNoticeListRequest(Class clazz, Context context,String data,String more, int sid) {
         super(clazz, context);
         this.data=data;
         this.more=more;
         this.sid = sid;
         android.util.Log.i("tag", "SchoolNoticeListRequest:"+sid);
     }

    @Override
    public Base run() {
        if(data.equals("school")){
            if(more.equals("1")){
                return schoolNoticeService.getNoticelistData(getRestTemplate(), sid);
            }else{
                return schoolNoticeService.getNoticelistDatamore(getRestTemplate(), more, sid);
            } 
        }else{
            if(more.equals("1")){
                return schoolNoticeService.getClassroomNoticelistData(getRestTemplate());
            }else{
                return schoolNoticeService.getClassroomNoticelistDatamore(getRestTemplate(), more);
            } 
        }
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
    public String getcachekey(){
        return "Noticelist"+data+sid+more+System.currentTimeMillis();
    }
}
