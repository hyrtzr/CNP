package com.hyrt.cnp.classroom.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.model.ClassRoom;
import com.hyrt.cnp.base.account.model.ClassRoomBabay;
import com.hyrt.cnp.base.account.utils.AlertUtils;
import com.hyrt.cnp.base.account.utils.FaceUtils;
import com.hyrt.cnp.base.account.utils.FileUtils;
import com.hyrt.cnp.base.account.utils.LogHelper;
import com.hyrt.cnp.base.account.utils.PhotoUpload;
import com.hyrt.cnp.R;
import com.hyrt.cnp.classroom.adapter.ClassroomIndexAdapter;
import com.hyrt.cnp.classroom.request.ClassroomAlterRequest;
import com.hyrt.cnp.classroom.request.ClassroomInfoRequest;
import com.hyrt.cnp.classroom.requestListener.ClassroomAlterRequestListener;
import com.hyrt.cnp.classroom.requestListener.ClassroomBabayRequestListener;
import com.hyrt.cnp.classroom.requestListener.ClassroomInfoRequestListener;
import com.hyrt.cnp.dynamic.fragment.AlldynamicFragment;
import com.hyrt.cnp.dynamic.ui.SendDynamicActivity;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import net.oschina.app.AppContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import roboguice.RoboGuice;

/**
 * Created by GYH on 14-1-16.
 */
public class ClassroomIndexActivity extends BaseActivity{
    private GridView gridView;
    private int[] imageResId;
    private String[] text={"班级公告","每日食谱","班级相册","班级成员"};
    private int[] bg;
    private Intent intent;
    private TextView renname,rennames;
    private ImageView classroomimage;
    private EditText classroominfo;
    private Dialog mPhotoSelctDialog;

    private PhotoUpload photoUpload;
    private Uri faceFile;

    private static final String TAG = "ClassroomIndexActivity";

