package com.hyrt.cnp.dynamic.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.R;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.oschina.app.AppContext;

import java.util.List;

/**
 * Created by Zoe on 2014-04-11.
 */
public class MyAblumAdapter extends MySimpleAdapter{

    private OnItemClickListener mListener;

    private List<Album> datas;

    public MyAblumAdapter(BaseActivity paramMyActivity, List data, int resourceId, String[] resKeys, int[] reses) {
        super(paramMyActivity, data, resourceId, resKeys, reses);
        this.datas = data;
    }

    @Override
    public View getView(final int position, View paramView, ViewGroup paramViewGroup) {
        View view = super.getView(position, paramView, paramViewGroup);
        TextView btn_change_album = (TextView) view.findViewById(R.id.btn_change_album);
        TextView btn_del_album = (TextView) view.findViewById(R.id.btn_del_album);
        ImageView item_album_image = (ImageView) view.findViewById(R.id.item_album_image);

        item_album_image.setImageDrawable(null);
        ImageLoader.getInstance().displayImage(
                datas.get(position).getImagepath2(),
                item_album_image
                , AppContext.getInstance().mImageloaderoptions);

        View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.btn_change_album){
                    if(mListener != null){
                        mListener.onClick(0, position);
                    }
                }else if(view.getId() == R.id.btn_del_album){
                    if(mListener != null){
                        mListener.onClick(1, position);
                    }
                }else{
                    if(mListener != null){
                        mListener.onClick(2, position);
                    }
                }
            }
        };
        btn_change_album.setOnClickListener(mOnClickListener);
        btn_del_album.setOnClickListener(mOnClickListener);
        item_album_image.setOnClickListener(mOnClickListener);
        view.setOnClickListener(mOnClickListener);
        return view;
    }

    public void setListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public static interface OnItemClickListener{
        /**
         * 0:修改 1:删除 2:跳转
         * @param type
         * @param position
         */
        public void onClick(int type, int position);
    }
}
