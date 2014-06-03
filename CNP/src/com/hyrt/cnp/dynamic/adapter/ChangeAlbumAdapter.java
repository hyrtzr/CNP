package com.hyrt.cnp.dynamic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.R;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.oschina.app.AppContext;

import java.util.List;

/**
 * Created by HY on 2014-04-11.
 */
public class ChangeAlbumAdapter extends BaseAdapter{

    List<Album> datas;
    private Context context;

    public ChangeAlbumAdapter(List<Album> datas, Context context) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.change_ablum_item, null);
        }

        ImageView iv = (ImageView) view.findViewById(R.id.iv_photo);
        TextView tv = (TextView) view.findViewById(R.id.tv_ablum_text);
//        iv.setBackgroundResource(R.drawable.cnp_spinner_inner);
        android.util.Log.i("tag", "position:"+i);
        if(i == 0){
            ImageLoader.getInstance().cancelDisplayTask(iv);
//            iv.setBackgroundResource(R.drawable.ic_new_photo);
            iv.setImageResource(R.drawable.ic_new_photo);
            tv.setText("新建相册");
        }else{
            Album data = datas.get(i-1);
            ImageLoader.getInstance().displayImage(data.getImagepath(), iv, AppContext.getInstance().mImageloaderoptions);
            tv.setText(data.getAlbumName());
        }
        return view;
    }
}
