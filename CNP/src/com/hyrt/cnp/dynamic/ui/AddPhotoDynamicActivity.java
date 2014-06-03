package com.hyrt.cnp.dynamic.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.ui.AlbumBrowserActivity;
import com.hyrt.cnp.base.account.utils.AlertUtils;
import com.hyrt.cnp.base.account.utils.FileUtils;
import com.hyrt.cnp.base.account.utils.PhotoUpload;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.adapter.AddPhotoAdapter;
import com.hyrt.cnp.dynamic.request.AddPhotoCompleteRequest;
import com.hyrt.cnp.dynamic.request.AddPhotoRequest;
import com.hyrt.cnp.dynamic.request.MyAlbumRequest;
import com.hyrt.cnp.dynamic.requestListener.AddPhotoCompleteRequestListener;
import com.hyrt.cnp.dynamic.requestListener.AddPhotoRequestListener;
import com.hyrt.cnp.dynamic.requestListener.MyAlbumRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoe on 2014-04-08.
 */
public class AddPhotoDynamicActivity extends BaseActivity{

    private EditText etContent;
    private RelativeLayout layoutChangeAblum;
    private TextView tvAblumText;
    private ImageView btnAddPhoto;
//    private MenuItem sendbtn;
    private ImageView photo;
    private GridView gvPhoto;
//    private AlertDialog uploaddialog;
    private View btn_back;
    private TextView tv_send;
    private TextView tv_action_title;

    private AddPhotoAdapter mAdapter;

    private boolean sendEnabled;

    private Uri faceFile;
    private PhotoUpload photoUpload;
    private Bitmap bitmap;
    private File targetFile;

    private Album selectAlbum;

    private List<Album> datas = new ArrayList<Album>();
    private ArrayList<String> checkeds = new ArrayList<String>();
    private ArrayList<String> imageUploadQueue = new ArrayList<String>();
    private ArrayList<String> successMsgs = new ArrayList<String>();

    public static final int RESULT_FOR_CHANGE_ALBUM = 101;
    public static final int RESULT_FOR_PHONE_ALBUM = 102;

    private boolean inited = false;

