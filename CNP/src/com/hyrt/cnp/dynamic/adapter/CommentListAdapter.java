package com.hyrt.cnp.dynamic.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.model.ItInfo;
import com.hyrt.cnp.base.account.utils.StringUtils;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.ui.CommentListActivity;
import com.hyrt.cnp.dynamic.ui.SendDynamicActivity;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.jingdong.common.frame.MyActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.oschina.app.AppContext;

import java.util.List;

/**
 * Created by Zoe on 2014-04-08.
 */
public class CommentListAdapter extends MySimpleAdapter{

    private BaseActivity mActivity;
    private Dynamic dynamic;
    private List<Comment> datas;

    public CommentListAdapter(
            BaseActivity paramMyActivity, List data,
            int resourceId, String[] resKeys, int[] reses,
            Dynamic dynamic, List<Comment> datas) {
        super(paramMyActivity, data, resourceId, resKeys, reses);
        this.mActivity = paramMyActivity;
        this.dynamic = dynamic;
        this.datas = datas;
    }

    @Override
    public View getView(final int position, View paramView, ViewGroup paramViewGroup) {
        View view = super.getView(position, paramView, paramViewGroup);
        TextView btn_comment = (TextView) view.findViewById(R.id.btn_comment);

        TextView comment_context = (TextView) view.findViewById(R.id.comment_context);
        comment_context.setText(StringUtils.getSpannableString(datas.get(position).getContent(), mActivity));
        ImageView comment_photo = (ImageView) view.findViewById(R.id.comment_photo);
        comment_photo.setImageDrawable(null);
        ImageLoader.getInstance().cancelDisplayTask(comment_photo);
        ImageLoader.getInstance().displayImage(
                datas.get(position).getphotoImage(),
                comment_photo, AppContext.getInstance().mNoCacheOnDiscImageloadoptions);

        if(datas.get(position).getUserid() == AppContext.getInstance().mUserDetail.getUser_id()){
            btn_comment.setVisibility(View.GONE);
        }else{
            btn_comment.setVisibility(View.VISIBLE);
            btn_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    android.util.Log.i("tag", "position:"+position+" con:"+datas.get(position).getContent());
                    Intent intent = new Intent();
                    intent.setClass(mActivity, SendDynamicActivity.class);
                    Comment data = datas.get(position);
                    intent.putExtra("comment",data);
                    intent.putExtra("type", SendDynamicActivity.TYPE_COMMENT);
                    mActivity.startActivityForResult(intent, 0);
                }
            });
        }

        return view;
    }
}
