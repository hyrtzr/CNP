package com.hyrt.cnp.dynamic.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.model.DynamicPhoto;
import com.hyrt.cnp.R;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.jingdong.common.frame.MyActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.oschina.app.AppContext;

import java.util.List;

/**
 * Created by HY on 2014-05-13.
 */
public class DynamicPhotoAdapter extends MySimpleAdapter{
    private List<DynamicPhoto> datas;

    public DynamicPhotoAdapter(BaseActivity paramMyActivity, List data, int resourceId, String[] resKeys, int[] reses) {
        super(paramMyActivity, data, resourceId, resKeys, reses);
        this.datas = data;
    }

    @Override
    public View getView(int position, View paramView, ViewGroup paramViewGroup) {
        View view =  super.getView(position, paramView, paramViewGroup);
        DynamicPhoto dp = datas.get(position);
        ImageView gridview_image = (ImageView) view.findViewById(R.id.gridview_image);
        ImageLoader.getInstance().displayImage(dp.getImagethpath(), gridview_image, AppContext.getInstance().mImageloaderoptions);
        return view;
    }
}
