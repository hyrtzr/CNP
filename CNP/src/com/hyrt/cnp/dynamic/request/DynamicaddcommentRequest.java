package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.CommentService;

/**
 * Created by GYH on 14-1-22.
 */
public class DynamicaddcommentRequest extends BaseRequest{

    @Inject
    private CommentService schoolListService;
    private Comment comment;
    private int type;
    public DynamicaddcommentRequest(Class clazz, Context context, Comment comment, int type) {
        super(clazz, context);
        this.comment=comment;
        this.type = type;
    }



    @Override
    public Base run() {
        return schoolListService.adddynamicCommentData(comment, type);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "classroomphotoaddcomment"+comment.getInfoID()+System.currentTimeMillis();
    }
}
