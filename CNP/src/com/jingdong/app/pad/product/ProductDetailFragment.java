package com.jingdong.app.pad.product;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyrt.cnp.R;
import com.jingdong.app.pad.product.drawable.HandlerRecycleBitmapDrawable;
import com.jingdong.app.pad.utils.InflateUtil;
import com.jingdong.common.frame.MyActivity;
import com.jingdong.common.frame.taskStack.TaskModule;
import com.jingdong.common.utils.Log;
import com.jingdong.common.utils.cache.GlobalImageCache;

import java.lang.ref.WeakReference;

public class ProductDetailFragment extends MyActivity {

    private static final String TAG = "ProductDetailFragment";
    private Bitmap imageBg;
    private WeakReference<ImageView> weakImageView;

    private void init() {
        getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.imageBg = BitmapFactory.decodeResource(getThisActivity().getResources(), R.drawable.ic_launcher);
    }

    protected View realCreateViewMethod(LayoutInflater paramLayoutInflater,
                                        ViewGroup paramViewGroup) {
        init();
        //BuildProjContent buildProjContent = (BuildProjContent) getArguments().getSerializable("BuildProjContent");
        View localView =  null;
                //inflate(R.layout.list_item);
        ImageView localImageView = (ImageView) localView.findViewById(android.R.id.icon1);
        weakImageView = new WeakReference<ImageView>(localImageView);
        HandlerRecycleBitmapDrawable localHandlerRecycleBitmapDrawable = new HandlerRecycleBitmapDrawable(null, this.getThisActivity());
        localHandlerRecycleBitmapDrawable.setBackGround(this.imageBg);
        localImageView.setImageDrawable(localHandlerRecycleBitmapDrawable);

        GlobalImageCache.BitmapDigest localBitmapDigest = null;
                //new GlobalImageCache.BitmapDigest(buildProjContent.getCRemark());
        localBitmapDigest.setWidth(localImageView.getWidth());
        localBitmapDigest.setHeight(localImageView.getHeight());
        Bitmap localBitmap = InflateUtil.loadImageWithCache(localBitmapDigest);
        if (localBitmap == null) {
            HandlerRecycleBitmapDrawable localHandlerRecycleBitmapDrawable2 = (HandlerRecycleBitmapDrawable) localImageView.getDrawable();
            localHandlerRecycleBitmapDrawable2.setBitmap(null);
            localHandlerRecycleBitmapDrawable.invalidateSelf();
            InflateUtil.loadImageWithUrl(getHttpGroupaAsynPool(), localBitmapDigest, new InflateUtil.ImageLoadListener() {
                public void onError(GlobalImageCache.BitmapDigest paramAnonymousBitmapDigest) {
                }

                public void onProgress(GlobalImageCache.BitmapDigest paramAnonymousBitmapDigest, int paramAnonymousInt1, int paramAnonymousInt2) {
                }

                public void onStart(GlobalImageCache.BitmapDigest paramAnonymousBitmapDigest) {
                }

                public void onSuccess(GlobalImageCache.BitmapDigest paramAnonymousBitmapDigest, Bitmap paramAnonymousBitmap) {
                    if (weakImageView != null) {
                        ImageView targetIv = weakImageView.get();
                        if (targetIv != null) {
                            HandlerRecycleBitmapDrawable localHandlerRecycleBitmapDrawable = (HandlerRecycleBitmapDrawable) targetIv.getDrawable();
                            localHandlerRecycleBitmapDrawable.setBitmap(paramAnonymousBitmap);
                            localHandlerRecycleBitmapDrawable.invalidateSelf();
                        }
                    }
                }
            });
        } else {
            localHandlerRecycleBitmapDrawable.setBitmap(localBitmap);
            localHandlerRecycleBitmapDrawable.invalidateSelf();
        }

        TextView textView = (TextView) localView.findViewById(android.R.id.text1);
        //textView.setText(buildProjContent.getCBprojcontent());
        return localView;
    }

    @Override
    public void onDestroy() {
        imageBg = null;
        weakImageView = null;
        super.onDestroy();
    }

    public static class ProductDetailFragmentTM extends TaskModule {

        ProductDetailFragment task;

        public ProductDetailFragmentTM() {
        }

        protected void doInit() {
            this.task = new ProductDetailFragment();
            this.task.setArguments(getBundle());
        }

        protected void doShow() {
            if (Log.D)
                Log.d(TAG, " doShow-->> ");
            replaceAndCommit(this.task);
        }
    }
}