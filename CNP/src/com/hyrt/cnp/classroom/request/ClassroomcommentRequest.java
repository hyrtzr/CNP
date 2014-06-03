package com.hyrt.cnp.classroom.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.CommentService;

/**
 * Created by GYH on 14-1-22.
 */
public class ClassroomcommentRequest extends BaseRequest{

    @Inject
    private CommentService schoolListService;
    private String infoid;
    private String siteid;
    private String more = "1";

    public ClassroomcommentRequest(Class clazz, Context context, String infoid, String siteid) {
        super(clazz, context);
        this.infoid=infoid;
        this.siteid= siteid;
    }

    public ClassroomcommentRequest(Class clazz, Context context, String infoid, String siteid, String more) {
        super(clazz, context);
        this.infoid=infoid;
        this.siteid= siteid;
        this.more = more;
    }

    @Override
    public Base run() {
        if("1".equals(more)){
            return schoolListService.getCommentlistData(getRestTemplate(),infoid,siteid);
        }else{
            return schoolListService.getCommentlistData(getRestTemplate(),infoid,siteid, more);
        }

    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "classroomphoto"+siteid+infoid;
    }
}
