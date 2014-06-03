package com.hyrt.cnp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyrt.cnp.account.manager.UserMainActivity;
import com.hyrt.cnp.base.account.model.UserDetail;
import com.hyrt.cnp.account.request.UserDetailRequest;
import com.hyrt.cnp.account.request.UserFaceBgRequest;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.account.service.MyService;
import com.hyrt.cnp.base.account.utils.FaceUtils;
import com.hyrt.cnp.base.account.utils.FileUtils;
import com.hyrt.cnp.base.account.utils.LogHelper;
import com.hyrt.cnp.base.account.utils.PhotoUpload;
import com.hyrt.cnp.base.account.utils.UpdateHepler;
import com.hyrt.cnp.classroom.ui.ClassroomIndexActivity;
import com.hyrt.cnp.dynamic.ui.HomeInteractiveActivity;
import com.hyrt.cnp.requestListener.UserFaceBgRequestListener;
import com.hyrt.cnp.school.ui.SchoolIndexActivity;
import com.hyrt.cnp.school.ui.SchoolSearchActivity;
import com.hyrt.cnp.util.SystemUiHider;
import com.jingdong.app.pad.product.drawable.HandlerRecycleBitmapDrawable;
import com.jingdong.common.frame.BaseActivity;
import com.jingdong.common.utils.cache.GlobalImageCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;
import net.oschina.app.AppContext;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import roboguice.RoboGuice;
import roboguice.inject.InjectView;
import roboguice.inject.RoboInjector;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *首页
 * @see SystemUiHider
 */

public class FullscreenActivity extends BaseActivity {


    private LinearLayout myClass;
    private LinearLayout mySchool;
    private LinearLayout myInfo;
    private LinearLayout mydaynamic;
    private LinearLayout schoolsearch;

    @InjectView(value = R.id.facebg_iv)
    private ImageView imageViewBg;
//    @InjectView(value =R.id.intro)
    private TextView timeText;
    private TextView nameText;
    private ListView lv_portal;
    private LinearLayout portalView;
    private ImageView imageFaceView;

    private PhotoUpload photoUpload;
    private Uri faceFile;
//    private Bitmap bitmap;

    private boolean isLogin = false;

    public SpiceManager spiceManager1 = new SpiceManager(
            MyService.class);

//    private GlobalImageCache.BitmapDigest localBitmapDigest;


    private UserDetail.UserDetailModel userDetail;

    private static final String TAG = "FullscreenActivity";

    /**
     * hockeyapp plugin APP_ID for this project
     */
    private static final String APP_ID = "411f163ce21e2352706900bdccb37c92";


    @Override
    protected void onStart() {
        super.onStart();
        if (!spiceManager1.isStarted())
            spiceManager1.start(this);
    }

   /* @Override
    protected void onStop() {
        super.onStop();
        spiceManager1.shouldStop();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final RoboInjector injector = RoboGuice.getInjector(this);
        injector.injectMembersWithoutViews(this);
        super.onCreate(savedInstanceState);
        AppContext.getInstance().mUserDetail = null;
        setContentView(R.layout.activity_fullscreen2);
        portalView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_main_portal, null);
        lv_portal = (ListView)findViewById(R.id.lv_portal);
//        lv_portal.addHeaderView(portalView);

        lv_portal.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public Object getItem(int i) {
                return i;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
//                TextView tv = new TextView(FullscreenActivity.this);
                return portalView;
            }
        });
        findView();
        UpdateHepler mUpdateHelper = new UpdateHepler(this);
        mUpdateHelper.DetectUpdate();
        mySchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppContext.getInstance().mUserDetail != null) {
                    startActivity(new Intent(FullscreenActivity.this, SchoolIndexActivity.class));
                }
            }
        });
        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppContext.getInstance().mUserDetail != null) {
                    startActivity(new Intent(FullscreenActivity.this, UserMainActivity.class));
                }
            }
        });
        myClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppContext.getInstance().mUserDetail != null) {
                    startActivity(new Intent(FullscreenActivity.this, ClassroomIndexActivity.class));
                }
            }
        });
        mydaynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AppContext.getInstance().mUserDetail != null){
                    Intent intent = new Intent();
                    intent.setClass(FullscreenActivity.this, HomeInteractiveActivity.class);
                    intent.putExtra("userinfo",userDetail);
                    startActivity(intent);
                }

            }
        });
        schoolsearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FullscreenActivity.this, SchoolSearchActivity.class));
            }
        });
        findViewById(R.id.update_cover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceFile = Uri.fromFile(FileUtils.createFile("cnp", "face_cover.png"));
                photoUpload = new PhotoUpload(FullscreenActivity.this, faceFile);
                photoUpload.setRang(true);
                photoUpload.choiceItem();
            }
        });
        checkForUpdates();
    }

    /**
     * 获取数据
     */
    private void initData() {
        LogHelper.i(TAG, "initData");
        //获取用户的基本信息
        UserDetailRequest userDetailRequest = new UserDetailRequest(this);
        LogHelper.i(TAG, "initData1");
        spiceManager1.execute(userDetailRequest, userDetailRequest.createCacheKey(),
                1000, new BaseRequestListener(this) {

            @Override
            public BaseRequestListener start() {
                LogHelper.i(TAG, "initData:start");
                showIndeterminate(R.string.user_info_pg);
                return this;
            }

            @Override
            public void onRequestFailure(SpiceException e) {
                LogHelper.i(TAG, "initData:fail");
                super.onRequestFailure(e);
                isLogin = false;
                showMessage(R.string.getinfo_msg_title, R.string.getinfod_msgerror_content);
            }

            @Override
            public void onRequestSuccess(Object o) {
                LogHelper.i(TAG, "initData:success");
                super.onRequestSuccess(o);
                if (o == null) {
                    isLogin = false;
                    showMessage(R.string.getinfo_msg_title, R.string.getinfod_msgerror_content);
                } else if (context.get() != null) {
                    LogHelper.i(TAG, "name:"+((UserDetail.UserDetailModel) o).getData().getNurseryName());
                    isLogin = true;
                    //获取基本资料成功后，加载头像
                    FullscreenActivity fullscreenActivity = (FullscreenActivity) context.get();
                    fullscreenActivity.initFaceIfSuccess((UserDetail.UserDetailModel) o);
                }
            }
        }.start());
        LogHelper.i(TAG, "initData2");
    }

