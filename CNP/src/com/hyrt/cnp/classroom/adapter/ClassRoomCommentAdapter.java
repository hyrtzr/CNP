package com.hyrt.cnp.classroom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.ui.SendDynamicActivity;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.jingdong.common.frame.MyActivity;

import net.oschina.app.AppContext;

import java.util.List;
import java.util.Map;

/**
 * Created by HY on 2014-05-28.
 */
public class ClassRoomCommentAdapter extends MySimpleAdapter{

    private List<Comment> comments;
    private Context context;

    public ClassRoomCommentAdapter(BaseActivity context, List data, int resourceId, String[] resKeys, int[] reses) {
        super(context, data, resourceId, resKeys, reses);
        this.comments = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View paramView, ViewGroup paramViewGroup) {
        View view = super.getView(position, paramView, paramViewGroup);
        final Comment comment = comments.get(position);
        LinearLayout layout_comment_btn = (LinearLayout) view.findViewById(R.id.layout_comment_btn);
        if(comment.getUserid() == AppContext.getInstance().mUserDetail.getUser_id()){
            layout_comment_btn.setVisibility(View.GONE);
            layout_comment_btn.setOnClickListener(null);
        }else{
            layout_comment_btn.setVisibility(View.VISIBLE);
            layout_comment_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Comment mComment = comment;
                    mComment.setInfoUserId(comment.getUserid()+"");
                    Intent intent = new Intent();
                    intent.setClass(context, SendDynamicActivity.class);
                    intent.putExtra("comment", mComment);
                    intent.putExtra("type", 2);
                    context.startActivity(intent);
                }
            });
        }
        return view;
    }
}
