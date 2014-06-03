package com.hyrt.cnp.dynamic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.utils.StringUtils;
import com.hyrt.cnp.R;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.oschina.app.AppContext;

import java.util.List;
import java.util.Map;

/**
 * Created by Zoe on 2014-04-22.
 */
public class ForwardListAdapter extends MySimpleAdapter{

    private List<Dynamic> datas;
    private BaseActivity baseActivity;

    public ForwardListAdapter(BaseActivity paramMyActivity, List data, int resourceId, String[] resKeys, int[] reses) {
        super(paramMyActivity, data, resourceId, resKeys, reses);
        this.baseActivity = paramMyActivity;
        this.datas = data;
    }

    @Override
    public View getView(int position, View paramView, ViewGroup paramViewGroup) {
        View view =  super.getView(position, paramView, paramViewGroup);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_forward_face);
        iv.setImageDrawable(null);
        ImageLoader.getInstance().displayImage(datas.get(position).getUserphoto(), iv, AppContext.getInstance().mNoCacheOnDiscImageloadoptions);

        TextView textView =(TextView)view.findViewById(R.id.tv_forward_content);
        TextView tcontext=(TextView)view.findViewById(R.id.tv_forward_content2);

        Dynamic mDynamic = datas.get(position);

        textView.setText(StringUtils.getSpannableString(mDynamic.getContent2(), baseActivity));
        tcontext.setText(StringUtils.getSpannableString(mDynamic.gettContent(), baseActivity));

        return view;
    }
}
