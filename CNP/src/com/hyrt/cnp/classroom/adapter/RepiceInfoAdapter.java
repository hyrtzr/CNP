package com.hyrt.cnp.classroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyrt.cnp.R;

import java.util.ArrayList;

/**
 * Created by GYH on 14-1-12.
 */
public class RepiceInfoAdapter extends BaseAdapter {

    private Context context;
    ArrayList<String> foot;
    String[] str;
    public RepiceInfoAdapter(Context context, String[] str, ArrayList<String> foot){
        this.context=context;
        this.foot=foot;
        this.str=str;
    }

    @Override
    public int getCount() {
        return foot.size();
    }

    @Override
    public Object getItem(int i) {
        return foot.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_repice1, null);
            holder.foottime = (TextView) convertView.findViewById(R.id.item_foottime);
            holder.footcont = (TextView) convertView.findViewById(R.id.item_footcont);
            convertView.setTag(holder);//绑定ViewHolder对象
        }else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }

        //TODO 修改数组越界
        if(str.length!=0){
            holder.foottime.setText(str[i]);
            holder.footcont.setText(foot.get(i));
        }
        return convertView;
    }

    /**存放控件*/
    public final class ViewHolder{
        public TextView foottime;
        public TextView footcont;
    }
}
