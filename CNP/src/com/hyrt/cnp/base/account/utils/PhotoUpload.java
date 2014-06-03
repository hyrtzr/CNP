package com.hyrt.cnp.base.account.utils;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.*;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.ui.AlbumBrowserActivity;
import com.jingdong.common.frame.BaseActivity;

import net.oschina.app.AppContext;

import static com.hyrt.cnp.base.account.ui.LightAlertDialog.Builder;

/**
 * 图片处理
 * Created by yepeng on 14-1-17.
 */
public class PhotoUpload {

    public static int FROM_CAMERA = 2;
    public static int PHOTO_ZOOM = 3;

    private BaseActivity baseActivity;
    private Uri uri;
    private boolean isRang;

    private Dialog mPhotoSelctDialog;

    public void setRang(boolean isRang) {
        this.isRang = isRang;
    }

    /**
     *
     * @param baseActivity 当前活动
     * @param uri 图片保存路径uri
     */
    public PhotoUpload(BaseActivity baseActivity,Uri uri){
        this.baseActivity = baseActivity;
        this.uri = uri;
    }

    /**
     * 图片剪切功能,需要在活动中监听forresult方法
     * @param paramUri 图片所在资源路径
     */
    public void startPhotoZoom(Uri paramUri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(paramUri, "image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        baseActivity.startActivityForResult(intent,PHOTO_ZOOM);
    }

    /**
     * 长图片图片剪切功能,需要在活动中监听forresult方法
     * @param paramUri 图片所在资源路径
     */
    public void startRangPhotoZoom(Uri paramUri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(paramUri, "image/*");
        intent.putExtra("crop","true");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 7);
        intent.putExtra("aspectY", 4);
        intent.putExtra("outputX", 350);
        intent.putExtra("outputY", 200);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        baseActivity.startActivityForResult(intent,PHOTO_ZOOM);
    }

    /**
     * 获取照相的图片 需要在活动中监听forresult方法
     * @param uri 照片保存路径
     */
    public void getFromCamera(Uri uri){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output",uri);
        baseActivity.startActivityForResult(intent, FROM_CAMERA);
    }

    /**
     * 选择两种图片方式，最终需要在活动中监听forresult方法
     */
    public void choiceItem(){
        if(mPhotoSelctDialog == null){
            mPhotoSelctDialog = new Dialog(baseActivity, R.style.MyDialog);
            mPhotoSelctDialog.setContentView(R.layout.layout_photo_upload_pop);
            mPhotoSelctDialog.getWindow().setLayout(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout layout_dialog_parent = (LinearLayout) mPhotoSelctDialog.findViewById(R.id.layout_dialog_parent);
            TextView tv_select_from_album = (TextView) mPhotoSelctDialog.findViewById(R.id.tv_select_from_album);
//            TextView tv_select_from_camera = (TextView) mPhotoSelctDialog.findViewById(R.id.tv_select_from_camera);
            TextView tv_cancle_dialog = (TextView) mPhotoSelctDialog.findViewById(R.id.tv_cancle_dialog);

            View.OnClickListener mLayoutOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(view.getId() == R.id.tv_select_from_album){
                        getFromLocal();
                    }/*else if(view.getId() == R.id.tv_select_from_camera){
                        getFromCamera(uri);
                    }*/
                    mPhotoSelctDialog.dismiss();
                }
            };
            layout_dialog_parent.setOnClickListener(mLayoutOnClickListener);
            tv_select_from_album.setOnClickListener(mLayoutOnClickListener);
//            tv_select_from_camera.setOnClickListener(mLayoutOnClickListener);
            tv_cancle_dialog.setOnClickListener(mLayoutOnClickListener);
        }
        mPhotoSelctDialog.show();

       /* AlertDialog.Builder builder =  Builder.create(baseActivity);
        builder.setTitle(R.string.picupload_title);
        builder.setItems(R.array.upload_type,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        getFromLocal();
                        break;
                    case 1:
                        getFromCamera(uri);
                        break;
                }
            }
        });
        builder.create();
        builder.show();*/
    }

    /**
     * 获取本地图片
     */
    public void getFromLocal(){
        Intent intent = new Intent();
        intent.setClass(baseActivity, AlbumBrowserActivity.class);
        baseActivity.startActivityForResult(intent, AppContext.getInstance().RESULT_FOR_PHONE_ALBUM);
    }

    /**
     * 获取本地图片，并剪切
     */
    public void getFromLocal2(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        if(isRang){
            intent.putExtra("aspectX", 7);
            intent.putExtra("aspectY", 4);
            intent.putExtra("outputX", 350);
            intent.putExtra("outputY", 200);
        }else{
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
        }
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        System.gc();
        baseActivity.startActivityForResult(intent, PhotoUpload.PHOTO_ZOOM);
    }
}
