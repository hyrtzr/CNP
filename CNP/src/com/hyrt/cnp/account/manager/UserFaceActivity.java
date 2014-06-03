package com.hyrt.cnp.account.manager;

import java.io.ByteArrayOutputStream;
import java.io.File;

import net.oschina.app.AppContext;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hyrt.cnp.R;
import com.hyrt.cnp.account.request.UserFaceRequest;
import com.hyrt.cnp.account.requestListener.UserFaceRequestListener;
import com.hyrt.cnp.base.account.model.UserDetail;
import com.hyrt.cnp.base.account.utils.FaceUtils;
import com.hyrt.cnp.base.account.utils.FileUtils;
import com.hyrt.cnp.base.account.utils.LogHelper;
import com.hyrt.cnp.base.account.utils.PhotoUpload;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.octo.android.robospice.persistence.DurationInMillis;

public class UserFaceActivity extends BaseActivity {

    private static final String TAG = "UserFaceActivity";
//    private WeakReference<ImageView> weakImageView;
    private PhotoUpload photoUpload;
    private Uri faceFile;
    private Bitmap bitmap;
//    private GlobalImageCache.BitmapDigest localBitmapDigest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_face);
        findViewById(R.id.upload_face).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.getInstance().clearMemoryCache();
                ImageLoader.getInstance().clearDiscCache();
                faceFile = Uri.fromFile(FileUtils.createFile("cnp", "face.png"));
                photoUpload = new PhotoUpload(UserFaceActivity.this, faceFile);
                photoUpload.getFromLocal2();
            }
        });
        findViewById(R.id.upload_face_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImageLoader.getInstance().clearMemoryCache();
//                ImageLoader.getInstance().clearDiscCache();
                faceFile = Uri.fromFile(FileUtils.createFile("cnp", "face.png"));
                photoUpload = new PhotoUpload(UserFaceActivity.this, faceFile);
                photoUpload.getFromCamera(faceFile);
            }
        });
        initData();

    }


    /**
     * 加载图片头像
     */
    private void initData() {
        UserDetail.UserDetailModel userDetail = (UserDetail.UserDetailModel) getIntent().getSerializableExtra("vo");
        String facePath = FaceUtils.getAvatar(userDetail.getData().getUser_id(), FaceUtils.FACE_BIG);
        ImageView imageView = (ImageView) findViewById(R.id.big_face);
        LogHelper.i("tag", "path:" + facePath + "?time=" + userDetail.getData().getLogo());
        ImageLoader.getInstance().displayImage(facePath+"?time="+userDetail.getData().getLogo(), imageView, AppContext.getInstance().mNoCacheOnDiscImageloadoptions);
        /*weakImageView = new WeakReference<ImageView>(imageView);
        HandlerRecycleBitmapDrawable localHandlerRecycleBitmapDrawable = new HandlerRecycleBitmapDrawable(null, this);
        imageView.setImageDrawable(localHandlerRecycleBitmapDrawable);
        localBitmapDigest = new GlobalImageCache.BitmapDigest(facePath);
        localBitmapDigest.setWidth(imageView.getWidth());
        localBitmapDigest.setHeight(imageView.getHeight());
        Bitmap localBitmap = InflateUtil.loadImageWithCache(localBitmapDigest);
        if (localBitmap == null) {
            HandlerRecycleBitmapDrawable localHandlerRecycleBitmapDrawable2 = (HandlerRecycleBitmapDrawable) imageView.getDrawable();
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
        }*/

    }

    /**
     * 监听剪切好的图片并上传|剪切保存好的图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        LogHelper.i("tag", "faceFile:"+faceFile+" data:"+data);
//        if(requestCode == PhotoUpload.PHOTO_ZOOM && faceFile != null){
//            LogHelper.i("tag", "faceFile:"+faceFile);
//        }
        if (requestCode == PhotoUpload.PHOTO_ZOOM && data != null) {
            //保存剪切好的图片
            ImageLoader.getInstance().clearMemoryCache();
//            ImageLoader.getInstance().clearDiscCache();
            if (data.getParcelableExtra("data") != null) {
                bitmap = data.getParcelableExtra("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                File targetFile = FileUtils.writeFile(baos.toByteArray(), "cnp", "face.png");

                //上传图片资源
                UserFaceRequest request = new UserFaceRequest(this, targetFile);
                String lastRequestCacheKey = request.createCacheKey();
                UserFaceRequestListener userFaceRequestListener = new UserFaceRequestListener(this);
                spiceManager.execute(request, lastRequestCacheKey, DurationInMillis.ONE_SECOND, userFaceRequestListener.start());

            }

        } else if (requestCode == PhotoUpload.FROM_CAMERA) {
            LogHelper.i("tag", "photoUpload:"+photoUpload);
            if(photoUpload == null){
                if(faceFile == null){
                    faceFile = Uri.fromFile(FileUtils.createFile("cnp", "face.png"));
                }
                photoUpload = new PhotoUpload(UserFaceActivity.this, faceFile);
            }
            photoUpload.startPhotoZoom(faceFile);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 上传图片成功后,更新缓存中的图片
     */
    public void updateCacheAndUI() {
       // GlobalImageCache.getLruBitmapCache().put(localBitmapDigest, bitmap);
        setResult(1, new Intent());
        this.finish();
    }
}