    /**
     * 加载图片头像
     */
    private void initFaceIfSuccess(UserDetail.UserDetailModel userData) {
        userDetail = userData;
        if(userData != null){
            AppContext.getInstance().mUserDetail = userData.getData();
        }
        //附加名字
        nameText.setText(userData.getData().getRenname());
        timeText.setText(userData.getData().getTimeText());
        //加载头像
        String facePath = FaceUtils.getAvatar(userDetail.getData().getUser_id(), FaceUtils.FACE_BIG);

        ImageLoader.getInstance().displayImage(
                facePath + "?time=" + userDetail.getData().getLogo(),
                imageFaceView, AppContext.getInstance().mNoCacheOnDiscImageloadoptions);
//        showDetailImage(facePath + "?time=" + userDetail.getData().getLogo(), imageView, false);

        //加载头像地址
        ImageLoader.getInstance().clearMemoryCache();
        DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.hua2)
                .showImageOnFail(R.drawable.hua2)
                .showImageForEmptyUri(R.drawable.hua2)
                .cacheInMemory(true)
                .build();
        String faceBgPath = FaceUtils.getAvatar(userDetail.getData().getUser_id(), FaceUtils.FACE_BG);
        ImageLoader.getInstance().displayImage(
                faceBgPath,
                imageViewBg, mOptions);
//        localBitmapDigest = showDetailImage(faceBgPath, imageViewBg, true);


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

                LogHelper.i(TAG, "file:" + file + " path:" + path);
                //上传图片资源
                UserFaceBgRequest request = new UserFaceBgRequest(this, file);
                String lastRequestCacheKey = request.createCacheKey();
                UserFaceBgRequestListener userFaceRequestListener = new UserFaceBgRequestListener(this);
                spiceManager.execute(request, lastRequestCacheKey, DurationInMillis.ONE_SECOND, userFaceRequestListener.start());
            }
        }

       /* if (requestCode == PhotoUpload.PHOTO_ZOOM && data != null && data.getParcelableExtra("data") != null) {

            //保存剪切好的图片
            bitmap = data.getParcelableExtra("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            File targetFile = FileUtils.writeFile(baos.toByteArray(), "cnp", "face_cover.png");

            //上传图片资源
            UserFaceBgRequest request = new UserFaceBgRequest(this, targetFile);
            String lastRequestCacheKey = request.createCacheKey();
            UserFaceBgRequestListener userFaceRequestListener = new UserFaceBgRequestListener(this);
            spiceManager.execute(request, lastRequestCacheKey, DurationInMillis.ONE_SECOND, userFaceRequestListener.start());
        } else if (requestCode == PhotoUpload.FROM_CAMERA) {
            photoUpload.startRangPhotoZoom(faceFile);
        }*/
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 上传图片成功后,更新缓存中的图片
     */
    public void updateCacheAndUI() {
//        if (localBitmapDigest != null) {
//            GlobalImageCache.getLruBitmapCache().put(localBitmapDigest, bitmap);
//            HandlerRecycleBitmapDrawable localHandlerRecycleBitmapDrawable =
//                    (HandlerRecycleBitmapDrawable) imageViewBg.getDrawable();
//            localHandlerRecycleBitmapDrawable.setBitmap(bitmap);
//            localHandlerRecycleBitmapDrawable.invalidateSelf();
//        }

        ImageLoader.getInstance().clearMemoryCache();
        DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.hua2)
                .showImageOnFail(R.drawable.hua2)
                .showImageForEmptyUri(R.drawable.hua2)
                .cacheInMemory(true)
                .build();
        String faceBgPath = FaceUtils.getAvatar(userDetail.getData().getUser_id(), FaceUtils.FACE_BG);
        ImageLoader.getInstance().displayImage(
                faceBgPath,
                imageViewBg, mOptions);
    }

    @Override
    public void onResume() {
        super.onResume();
        AppContext.getInstance().activities.clear();
        if(AppContext.getInstance().mUserDetail == null){
            imageViewBg.setImageResource(R.drawable.hua2);
            imageFaceView.setImageResource(R.drawable.cnp_default_img);
            nameText.setText("");
            timeText.setText("");
            checkForCrashes();
            initData();
        }
    }


    private void checkForCrashes() {
        CrashManager.register(this, APP_ID);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this, APP_ID);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void findView(){
        myClass = (LinearLayout) portalView.findViewById(R.id.layout_index_classroom);
        mySchool = (LinearLayout) portalView.findViewById(R.id.layout_index_school);
        myInfo = (LinearLayout) portalView.findViewById(R.id.layout_index_user);
        mydaynamic = (LinearLayout) portalView.findViewById(R.id.layout_index_dynamic);
        schoolsearch = (LinearLayout) portalView.findViewById(R.id.layout_index_search);
        imageFaceView = (ImageView) findViewById(R.id.face_iv);
        nameText = (TextView) findViewById(R.id.name_tv);
        timeText = (TextView) findViewById(R.id.intro);
    }
}
