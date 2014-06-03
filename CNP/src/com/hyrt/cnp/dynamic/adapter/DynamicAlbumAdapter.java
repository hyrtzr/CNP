package com.hyrt.cnp.dynamic.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.R;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;

import java.util.List;

/**
 * Created by HY on 2014-04-11.
 */
public class DynamicAlbumAdapter extends MySimpleAdapter{

    private List<Album> datas;

    public DynamicAlbumAdapter(BaseActivity paramMyActivity, List data, int resourceId, String[] resKeys, int[] reses) {
        super(paramMyActivity, data, resourceId, resKeys, reses);
    }

    @Override
    public View getView(int position, View paramView, ViewGroup paramViewGroup) {
        View view = super.getView(position, paramView, paramViewGroup);
        TextView change = (TextView) view.findViewById(R.id.btn_change_album);
        TextView del = (TextView) view.findViewById(R.id.btn_change_album);

        Album album = datas.get(position);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