    private static final String TAG = "AddPhotoDynamicActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(inited){
           return;
        }
        inited = true;
        setContentView(R.layout.activity_add_ablum);
        findView();

        mAdapter = new AddPhotoAdapter(checkeds, this);
        gvPhoto.setAdapter(mAdapter);

        setListener();
        loadData();
    }

    private void loadData() {
        MyAlbumRequestListener requestListener = new MyAlbumRequestListener(this);
        requestListener.setListener(mAlbumRequestListener);
        MyAlbumRequest request = new MyAlbumRequest(Album.Model.class, this);
        spiceManager.execute(request, request.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                requestListener.start());
    }

    private MyAlbumRequestListener.RequestListener mAlbumRequestListener = new MyAlbumRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Object data) {
            datas.clear();
            datas.addAll(((Album.Model)data).getData());
            if(datas.size() > 0){
                if(tvAblumText.getText().length() <= 0 || tvAblumText.getText().equals("请选择相册")){
                    tvAblumText.setText(datas.get(0).getAlbumName());
                    selectAlbum = datas.get(0);
                }
            }else{
                tvAblumText.setText("请选择相册");
                selectAlbum = null;
            }

        }

        @Override
        public void onRequestFailure(SpiceException e) {

        }
    };

    public void setListener(){
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faceFile = Uri.fromFile(FileUtils.createFile("cnp", "face_cover.png"));
                photoUpload = new PhotoUpload(AddPhotoDynamicActivity.this, faceFile);
//                photoUpload.setRang(true);
                photoUpload.choiceItem();

                /*Intent intent = new Intent();
                intent.setClass(AddPhotoDynamicActivity.this, AlbumBrowserActivity.class);
                startActivity(intent);*/
            }
        });

        layoutChangeAblum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AddPhotoDynamicActivity.this, ChangeAlbumActivity.class);
                startActivityForResult(intent, RESULT_FOR_CHANGE_ALBUM);
            }
        });

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if(checkeds.size() > 0 && selectAlbum != null){
                    setSendEnabled(true);
                }else{
                    setSendEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faceFile = null;
                targetFile = null;
                photo.setVisibility(View.GONE);
                TextView textview = (TextView) findViewById(R.id.tv_add_photo_text);
                textview.setVisibility(View.VISIBLE);
                btnAddPhoto.setVisibility(View.VISIBLE);
                if(checkeds.size() <= 0){
                    setSendEnabled(false);
                }
            }
        });

        mAdapter.setOnClickListener(new AddPhotoAdapter.OnClickListener() {
            @Override
            public void onClick(int type, int position) {
                if(type == 0){
                    Intent intent = new Intent();
                    intent.setClass(AddPhotoDynamicActivity.this, AlbumBrowserActivity.class);
                    intent.putStringArrayListExtra("checkeds", checkeds);
                    startActivityForResult(intent, AddPhotoDynamicActivity.RESULT_FOR_PHONE_ALBUM);
                }else{
                    checkeds.remove(checkeds.get(position));
                    mAdapter.notifyDataSetChanged();
                    if(checkeds.size() > 0 && selectAlbum != null){
                        setSendEnabled(true);
                    }else{
                        setSendEnabled(false);
                    }
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sendEnabled){
                    if(selectAlbum != null){
                        successMsgs.clear();
                        imageUploadQueue.clear();
                        imageUploadQueue.addAll(checkeds);
                        String path = imageUploadQueue.remove(0);
                        targetFile = new File("", path);
                        uploadImage();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkeds.size() > 0 && selectAlbum != null) {
            setSendEnabled(true);
        }else{
            setSendEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(resultCode == RESULT_FOR_CHANGE_ALBUM){
                Album tempSelectAlbum = (Album) data.getSerializableExtra("album");
                if(tempSelectAlbum != null){
                    selectAlbum = tempSelectAlbum;
                    tvAblumText.setText(selectAlbum.getAlbumName());
                }
            }else if (requestCode == PhotoUpload.FROM_CAMERA && data.getParcelableExtra("data") != null) {
                //保存剪切好的图片
                bitmap = data.getParcelableExtra("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                targetFile = FileUtils.writeFile(baos.toByteArray(), "cnp", "dynamic_upload_photo"+System.currentTimeMillis()+".png");
                android.util.Log.i(TAG, "getPath():"+targetFile.getPath());
                checkeds.add(targetFile.getPath());
                mAdapter.notifyDataSetChanged();
               /* BitmapDrawable bd = new BitmapDrawable(bitmap);
                photo.setBackground(bd);
                photo.setVisibility(View.VISIBLE);
                TextView textview = (TextView) findViewById(R.id.tv_add_photo_text);
                textview.setVisibility(View.GONE);
                btnAddPhoto.setVisibility(View.GONE);*/

                if(selectAlbum != null){
                    setSendEnabled(true);
                }
            }else if(resultCode == RESULT_FOR_PHONE_ALBUM){
                if(data.getSerializableExtra("checkeds") != null){
                    checkeds.clear();
                    checkeds.addAll(data.getStringArrayListExtra("checkeds"));
                    mAdapter.notifyDataSetChanged();
                    if(checkeds.size() > 0 && selectAlbum != null){
                        setSendEnabled(true);
                    }else{
                        setSendEnabled(false);
                    }
                }
            }
        }
    }


    @Override
    protected void initTitleview() {
        actionBar.hide();
    }

    public void uploadImage(){
        /*uploaddialog = LightAlertDialog.create(AddPhotoDynamicActivity.this);
        uploaddialog.setTitle("上传中...");
        uploaddialog.setMessage("发送失败!");
        uploaddialog.show();*/

        android.util.Log.i(TAG, "selectAlbum:"+selectAlbum.getAlbumName()+"-"+selectAlbum.getPaId());
        AddPhotoRequestListener requestListener = new AddPhotoRequestListener(this);
        requestListener.setListener(mAddPhotoRequestListener);
        AddPhotoRequest request =
                new AddPhotoRequest(BaseTest.class, this, selectAlbum.getPaId()+"",
                        selectAlbum.getAlbumName(), etContent.getText().toString(), targetFile);
        spiceManager.execute(request, request.getcachekey(), 1,
                requestListener.start());
    }

    private AddPhotoRequestListener.RequestListener mAddPhotoRequestListener = new AddPhotoRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Object o) {
            String msg = ((BaseTest)o).getMsg();
            Log.i(TAG, "msg:"+msg);
            successMsgs.add(msg);
            if(imageUploadQueue.size()>0){
                String path = imageUploadQueue.remove(0);
                targetFile = new File("", path);
                uploadImage();
            }else{
                addPhotoComplete();
            }
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            AlertUtils.getInstance().showCenterToast(
                    AddPhotoDynamicActivity.this,
                    getString(R.string.adddynamicphoto_fail));
        }
    };

    private void addPhotoComplete(){
        Log.i(TAG , "addPhotoComplete");
        AddPhotoCompleteRequestListener requestListener = new AddPhotoCompleteRequestListener(this);
        requestListener.setListener(new AddPhotoCompleteRequestListener.RequestListener() {
            @Override
            public void onRequestSuccess(Object o) {
                AlertUtils.getInstance().showCenterToast(
                        AddPhotoDynamicActivity.this,
                        getString(R.string.adddynamicphoto_success));
                Intent intent = new Intent();
                intent.setClass(AddPhotoDynamicActivity.this, DynamicPhotoListActivity.class);
                intent.putExtra("album", selectAlbum);
                startActivity(intent);
                finish();
            }

            @Override
            public void onRequestFailure(SpiceException e) {
                AlertUtils.getInstance().showCenterToast(
                        AddPhotoDynamicActivity.this,
                        getString(R.string.adddynamicphoto_fail));
            }
        });
        AddPhotoCompleteRequest request =
                new AddPhotoCompleteRequest(
                        BaseTest.class, AddPhotoDynamicActivity.this,
                        selectAlbum.getPaId()+"", selectAlbum.getAlbumName(),
                        successMsgs);
        spiceManager.execute(request, request.getcachekey(), 1,
                requestListener.start());
    }

    private void setSendEnabled(boolean enable){
        if(enable){
            sendEnabled = true;
            tv_send.setTextColor(getResources().getColor(android.R.color.white));
        }else{
            sendEnabled = false;
            tv_send.setTextColor(getResources().getColor(R.color.sendbtn_enable_color));
        }
    }

    public void findView(){
        etContent = (EditText) findViewById(R.id.et_add_ablum_content);
        layoutChangeAblum = (RelativeLayout) findViewById(R.id.layout_change_ablum);
        tvAblumText = (TextView) findViewById(R.id.tv_ablum_text);
        btnAddPhoto = (ImageView) findViewById(R.id.btn_add_photo);
        photo = (ImageView) findViewById(R.id.iv_photo1);
        gvPhoto = (GridView) findViewById(R.id.gv_photo);
        btn_back = findViewById(R.id.btn_back);
        tv_send = (TextView) findViewById(R.id.tv_send);
        tv_action_title = (TextView) findViewById(R.id.tv_action_title);
        tv_action_title.setText(getString(R.string.adddynamicphoto));
    }


}
