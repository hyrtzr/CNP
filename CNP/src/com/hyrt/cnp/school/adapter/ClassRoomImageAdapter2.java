package com.hyrt.cnp.school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.ClassRoom;
import com.hyrt.cnp.R;

/**
 * Created by GYH on 14-1-8.
 */
public class ClassRoomImageAdapter2 extends BaseAdapter{

    private int[] imageid;
    private Context context;
    private ClassRoom.Model model;
    public ClassRoomImageAdapter2(ClassRoom.Model model, int[] imageid, Context context){
        this.model=model;
        this.imageid=imageid;
        this.context=context;
    }

    @Override
    public int getCount() {
        return model.getData().size();
    }

    @Override
    public Object getItem(int i) {
        return model.getData().get(i);
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
//            imgView.setImageResource(imageid[i]);
            textView.setText(model.getData().get(i).getRenname());
        return convertView;
    }
}
