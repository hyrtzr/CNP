package com.hyrt.cnp.school.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Notice;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.request.NoticeAlterRequest;
import com.hyrt.cnp.school.requestListener.NoticeAlterRequestListener;
import com.hyrt.cnp.school.ui.SchoolNoticeActivity;
import com.hyrt.cnp.school.ui.SendNoticeActivity;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

import net.oschina.app.AppContext;

import java.util.ArrayList;

/**
 * Created by GYH on 14-1-9.
 */
public class SchoolNoticeAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Notice> notices;
    private String type;

    public SchoolNoticeAdapter(Context context,ArrayList<Notice> notices, String type){
        this.context=context;
        this.notices=notices;
        this.type = type;
    }

    @Override
    public int getCount() {
        return notices.size()-1;
    }

    @Override
    public Object getItem(int i) {
        return notices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_text, null);
            holder.title = (TextView) convertView.findViewById(R.id.item_title);
            holder.text = (TextView) convertView.findViewById(R.id.item_time);
            convertView.setTag(holder);//绑定ViewHolder对象
//            LinearLayout layoutAlter = (LinearLayout) convertView.findViewById(R.id.layout_alter);
//            if("classroom".equals(type)
//                    && AppContext.getInstance().mUserDetail != null
//                    && AppContext.getInstance().mUserDetail.getGroupID() != 7){
//                layoutAlter.setVisibility(View.VISIBLE);
//            }
            TextView tv_change = (TextView) convertView.findViewById(R.id.tv_change);
            tv_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(context, SendNoticeActivity.class);
                    intent.putExtra("notice", notices.get(i+1));
                    intent.putExtra("type", 1);
                    ((BaseActivity)context).startActivityForResult(intent, SchoolNoticeActivity.RESULT_FROM_SEND_NOTICE);
                }
            });
            TextView tv_del = (TextView) convertView.findViewById(R.id.tv_del);
            tv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NoticeAlterRequestListener requestListener = new NoticeAlterRequestListener((BaseActivity)context);
                    requestListener.setListener(((SchoolNoticeActivity)context).mNoticeDelRequestListener);
                    NoticeAlterRequest request = new NoticeAlterRequest(
                            context, notices.get(i+1).getAnnource_id());
                    ((BaseActivity)context).spiceManager.execute(
                            request, request.getcachekey(),
                            DurationInMillis.ONE_SECOND * 10,
                            requestListener.start(2));
                }
            });
        }else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }

        holder.title.setText(notices.get(i+1).getTitle());
        holder.text.setText(notices.get(i+1).getPosttime2());
        return convertView;
    }

    /**存放控件*/
    public final class ViewHolder{
        public TextView title;
        public TextView text;
    }

}
