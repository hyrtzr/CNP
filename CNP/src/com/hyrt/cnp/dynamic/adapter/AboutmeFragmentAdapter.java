package com.hyrt.cnp.dynamic.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.ui.HomeInteractiveActivity;

/**
 * Created by HY on 2014-05-15.
 */
public class AboutmeFragmentAdapter extends BaseAdapter{
    private Context context;
    private List<Map<String, Object>> contents;

    public AboutmeFragmentAdapter(Context context, List<Map<String, Object>> contents) {
        this.context = context;
        this.contents = contents;
    }

    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public Object getItem(int i) {
        return contents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.layout_aboutme_item, null);
        }
        ImageView item_image = (ImageView) view.findViewById(R.id.item_image);
        TextView item_text = (TextView) view.findViewById(R.id.item_text);
        TextView text_new_num = (TextView) view.findViewById(R.id.text_new_num);

        Map<String, Object> map = contents.get(i);

        item_image.setImageResource((Integer) map.get("imageid"));
        item_text.setText(map.get("TITLE")+"");
        int count = 0;

        if(i == 0){
            count = ((HomeInteractiveActivity)context).aboutmeCount;
        }
        if(i == 1){
            count = ((HomeInteractiveActivity)context).commentCount;
        }
        if(i == 2){
            count = ((HomeInteractiveActivity)context).forwardCount;
        }

        if(count > 0){
            text_new_num.setText(count+"");
            text_new_num.setVisibility(View.VISIBLE);
        }else{
            text_new_num.setVisibility(View.GONE);
        }

        return view;
    }
}
