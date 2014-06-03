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
public class NotNeedLoginSchoolNoticeListRequest extends NotNeedLoginBaseRequest {

   private String data;
   private String more;
   private int sid = -1;

   @Inject
   private SchoolNoticeService schoolNoticeService;
   public NotNeedLoginSchoolNoticeListRequest(Class clazz, Context context, String data, String more) {
       super(clazz, context);
       this.data=data;
       this.more=more;
   }

    public NotNeedLoginSchoolNoticeListRequest(Class clazz, Context context, String data, String more, int sid) {
        super(clazz, context);
        this.data=data;
        this.more=more;
        this.sid = sid;
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
           return null;
       }
   }

   @Override
   public int compareTo(Object o) {
       return 0;
   }
   public String getcachekey(){
       return "Noticelist"+data+sid+more;
   }
}
