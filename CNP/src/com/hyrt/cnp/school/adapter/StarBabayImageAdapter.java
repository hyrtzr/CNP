package com.hyrt.cnp.school.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hyrt.cnp.base.account.model.StarBabay;
import com.hyrt.cnp.R;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.oschina.app.AppContext;

import java.util.List;

/**
 * Created by GYH on 14-1-8.
 */
public class StarBabayImageAdapter extends MySimpleAdapter {


    private List<StarBabay> datas;

    public StarBabayImageAdapter(BaseActivity context, List data, int layoutId, String[] resKeys, int[] reses) {
        super(context, data, layoutId, resKeys, reses);
        this.datas = data;
    }

    @Override
    public View getView(int position, View paramView, ViewGroup paramViewGroup) {
        View view = super.getView(position, paramView, paramViewGroup);
        ImageView iv = (ImageView) view.findViewById(R.id.gridview_image);
        ImageLoader.getInstance().displayImage(
                datas.get(position).getImagepath(),
                iv, AppContext.getInstance().mNoCacheOnDiscImageloadoptions);
        return view;
    }
}
