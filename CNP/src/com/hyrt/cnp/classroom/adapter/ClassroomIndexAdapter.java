package com.hyrt.cnp.classroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyrt.cnp.R;

/**
 * Created by GYH on 14-1-8.
 */
public class ClassroomIndexAdapter extends BaseAdapter{

    private String[] text;
    private int[] imageid;
    private int[] bg;
    private Context context;
    public ClassroomIndexAdapter(String[] text, int[] imageid, int[] bg, Context context){
        this.text=text;
        this.imageid=imageid;
        this.bg=bg;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_schoolindexlistitem, null);
        }
        RelativeLayout list_item = (RelativeLayout)convertView.findViewById(R.id.list_item);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.item_image);
        TextView textView = (TextView)convertView.findViewById(R.id.item_text);
        list_item.setBackgroundResource(bg[i]);
        imgView.setImageResource(imageid[i]);
        textView.setText(text[i]);
        return convertView;
    }
}