    @Inject
    @Named("schoolNoticeActivity")
    private Class schoolNoticeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroomindex);
        RoboGuice.getInjector(this.getApplicationContext()).injectMembers(this);
        initView();
        LoadData();
    }

    private void initView(){
        classroomimage=(ImageView)findViewById(R.id.classroomimage);

        classroominfo=(EditText)findViewById(R.id.classroomintro);
        classroominfo.clearFocus();
        classroominfo.setCursorVisible(false);

            classroominfo.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    if(!imm.isActive()){
//                        return true;
//                    }
                    if(AppContext.getInstance().mUserDetail == null
                            || AppContext.getInstance().mUserDetail.getGroupID() == 7){
                        return true;
                    }
                    classroominfo.setCursorVisible(true);
                    /*if(Build.VERSION.SDK_INT >= 16){
                        if(!classroominfo.isCursorVisible()){
                            return true;
                        }
                    }else{
                        classroominfo.setCursorVisible(true);
                    }*/

                    return false;
                }
            });

        classroominfo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                classroominfo.setCursorVisible(false);
                classroominfo.clearFocus();
                ClassroomAlterRequest request = new ClassroomAlterRequest(
                        ClassroomIndexActivity.this, classroominfo.getText().toString());
                ClassroomAlterRequestListener userFaceRequestListener
                        = new ClassroomAlterRequestListener(ClassroomIndexActivity.this);
                userFaceRequestListener.setListener(new ClassroomAlterRequestListener.RequestListener() {
                    @Override
                    public void onRequestSuccess(BaseTest data) {
                        AlertUtils.getInstance().showCenterToast(ClassroomIndexActivity.this, "修改成功");
                        LoadData();
                    }

                    @Override
                    public void onRequestFailure() {

                    }
                });
                spiceManager.execute(request, request.getcachekey(), DurationInMillis.ONE_SECOND, userFaceRequestListener.start());
                return false;
            }
        });
        renname=(TextView)findViewById(R.id.text_renname);
        rennames=(TextView)findViewById(R.id.text_rennames);
        imageResId = new int[] { R.drawable.classroom_notice, R.drawable.classroom_recipe,
                R.drawable.classroom_photo, R.drawable.classroom_member};
        bg = new int[]{R.color.classroomindex_notice,R.color.classroomindex_recipe,
                R.color.classroomindex_photo,R.color.classroomindex_member};
        gridView = (GridView) findViewById(R.id.schoolindexlist);
        ClassroomIndexAdapter schoolIndexAdapter=new ClassroomIndexAdapter(text,imageResId,bg,this);
        gridView.setAdapter(schoolIndexAdapter);
        gridView.setOnItemClickListener(new ItemClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(menu != null && AppContext.getInstance().mUserDetail != null
                && AppContext.getInstance().mUserDetail.getGroupID() != 7){
            menu.add("修改")
                    .setIcon(R.drawable.ic_setting)
                    .setShowAsAction(
                            MenuItem.SHOW_AS_ACTION_ALWAYS);
            return  true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("修改")) {
            if(mPhotoSelctDialog == null){
                mPhotoSelctDialog = new Dialog(this, R.style.MyDialog);
                mPhotoSelctDialog.setContentView(R.layout.layout_classroom_alter_dialog);
                mPhotoSelctDialog.getWindow().setLayout(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                LinearLayout layout_dialog_parent = (LinearLayout) mPhotoSelctDialog.findViewById(R.id.layout_dialog_parent);
                TextView tv_select_from_album = (TextView) mPhotoSelctDialog.findViewById(R.id.tv_change_banner);
            TextView tv_change_intro = (TextView) mPhotoSelctDialog.findViewById(R.id.tv_change_intro);
                TextView tv_cancle_dialog = (TextView) mPhotoSelctDialog.findViewById(R.id.tv_cancle_dialog);

                View.OnClickListener mLayoutOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(view.getId() == R.id.tv_change_banner){
                            if(photoUpload == null){
                                photoUpload = new PhotoUpload(ClassroomIndexActivity.this, faceFile);
                            }
                            photoUpload.getFromLocal();
                        }else if(view.getId() == R.id.tv_change_intro){
                            mPhotoSelctDialog.dismiss();
                            classroominfo.setSelection(classroominfo.getText().length());
                            classroominfo.setCursorVisible(true);
                            classroominfo.setEnabled(true);
                            classroominfo.setFocusable(true);
                            classroominfo.requestFocus();
                            InputMethodManager inputManager =
                                    (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.toggleSoftInput(0, 0);
                        }else{
                            mPhotoSelctDialog.dismiss();
                        }
                    }
                };
                layout_dialog_parent.setOnClickListener(mLayoutOnClickListener);
                tv_select_from_album.setOnClickListener(mLayoutOnClickListener);
                tv_change_intro.setOnClickListener(mLayoutOnClickListener);
                tv_cancle_dialog.setOnClickListener(mLayoutOnClickListener);
            }
            mPhotoSelctDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadData(){
        ClassroomInfoRequestListener sendwordRequestListener = new ClassroomInfoRequestListener(this);
        ClassroomInfoRequest schoolRecipeRequest=new ClassroomInfoRequest(ClassRoom.Model2.class,this);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    public void updateUI(ClassRoom.Model2 model2){
        renname.setText("班主任："+model2.getData().getRenname());
        rennames.setText("教师："+model2.getData().getRennames());
        titletext.setText(model2.getData().getRoomname());
        String bannerUrl = FaceUtils.getClassroomBanner(model2.getData().getClassroomID());
//        android.util.Log.i("tag", "bannerUrl:"+bannerUrl);
//        showDetailImage(bannerUrl,classroomimage,false);
//        classroomimage.setImageResource(R.drawable.hua2);
        DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.hua2)
                .showImageOnFail(R.drawable.hua2)
                .showImageForEmptyUri(R.drawable.hua2)
                .cacheInMemory(true)
                .build();
        ImageLoader.getInstance().displayImage(bannerUrl, classroomimage, mOptions);
        classroominfo.setText(model2.getData().getSignature());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == AppContext.getInstance().RESULT_FOR_PHONE_ALBUM){
            ArrayList<String> checkeds = data.getStringArrayListExtra("checkeds");
            if(checkeds != null && checkeds.size() > 0){
                String path = checkeds.get(0);
                File file = new File("", path);
//                try{
//                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse("file:"+path));
//                }catch (IOException e){
//                    android.util.Log.i(TAG, "uri to bitmap error");
//                }

                android.util.Log.i(TAG, "file:"+file+" path:"+path);
                //上传图片资源
                ClassroomAlterRequest request = new ClassroomAlterRequest(ClassroomIndexActivity.this, file);
                ClassroomAlterRequestListener userFaceRequestListener = new ClassroomAlterRequestListener(this);
                userFaceRequestListener.setListener(new ClassroomAlterRequestListener.RequestListener() {
                    @Override
                    public void onRequestSuccess(BaseTest data) {
                        AlertUtils.getInstance().showCenterToast(ClassroomIndexActivity.this, "修改成功");
                        LoadData();
                    }

                    @Override
                    public void onRequestFailure() {

                    }
                });
                spiceManager.execute(request, request.getcachekey(), DurationInMillis.ONE_SECOND, userFaceRequestListener.start());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mPhotoSelctDialog != null){
            mPhotoSelctDialog.dismiss();
        }
    }

    class  ItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> arg0,View arg1,int arg2,long arg3) {
            int i = arg2;
            switch (i){
                case 0:
                    intent =new Intent();
                    intent.setClass(ClassroomIndexActivity.this,schoolNoticeActivity);
                    intent.putExtra("data","classroom");
                    startActivity(intent);
                    break;
                case 1:
                    startActivity(new Intent().setClass(ClassroomIndexActivity.this,ClassroomRecipeInfoActivity.class));
                    break;
                case 2:
                    intent =new Intent();
                    intent.setClass(ClassroomIndexActivity.this,ClassroomAlbumActivity.class);
                    intent.putExtra("Category","ClassroomIndexActivity");
                    startActivity(intent);
                    break;
                case 3:
                    startActivity(new Intent().setClass(ClassroomIndexActivity.this,ClassroomBabayActivity.class));
                    break;
            }
        }
    }
}
