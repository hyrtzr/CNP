package com.hyrt.cnp.dynamic.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.BabyInfo;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.utils.Log;
import com.hyrt.cnp.base.account.utils.LogHelper;
import com.hyrt.cnp.base.account.utils.StringUtils;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.ui.BabayIndexActivity;
import com.hyrt.cnp.dynamic.ui.CommentListActivity;
import com.hyrt.cnp.dynamic.ui.DynamicCommentActivity;
import com.hyrt.cnp.dynamic.ui.SendDynamicActivity;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.oschina.app.AppContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GYH on 14-1-23.
 */
public class DynamicAdapter extends BaseAdapter {

    private BaseActivity activity;
    private ArrayList<Dynamic> list;
    private DynamicAdapterCallback mCallback;
    
    public DynamicAdapter(BaseActivity context, ArrayList<Dynamic> data) {
    	this.list=(ArrayList<Dynamic>)data;
        this.activity=context;
    }


//    @Override
//    public View getView(final int position, View paramView, ViewGroup paramViewGroup) {

//    }

    public void setCallback(DynamicAdapterCallback callback){
        this.mCallback = callback;
    }

    public static interface DynamicAdapterCallback{
        /**
         *点击头像
         * @param position 动态下标
         */
        public void onFaceClick(int position);

        /**
         * 点击图片
         * @param position 动态下标
         * @param PhotoPosition 图片下标
         */
        public void onPhotoClick(int position, int PhotoPosition);

        public void onAboutClick(int position);
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder mHolder = null;
		if(view == null){
			view = LayoutInflater.from(activity).inflate(R.layout.layout_item_dynamic, null);
	    	mHolder = new ViewHolder();
	    	mHolder.textView = (TextView) view.findViewById(R.id.dynamic_context);
	    	mHolder.dynamic_Avatar = (ImageView) view.findViewById(R.id.dynamic_Avatar);
	    	mHolder.tcontext = (TextView)view.findViewById(R.id.dynamic_dcontext);
	    	mHolder.gv_photos = (GridView) view.findViewById(R.id.gv_photos);
	    	mHolder.tv_dynamic_about = (TextView) view.findViewById(R.id.tv_dynamic_about);
	    	mHolder.layout_forward_content = (LinearLayout) view.findViewById(R.id.layout_forward_content);
	    	mHolder.dynamic_name = (TextView) view.findViewById(R.id.dynamic_name);
	    	mHolder.dynamic_type = (TextView) view.findViewById(R.id.dynamic_type);
	    	mHolder.dynamic_time = (TextView) view.findViewById(R.id.dynamic_time);
	    	mHolder.dynamic_time2 = (TextView) view.findViewById(R.id.dynamic_time2);
			mHolder.dynamic_zf_num = (TextView) view.findViewById(R.id.dynamic_zf_num);
			mHolder.dynamic_pl_num = (TextView) view.findViewById(R.id.dynamic_pl_num);
			view.setTag(mHolder);
      }else{
    	  mHolder = (ViewHolder) view.getTag();
      }
      final Dynamic mDynamic = list.get(position);
      
      mHolder.dynamic_name.setText(mDynamic.getUserName());
      mHolder.dynamic_type.setText(mDynamic.getType());
      LogHelper.i("tag", "posttime3:"+mDynamic.getPosttime3());
      mHolder.dynamic_time.setVisibility(View.VISIBLE);
      mHolder.dynamic_time.setText(mDynamic.getPosttime3());
      mHolder.dynamic_time2.setText(mDynamic.getPosttime2());
      mHolder.dynamic_zf_num.setText(mDynamic.getTransmit2());
      mHolder.dynamic_pl_num.setText(mDynamic.getReview2());
      
