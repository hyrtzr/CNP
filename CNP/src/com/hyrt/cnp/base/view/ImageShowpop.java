package com.hyrt.cnp.base.view;

import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.R;
import com.jingdong.common.frame.BaseActivity;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by GYH on 14-2-27.
 */
public class ImageShowpop extends BaseActivity{

    static final String PHOTO_TAP_TOAST_STRING = "Photo Tap! X: %.2f %% Y:%.2f %%";
    static final String SCALE_TOAST_STRING = "Scaled to: %.2ff";

    private TextView mCurrMatrixTv;

    private PhotoViewAttacher mAttacher;

    private Toast mCurrentToast;

    private Matrix mCurrentDisplayMatrix = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar.hide();
        setContentView(R.layout.activity_imageshowpop);
        final ImageView mImageView = (ImageView) findViewById(R.id.iv_photo);
        mCurrMatrixTv = (TextView) findViewById(R.id.tv_current_matrix);

        String bigImgPath=getIntent().getStringExtra("imageurl");
        showDetailImage1(bigImgPath, mImageView, false,true);
        // The MAGIC happens here!


    }

}
