package com.hyrt.cnp.base.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import net.oschina.app.AppContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uk.co.senab.photoview.PhotoView;

/**
 * 图片滚动加载
 * Created by GYH on 14-1-8.
 */
public class ImageAdapter extends PagerAdapter {

    private ArrayList<ImageView> imageViews;
    private ArrayList<String> imageurls;
    private Context context;
    private boolean isfirst=true;
    private int curPosition = 0;
    private ImageAdapterCallback mCallback;
    private Map<String, Bitmap> images = new HashMap<String, Bitmap>();
    private String positionValue = "";

    public ImageAdapter(ArrayList<ImageView> imageViews,ArrayList<String> imageurls,Context context){
        this.imageViews=imageViews;
        this.imageurls=imageurls;
        this.context=context;
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if(images.get(imageurls.get(position)) != null
                && mCallback != null
                && !positionValue.equals(imageurls.get(position))){
            mCallback.onLoadingComplete(imageurls.get(position), images.get(imageurls.get(position)));
            positionValue = imageurls.get(position);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageLoader.getInstance().displayImage(
                imageurls.get(position),
                imageViews.get(position),
                AppContext.getInstance().mImageloaderoptions, imageLoadingListener);
//        baseActivity.showDetailImage1(imageurls.get(position), imageViews.get(position), false,isfirst);
//        isfirst=false;
        container.addView(imageViews.get(position), ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return imageViews.get(position);
    }

    ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {
        @Override
        public void onLoadingStarted(String s, View view) {

        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            images.put(s, bitmap);
            if(mCallback != null){
                mCallback.onLoadingComplete(s, bitmap);
            }
        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    };

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int arg1, Object arg2) {
        container.removeView((View) arg2);
        imageViews.get(arg1).setImageBitmap(null);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public void setCallback(ImageAdapterCallback callback){
        this.mCallback = callback;
    }

    public static interface ImageAdapterCallback{
        public void onLoadingComplete(String url, Bitmap bitmap);
    }
}
