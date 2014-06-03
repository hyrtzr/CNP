package com.hyrt.cnp.school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.model.StarBabay;
import com.hyrt.cnp.base.account.model.Teacher;
import com.hyrt.cnp.R;

/**
 * Created by GYH on 14-1-8.
 */
public class GridviewImageAdapter extends BaseAdapter{

    private String[] text;
    private int[] imageid;
    private Context context;
    private Base base;
    public GridviewImageAdapter(String[] text, int[] imageid, Context context){
        this.text=text;
        this.imageid=imageid;
        this.context=context;
    }

    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int i) {
        return text[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_gridview_image, null);
        }

        LinearLayout list_item = (LinearLayout)convertView.findViewById(R.id.gridview_item);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.gridview_image);
        TextView textView = (TextView)convertView.findViewById(R.id.gridview_name);

        if(base instanceof Teacher.Model){
            Teacher.Model model = (Teacher.Model)base;
            imgView.setImageResource(imageid[i]);
            textView.setText(model.getData().get(i).getRenname());
        }else if(base instanceof StarBabay.Model){

        }
        return convertView;
    }
}
