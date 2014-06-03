package com.hyrt.cnp.dynamic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hyrt.cnp.R;

import java.util.List;

/**
 * Created by GYH on 2014/3/30.
 */
public class DynamicAddphotoAdapter extends BaseAdapter{


    private List<Integer> list;
    private Context context;

    public DynamicAddphotoAdapter(List<Integer> list,Context context){
        this.context=context;
        this.list=list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_dynamicaddphoto, null);
        }
        ImageView imgView = (ImageView) convertView.findViewById(R.id.gridview_image);
        imgView.setBackgroundResource(list.get(i));
        return convertView;
    }
}
