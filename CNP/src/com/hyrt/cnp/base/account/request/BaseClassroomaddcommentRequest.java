package com.hyrt.cnp.base.account.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.service.CommentService;
import com.octo.android.robospice.request.SpiceRequest;

/**
 * Created by GYH on 14-1-22. change
 */
public class BaseClassroomaddcommentRequest extends BaseRequest{

    @Inject
    private CommentService schoolListService;
    private Comment comment;
    public BaseClassroomaddcommentRequest(Class clazz, Context context, Comment comment) {
        super(clazz, context);
        this.comment=comment;
    }
    @Override
    public Base run() {
        return schoolListService.addCommentData(comment);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "classroomphotoaddcomment"+comment.getInfoID();
    }
}
