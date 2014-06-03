package com.hyrt.cnp.base.account.adapter;

import java.util.ArrayList;
import java.util.List;

import net.oschina.app.AppContext;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.utils.AlertUtils;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Zoe on 2014-04-16.
 */
public class PhoneAlbumAdapter extends BaseAdapter{

    private List<String> datas;
    private Context mContext;
    public ArrayList<String> checkeds = new ArrayList<String>();
    private int type = 0;

    public PhoneAlbumAdapter(List<String> datas, List<String> checkeds, int type, Context context) {
        this.checkeds.clear();
        this.checkeds.addAll(checkeds);
        this.datas = datas;
        this.mContext = context;
        this.type = type;
    }

    @Override
    public int getCount() {
        return datas.size();
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
        final String data = datas.get(i);
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.phone_album_item, null);
        }
        ImageView iv = (ImageView) view.findViewById(R.id.iv_phone_album_img);
        final CheckBox cb = (CheckBox) view.findViewById(R.id.cb_phone_album_select);
        if(type == 1){
            cb.setVisibility(View.GONE);
        }else{
            cb.setVisibility(View.VISIBLE);
        }
        if(checkeds.contains(data)){
            cb.setChecked(true);
        }else{
            cb.setChecked(false);
        }
        android.util.Log.i("tag", "data："+data);
        ImageLoader.getInstance().displayImage("file:///"+data, iv, AppContext.getInstance().mImageloaderoptions);

        View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type == 1){
                    checkeds.add(data);
                    Intent data = new Intent();
                    data.putStringArrayListExtra("checkeds", checkeds);
                    ((BaseActivity)mContext).setResult(AppContext.getInstance().RESULT_FOR_PHONE_ALBUM, data);
                    ((BaseActivity)mContext).finish();
                }else{
                    if(checkeds.contains(data)){
                        checkeds.remove(data);
                        cb.setChecked(false);
                    }else{
                        if(checkeds.size() < 9){
                            checkeds.add(data);
                            cb.setChecked(true);
                        }else{
                            cb.setChecked(false);
                            AlertUtils.getInstance().showCenterToast(mContext, "最多上传9张");
//                            Toast.makeText(mContext, "最多上传9张", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        };

        iv.setOnClickListener(mOnClickListener);
        cb.setOnClickListener(mOnClickListener);

        return view;
    }
}
