package com.hyrt.cnp.dynamic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.ui.SendDynamicActivity;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.oschina.app.AppContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GYH on 14-1-8.
 */
public class DynamicPhotoInfoAdapter extends BaseAdapter {

    private List<Comment> comments;
    private Context context;

    public DynamicPhotoInfoAdapter(Context context, List<Comment> comments) {
        this.comments = comments;
        this.context = context;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.layout_item_comment_classroom, null);
        }
        ImageView comment_photo = (ImageView) view.findViewById(R.id.comment_photo);
        TextView text_name = (TextView) view.findViewById(R.id.text_name);
        TextView text_time = (TextView) view.findViewById(R.id.text_time);
        TextView text_con = (TextView) view.findViewById(R.id.text_con);
        LinearLayout layout_comment_btn = (LinearLayout) view.findViewById(R.id.layout_comment_btn);

        final Comment comment = comments.get(i);
        ImageLoader.getInstance().displayImage(
                comment.getphotoImage(), comment_photo,
                AppContext.getInstance().mNoCacheOnDiscImageloadoptions);
        text_name.setText(comment.getUsername());
        text_time.setText(comment.getCreatdate2());
        text_con.setText(comment.getContent());

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
