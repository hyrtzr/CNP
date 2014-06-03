package com.hyrt.cnp.dynamic.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.model.ItInfo;
import com.hyrt.cnp.base.account.utils.StringUtils;
import com.hyrt.cnp.R;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GYH on 2014/4/6.
 */
public class MyItInfoAdapter extends MySimpleAdapter {

    private BaseActivity activity;
    private ArrayList<ItInfo> list;
    public MyItInfoAdapter(BaseActivity context, List data, int layoutId, String[] resKeys, int[] reses) {
        super(context, data, layoutId, resKeys, reses);
        this.list=(ArrayList<ItInfo>)data;
        this.activity=context;
    }


    @Override
    public View getView(final int position, View paramView, ViewGroup paramViewGroup) {
        View view= super.getView(position, paramView, paramViewGroup);
        LinearLayout item_bottom_list = (LinearLayout) view.findViewById(R.id.item_bottom_list);
        item_bottom_list.setVisibility(View.GONE);
        TextView textView =(TextView)view.findViewById(R.id.dynamic_context);
        ImageView imageView1=(ImageView)view.findViewById(R.id.dynamic_image1);
        ImageView imageView2=(ImageView)view.findViewById(R.id.dynamic_image2);
        ImageView imageView3=(ImageView)view.findViewById(R.id.dynamic_image3);
        TextView tcontext=(TextView)view.findViewById(R.id.dynamic_dcontext);
        imageView1.setVisibility(View.GONE);
        imageView2.setVisibility(View.GONE);
        imageView3.setVisibility(View.GONE);
        LinearLayout dynamic_zf=(LinearLayout)view.findViewById(R.id.dynamic_zf);
        ItInfo mItInfo = list.get(position);
        textView.setText(StringUtils.getSpannableString(mItInfo.getMsgData(), activity));
        tcontext.setVisibility(View.GONE);
        dynamic_zf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Dynamic mDynamic = new Dynamic();
//                mDynamic.set_id(mItInfo.get);
//                Intent intent = new Intent();
//                intent.setClass(activity, DynamicCommentActivity.class);
//                intent.putExtra("vo", list.get(posi));
//                intent.putExtra("Category","zf");
//                activity.startActivityForResult(intent,0);
            }
        });
        LinearLayout dynamic_pl=(LinearLayout)view.findViewById(R.id.dynamic_pl);
        dynamic_pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(activity, CommentListActivity.class);
//                intent.putExtra("vo", list.get(posi));
//                intent.putExtra("Category","pl");
//                activity.startActivityForResult(intent,0);
            }
        });
        return view;
    }
}
