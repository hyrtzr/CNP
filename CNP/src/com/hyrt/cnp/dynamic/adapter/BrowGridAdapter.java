package com.hyrt.cnp.dynamic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hyrt.cnp.base.account.utils.ScreenAdaptHelper;
import com.hyrt.cnp.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Zoe on 2014-04-09.
 */
public class BrowGridAdapter extends BaseAdapter{

    private Context mContext;
    private List<Object[]> datas;

    public BrowGridAdapter(Context mContext, List<Object[]> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size()+1;
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
//            AbsListView.LayoutParams params = new AbsListView.LayoutParams(
//                    ScreenAdaptHelper.getInstance(mContext).dipToPx(22),
//                    ScreenAdaptHelper.getInstance(mContext).dipToPx(22));
//            view = new ImageView(mContext);
//            view.setPadding(0, 0, 0, mContext.getResources().getDimensionPixelOffset(R.dimen.view_margin_small));
//            view.setLayoutParams(params);
            view = LayoutInflater.from(mContext).inflate(R.layout.brow_item, null);
        }
        ImageView brow = (ImageView) view.findViewById(R.id.iv_brow);
        if(i == 20){
            brow.setBackgroundResource(R.drawable.ic_del);
        }else{
            brow.setBackgroundResource((Integer) datas.get(i)[1]);
        }
        return view;
    }
}
