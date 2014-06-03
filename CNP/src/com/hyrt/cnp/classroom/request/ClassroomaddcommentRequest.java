package com.hyrt.cnp.classroom.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.CommentService;

/**
 * Created by GYH on 14-1-22.
 */
public class ClassroomaddcommentRequest extends BaseRequest{

    @Inject
    private CommentService schoolListService;
    private Comment comment;
    public ClassroomaddcommentRequest(Class clazz, Context context, Comment comment) {
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
