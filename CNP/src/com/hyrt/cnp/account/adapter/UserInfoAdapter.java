package com.hyrt.cnp.account.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hyrt.cnp.R;

/**
 * Created by HY on 2014-04-21.
 */
public class UserInfoAdapter extends SimpleAdapter{
    private List<Map<String, String>> datas;
    private boolean mybabayinfo;

    public UserInfoAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to, boolean mybabayinfo) {
        super(context, data, resource, from, to);
        this.datas = (List<Map<String, String>>)data;
        this.mybabayinfo = mybabayinfo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if(position < 5){
            TextView title = (TextView) view.findViewById(R.id.title);
            if(mybabayinfo){
                title.setText(Html.fromHtml("<font color='red'>*</font>"+datas.get(position).get("title")));
            }else{
                title.setText(datas.get(position).get("title"));
            }

        }
        return view;
    }
}
