package com.hyrt.cnp.school.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyrt.cnp.R;

/**
 * Created by HY on 2014-04-04.
 */
public class SchoolCityListAdapter extends BaseAdapter{
    private Context context;
    private String[] array;
    private String position;

    public SchoolCityListAdapter(Context context, String[] array, String position) {
        this.context = context;
        this.array = array;
        this.position = position;
    }

    @Override
    public int getCount() {
        return array.length+2;
    }

    @Override
    public Object getItem(int i) {
        return array[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.school_city_list_item, null);
        }
        view.setBackgroundResource(android.R.color.white);
        TextView tv = (TextView) view.findViewById(R.id.tv_school_city_list_item);
        if(i == 0){
            tv.setText(Html.fromHtml(position+"    <font color='#767676'>GPS定位</font>"));
        }else if(i == 1){
            view.setBackgroundResource(android.R.color.darker_gray);
            tv.setText(context.getString(R.string.school_city_list));
        }else{
            tv.setText(array[i-2]);
        }
        return view;
    }
}
