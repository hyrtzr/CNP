package com.hyrt.cnp.dynamic.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.ItInfo;
import com.hyrt.cnp.base.account.utils.StringUtils;
import com.hyrt.cnp.R;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.oschina.app.AppContext;

import java.util.List;

/**
 * Created by Zoe on 2014-04-15.
 */
public class MyForwardListAdapter extends MySimpleAdapter{

    private List<ItInfo> mData;
    private BaseActivity activity;

    public MyForwardListAdapter(BaseActivity paramMyActivity, List data, int resourceId, String[] resKeys, int[] reses) {
        super(paramMyActivity, data, resourceId, resKeys, reses);
        this.mData = data;
        this.activity = paramMyActivity;
    }

    @Override
    public View getView(int position, View paramView, ViewGroup paramViewGroup) {
        View view = super.getView(position, paramView, paramViewGroup);
        ItInfo mItInfo = mData.get(position);
        TextView tv_comment_content = (TextView) view.findViewById(R.id.tv_forward_content);
        TextView tv_comment_content2 = (TextView) view.findViewById(R.id.tv_forward_content2);

        tv_comment_content.setText(StringUtils.getSpannableString(mItInfo.getdContent(), activity));
        tv_comment_content2.setText(StringUtils.getSpannableString(mItInfo.gettContent(), activity));

        ImageView iv = (ImageView) view.findViewById(R.id.iv_forward_face);
        iv.setImageDrawable(null);
        ImageLoader.getInstance().displayImage(mData.get(position).getUserphoto(), iv, AppContext.getInstance().mNoCacheOnDiscImageloadoptions);
        return view;
    }
}