      if(mDynamic.getIsTran() == 2){
    	  mHolder.tv_dynamic_about.setText("@"+mDynamic.gettUserName()+":");
    	  mHolder.tv_dynamic_about.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  /*if(AppContext.getInstance().uuid == mDynamic.gettUserId()){
                      activity.showTitle(2);
                      activity.homeViewpager.setCurrentItem(2);
                  }else{
                      BabyInfo mBabayInfo = new BabyInfo();
                      mBabayInfo.setUser_id(mDynamic.gettUserId());
                      Intent intent = new Intent();
                      intent.setClass(activity, BabayIndexActivity.class);
                      intent.putExtra("vo",mBabayInfo);
                      activity.startActivity(intent);
                  }*/
                  if (mCallback != null) {
                      mCallback.onAboutClick(position);
                  }

              }
          });

      }else{
    	  mHolder.tv_dynamic_about.setText("");
      }
      LinearLayout.LayoutParams gvPhotoParams = (LinearLayout.LayoutParams) mHolder.gv_photos.getLayoutParams();
      gvPhotoParams.width =
              (activity.getResources().getDimensionPixelOffset(R.dimen.imageview_small)
                      + activity.getResources().getDimensionPixelOffset(R.dimen.view_margin_smallest))*3;
      if(mDynamic.getbPicAry2().size() < 4){
          gvPhotoParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
      }else{
          gvPhotoParams.height = (activity.getResources().getDimensionPixelOffset(R.dimen.imageview_small)
                  + activity.getResources().getDimensionPixelOffset(R.dimen.view_margin_smallest))*2;
      }

      mHolder.gv_photos.setAdapter(new BaseAdapter() {
          @Override
          public int getCount() {
              return mDynamic.getbPicAry2().size();
          }


          @Override
          public Object getItem(int i) {
              return mDynamic.getbPicAry2().get(i);
          }

          @Override
          public long getItemId(int i) {
              return i;
          }

          @Override
          public View getView(int i, View view, ViewGroup viewGroup) {
              if(view == null){
                  view = LayoutInflater.from(activity).inflate(R.layout.layout_small_img_item, null);
              }
              ImageView iv = (ImageView) view.findViewById(R.id.iv_phone_album_img);
              iv.setImageDrawable(null);

              ImageLoader.getInstance().displayImage(
                      mDynamic.getbPicAry2().get(i),
                      iv,
                      AppContext.getInstance().mImageloaderoptions);
              return view;
          }
      });

      mHolder.gv_photos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              if(mCallback != null){
                  mCallback.onPhotoClick(position, i);
              }
          }
      });

      String facePath = mDynamic.getUserphoto();
      mHolder.dynamic_Avatar.setTag(facePath);
      mHolder.dynamic_Avatar.setImageResource(R.drawable.cnp_default_img);
//      ImageLoader.getInstance().cancelDisplayTask(dynamic_Avatar);
      ImageLoader.getInstance().displayImage(
              facePath,
              mHolder.dynamic_Avatar,
              AppContext.getInstance().mNoCacheOnDiscImageloadoptions);

      mHolder.dynamic_Avatar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(mCallback != null){
                  mCallback.onFaceClick(position);
              }
          }
      });

      if(list.get(position).getContent2().trim().equals("")){
    	  mHolder.textView.setText(list.get(position).getTitle());
      }else{
    	  mHolder.textView.setText(StringUtils.getSpannableString(mDynamic.getContent2(), activity));
      }

      mHolder.tcontext.setText(StringUtils.getSpannableString(mDynamic.gettContent(), activity));

      final int posi=position;

      if(list.get(position).getContent().trim().equals("") && list.get(position).getTitle().trim().equals("")){
    	  mHolder.textView.setVisibility(View.GONE);
      }else{
    	  mHolder.textView.setVisibility(View.VISIBLE);
      }

      
      if(list.get(position).gettContent()==null || list.get(position).gettContent().length()<=0){
    	  mHolder.layout_forward_content.setVisibility(View.GONE);
      }else{
    	  mHolder.layout_forward_content.setVisibility(View.VISIBLE);
      }

      LinearLayout dynamic_zf=(LinearLayout)view.findViewById(R.id.dynamic_zf);
      dynamic_zf.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent();
              intent.setClass(activity, SendDynamicActivity.class);
              intent.putExtra("dynamic", list.get(posi));
              intent.putExtra("type", SendDynamicActivity.TYPE_FORWARD);
              activity.startActivityForResult(intent,0);
          }
      });
      LinearLayout dynamic_pl=(LinearLayout)view.findViewById(R.id.dynamic_pl);
      dynamic_pl.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent();
              intent.setClass(activity, CommentListActivity.class);
              intent.putExtra("vo", list.get(posi));
              intent.putExtra("Category","pl");
              activity.startActivityForResult(intent,0);
          }
      });
      return view;
	}
	
	private class ViewHolder{
		TextView textView;
		ImageView dynamic_Avatar;
		TextView tcontext;
		GridView gv_photos;
		TextView tv_dynamic_about;
		LinearLayout layout_forward_content;
		TextView dynamic_name;
		TextView dynamic_type;
		TextView dynamic_time;
		TextView dynamic_time2;
		TextView dynamic_zf_num;
		TextView dynamic_pl_num;
	}
}
