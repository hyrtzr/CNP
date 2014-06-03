package com.hyrt.cnp.dynamic.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.utils.FileUtils;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.ui.AddPhotoDynamicActivity;
import com.hyrt.cnp.base.account.ui.AlbumBrowserActivity;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.oschina.app.AppContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoe on 2014-04-16.
 */
public class AddPhotoAdapter extends BaseAdapter{

    private ArrayList<String> datas;
    private Context context;
    private OnClickListener mListener;

    public AddPhotoAdapter(ArrayList<String> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size()+1;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.phone_album_item, null);
        }
        CheckBox cb = (CheckBox) view.findViewById(R.id.cb_phone_album_select);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_phone_album_img);
        iv.setImageDrawable(null);
        cb.setVisibility(View.GONE);

        if(i == datas.size()){
            ImageLoader.getInstance().cancelDisplayTask(iv);
            if(datas.size() < 9){
                iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                iv.setImageResource(R.drawable.ic_new_photo);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mListener != null){
                            mListener.onClick(0, i);
                        }
//                        Intent intent = new Intent();
//                        intent.setClass(context, AlbumBrowserActivity.class);
//                        intent.putStringArrayListExtra("checkeds", datas);
//                        ((BaseActivity)context).startActivityForResult(intent, AddPhotoDynamicActivity.RESULT_FOR_PHONE_ALBUM);
                    }
                });
            }

        }else{
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        mListener.onClick(1, i);
                    }
//                    datas.remove(datas.get(i));
//                    AddPhotoAdapter.this.notifyDataSetChanged();
                }
            });
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoader.getInstance().displayImage("file:///"+datas.get(i), iv, AppContext.getInstance().mImageloaderoptions);
        }

        return view;
    }

    public void setOnClickListener(OnClickListener listener){
        this.mListener = listener;
    }

    public static interface OnClickListener{
        /**
         *
         * @param type (0:添加照片; 1:删除照片)
         */
        public void onClick(int type, int position);
    }
}
