package com.hyrt.cnp.school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.SchoolSearch;
import com.hyrt.cnp.base.account.utils.StringUtils;
import com.hyrt.cnp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.oschina.app.AppContext;

import java.util.List;

/**
 * Created by Zoe on 2014-04-01.
 */
public class SchoolSearchResultAdapter extends BaseAdapter{

    private Context mContext;
    private List<SchoolSearch> datas;

    public SchoolSearchResultAdapter(Context context, List<SchoolSearch> data) {
        this.mContext = context;
        this.datas = data;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder mViewHolder;
        if(convertView == null){
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.search_result_item, null);
            mViewHolder.pic = (ImageView) convertView.findViewById(R.id.iv_seach_result_item_pic);
            mViewHolder.title = (TextView) convertView.findViewById(R.id.tv_search_item_title);
            mViewHolder.range = (TextView) convertView.findViewById(R.id.tv_search_item_range);
            mViewHolder.property = (TextView) convertView.findViewById(R.id.tv_search_item_property);
            mViewHolder.staffNum = (TextView) convertView.findViewById(R.id.tv_search_item_staff_num);
            mViewHolder.foundTime = (TextView) convertView.findViewById(R.id.tv_search_item_found_time);
            mViewHolder.address = (TextView) convertView.findViewById(R.id.tv_search_item_address);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        SchoolSearch data = datas.get(i);
        ImageLoader.getInstance().displayImage(data.getSchoolImagePath(), mViewHolder.pic, AppContext.getInstance().mImageloaderoptions);
        mViewHolder.title.setText(data.getnName()+"");
        if(data.getProperty() == 1){
            mViewHolder.property.setText("公办");
        }else{
            mViewHolder.property.setText("民办");
        }
        mViewHolder.staffNum.setText(data.getStaffNum() + "人");
        String[] createTimes = data.getnCreate().split("-");
        mViewHolder.foundTime.setText(createTimes[0]+"年"+createTimes[1]+"月"+createTimes[2]+"号");
        mViewHolder.address.setText(data.getAddress()+"");

        return convertView;
    }

    public final class ViewHolder{
        public ImageView pic;
        public TextView title;
        public TextView range;
        public TextView property;
        public TextView staffNum;
        public TextView foundTime;
        public TextView address;


    }
}
