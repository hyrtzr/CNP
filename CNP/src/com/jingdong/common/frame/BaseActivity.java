package com.jingdong.common.frame;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Key;
import com.hyrt.cnp.R;
import com.hyrt.cnp.base.service.MyService;
import com.hyrt.cnp.base.view.HackyViewPager;
import com.hyrt.cnp.base.view.ImageAdapter;
import com.jingdong.app.pad.product.ProductListFragment;
import com.jingdong.app.pad.product.drawable.HandlerRecycleBitmapDrawable;
import com.jingdong.app.pad.utils.InflateUtil;
import com.jingdong.common.broadcastReceiver.InterfaceBroadcastReceiver;
import com.jingdong.common.broadcastReceiver.InterfaceBroadcastReceiver.Command;
import com.jingdong.common.frame.taskStack.ApplicationManager;
import com.jingdong.common.http.HttpGroup;
import com.jingdong.common.http.HttpGroupSetting;
import com.jingdong.common.http.HttpSetting;
import com.jingdong.common.utils.DPIUtil;
import com.jingdong.common.utils.Log;
import com.jingdong.common.utils.cache.GlobalImageCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.octo.android.robospice.SpiceManager;

import net.oschina.app.AppContext;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;
import roboguice.util.RoboContext;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 应用程序Activity的基类
 */
public class BaseActivity extends ActionBarActivity implements RoboContext {

    private static final String TAG = "BaseActivity";
    private MyActivity currentMyActivity;


    protected HashMap<Key<?>, Object> scopedObjects = new HashMap<Key<?>, Object>();

    protected View viewTitleBar;
    protected ActionBar actionBar;
    protected ImageView backimage;
    protected TextView titletext;
    private PhotoViewAttacher mAttacher;
    protected PopupWindow popWin;

    protected Menu mymenu;

    @Override
    public Map<Key<?>, Object> getScopedObjectMap() {
        return scopedObjects;
    }

    public SpiceManager spiceManager = new SpiceManager(
            MyService.class);

    public HttpGroup getHttpGroupaAsynPool() {
        return getHttpGroupaAsynPool(HttpGroupSetting.TYPE_JSON);
    }

    public HttpGroup getHttpGroupaAsynPool(int type) {
        HttpGroupSetting localHttpGroupSetting = new HttpGroupSetting();
        localHttpGroupSetting.setType(type);
        return getHttpGroupaAsynPool(localHttpGroupSetting);
    }

    public HttpGroup getHttpGroupaAsynPool(
            final HttpGroupSetting paramHttpGroupSetting) {
        HttpGroup.HttpGroupaAsynPool localHttpGroupaAsynPool = new HttpGroup.HttpGroupaAsynPool(
                paramHttpGroupSetting);
        return localHttpGroupaAsynPool;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //ApplicationManager.back();
                ImageLoader.getInstance().clearMemoryCache();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final RoboInjector injector = RoboGuice.getInjector(this.getApplicationContext());
        injector.injectMembers(this);
        super.onCreate(savedInstanceState);
        if (true) {
            Log.d("MainActivity",
                    " -->> this.getResources().getDisplayMetrics().density:"
                            + getResources().getDisplayMetrics().density
            );
            Log.d("MainActivity",
                    " -->> this.getResources().getDisplayMetrics().heightPixels:"
                            + getResources().getDisplayMetrics().heightPixels
            );
            Log.d("MainActivity",
                    " -->> this.getResources().getDisplayMetrics().widthPixels:"
                            + getResources().getDisplayMetrics().widthPixels
            );
            Log.d("MainActivity",
                    " -->> this.getResources().getDisplayMetrics().xdpi:"
                            + getResources().getDisplayMetrics().xdpi
            );
            Log.d("MainActivity",
                    " -->> this.getResources().getDisplayMetrics().ydpi:"
                            + getResources().getDisplayMetrics().ydpi
            );
            Log.d("MainActivity",
                    " -->> this.getResources().getDisplayMetrics().densityDpi:"
                            + getResources().getDisplayMetrics().densityDpi
            );
            try {
                Log.d("MainActivity",
                        "getSizeOfScreen() -->> " + DPIUtil.getSizeOfScreen());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //效率观察
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                    .detectDiskReads().detectDiskWrites().detectNetwork()
//                    .penaltyLog().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                    .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
//                    .penaltyLog().penaltyDeath().build());

        }
        if (!(this.getLocalClassName().contains("FullscreenActivity"))) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeButtonEnabled(true);
            initTitleview();
            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        AppContext.getInstance().setBaseActivity(this);
    }

    @Override
    protected void onDestroy() {
        try {
            RoboGuice.destroyInjector(this);
        } finally {
            super.onDestroy();
            AppContext.getInstance().setBaseActivity(null);
        }

        if (mAttacher != null) {
            mAttacher.cleanup();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        contentViewChanged();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        contentViewChanged();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        contentViewChanged();
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
        contentViewChanged();
    }

    private void contentViewChanged() {
        RoboGuice.getInjector(this).injectViewMembers(this);
    }

    public MyActivity getCurrentMyActivity() {
        return currentMyActivity;
    }

    public void setCurrentMyActivity(MyActivity paramMyActivity) {
        currentMyActivity = paramMyActivity;
    }

    public int getCurrentFragmentViewId() {
        if (getCurrentMyActivity() != null)
            Log.d("MainActivity", "getview id-->> "
                    + getCurrentMyActivity().getViewId());
        else if (Log.D)
            Log.d("MainActivity", "getCurrentMyActivity() == null -->> ");
        return R.id.container;
    }

    public void toTargetActivity(Command localCommand) {
        if (Log.D)
            Log.d(TAG, "MainActivity toTargetActivity() -->> ");
        Bundle localBundle = localCommand.getBundle();
        switch (localBundle.getInt("moduleId")) {
            case InterfaceBroadcastReceiver.MODULE_ID_HOME:
                ProductListFragment.ProductListTM productListTM = new ProductListFragment.ProductListTM();
                productListTM.setBundle(localBundle);
                ApplicationManager.go(productListTM);
                break;
        }
    }

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        if(spiceManager.isStarted()){
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    /**
     * 初始化顶部标题栏
     */
    protected void initTitleview() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        viewTitleBar = getLayoutInflater().inflate(R.layout.layout_actionbar_title, null);
        backimage = (ImageView) viewTitleBar.findViewById(R.id.action_bar_title_back);
        titletext = (TextView) viewTitleBar.findViewById(R.id.action_bar_title_text);
        titletext.setText(this.getTitle());
        actionBar.setCustomView(viewTitleBar, lp);
        backimage.setVisibility(View.GONE);
        actionBar.setIcon(R.drawable.actionbar_title_back);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!this.getTitle().equals("转发动态") ||
                !this.getTitle().equals("评论信息") ||
                !this.getTitle().equals("全部动态") ||
                !this.getTitle().equals("发布动态")) {
            menu.add("abc")
                    .setIcon(R.drawable.actionbar_right)
                    .setShowAsAction(
                            MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        this.mymenu = menu;
        return true;
    }

    /**
     * 获取图片,并附加到视图上
     *
     * @param imageUrl
     * @param onEndListener
     */
    public void executeImage(String imageUrl, HttpGroup.OnEndListener onEndListener) {
        HttpGroup httpGroup = getHttpGroupaAsynPool();
        HttpSetting httpSetting = new HttpSetting();
        httpSetting.setFinalUrl(imageUrl);
        httpSetting.setType(HttpGroupSetting.TYPE_IMAGE);
        httpSetting.setPriority(HttpGroupSetting.PRIORITY_IMAGE);
        httpSetting.setCacheMode(HttpSetting.CACHE_MODE_ONLY_CACHE);
        httpSetting.setListener(onEndListener);
        httpGroup.add(httpSetting);
    }

    /**
     * 显示弹出大图
     *
     * @param view       窗口view
     * @param bigImgPath 大图路径
     */
    public void showPop(View view, String bigImgPath) {
        View popView = this.getLayoutInflater().inflate(
                R.layout.layout_popwindwos, null);
        popView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWin.dismiss();
            }
        });
        popWin = new PopupWindow(popView, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        // 需要设置一下此参数，点击外边可消失
        popWin.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        popWin.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popWin.setFocusable(true);
        popWin.setTouchable(true);
        popWin.showAtLocation(view, Gravity.CENTER, 0, 0);
        ImageView imageview = (ImageView) popView.findViewById(R.id.pop_img);
        ImageLoader.getInstance().displayImage(
                bigImgPath,
                imageview, AppContext.getInstance().mNoCacheOnDiscImageloadoptions);
//        showDetailImage1(bigImgPath, imageview, false, true);
    }

    /**
     * 显示详细页面人员头像
     * facePath 头像图片地址
     * targetView 图片显示元素
     */
    public GlobalImageCache.BitmapDigest showDetailImage1(String facePath, final ImageView targetView, boolean isrefresh, boolean isfirst) {
        final WeakReference<ImageView> weakImageView = new WeakReference<ImageView>(targetView);
        HandlerRecycleBitmapDrawable localHandlerRecycleBitmapDrawable = new HandlerRecycleBitmapDrawable(null, this);
        targetView.setImageDrawable(localHandlerRecycleBitmapDrawable);
        GlobalImageCache.BitmapDigest localBitmapDigest = new GlobalImageCache.BitmapDigest(facePath);
        localBitmapDigest.setWidth(targetView.getWidth());
        localBitmapDigest.setHeight(targetView.getHeight());
        if (isfirst)
            localBitmapDigest.setLarge(true);
        Bitmap localBitmap = InflateUtil.loadImageWithCache(localBitmapDigest);
        if (localBitmap == null) {
            InflateUtil.loadImageWithUrl(getHttpGroupaAsynPool(),
                    localBitmapDigest, isrefresh, new InflateUtil.ImageLoadListener() {
                        @Override
                        public void onError(GlobalImageCache.BitmapDigest paramBitmapDigest) {

                        }

                        @Override
                        public void onProgress(GlobalImageCache.BitmapDigest paramBitmapDigest, int paramInt1, int paramInt2) {

                        }

                        @Override
                        public void onStart(GlobalImageCache.BitmapDigest paramBitmapDigest) {

                        }

                        @Override
                        public void onSuccess(GlobalImageCache.BitmapDigest paramBitmapDigest, Bitmap paramAnonymousBitmap) {
                            if (weakImageView != null && weakImageView.get() != null) {
                                ImageView targetIv = weakImageView.get();
                                targetIv.setImageBitmap(paramAnonymousBitmap);
                                mAttacher = new PhotoViewAttacher(targetIv);
                                targetIv.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {
                                                                    if (popWin != null) {
                                                                        popWin.dismiss();
                                                                    }
                                                                }
                                                            }
                                );
                                mAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                                    @Override
                                    public void onViewTap(View view, float x, float y) {
                                        if (popWin != null) {
                                            popWin.dismiss();
                                        }
                                    }
                                });
                                mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                                    @Override
                                    public void onPhotoTap(View view, float x, float y) {
                                        if (popWin != null) {
                                            popWin.dismiss();
                                        }
                                    }
                                });
                            }
                        }
                    }
            );
        } else {
            targetView.setImageBitmap(localBitmap);
        }
        return localBitmapDigest;
    }


    /**
     * 显示详细页面人员头像
     * facePath 头像图片地址
     * targetView 图片显示元素
     */
    public GlobalImageCache.BitmapDigest showDetailImage(String facePath, final ImageView targetView, boolean isrefresh) {
        final WeakReference<ImageView> weakImageView = new WeakReference<ImageView>(targetView);
        HandlerRecycleBitmapDrawable localHandlerRecycleBitmapDrawable = new HandlerRecycleBitmapDrawable(null, this);
        targetView.setImageDrawable(localHandlerRecycleBitmapDrawable);
        GlobalImageCache.BitmapDigest localBitmapDigest = new GlobalImageCache.BitmapDigest(facePath);
        localBitmapDigest.setWidth(targetView.getWidth());
        localBitmapDigest.setHeight(targetView.getHeight());
        Bitmap localBitmap = InflateUtil.loadImageWithCache(localBitmapDigest);
        if (localBitmap == null) {
            HandlerRecycleBitmapDrawable localHandlerRecycleBitmapDrawable2 = (HandlerRecycleBitmapDrawable) targetView.getDrawable();
            localHandlerRecycleBitmapDrawable2.setBitmap(null);
            localHandlerRecycleBitmapDrawable.invalidateSelf();
            InflateUtil.loadImageWithUrl(getHttpGroupaAsynPool(), localBitmapDigest, isrefresh, new InflateUtil.ImageLoadListener() {
                public void onError(GlobalImageCache.BitmapDigest paramAnonymousBitmapDigest) {
                }

                public void onProgress(GlobalImageCache.BitmapDigest paramAnonymousBitmapDigest, int paramAnonymousInt1, int paramAnonymousInt2) {
                }

                public void onStart(GlobalImageCache.BitmapDigest paramAnonymousBitmapDigest) {
                }

                public void onSuccess(GlobalImageCache.BitmapDigest paramAnonymousBitmapDigest, Bitmap paramAnonymousBitmap) {
                    if (weakImageView != null && weakImageView.get() != null) {
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
        return localBitmapDigest;
    }

    private class PhotoTapListener implements PhotoViewAttacher.OnPhotoTapListener {

        @Override
        public void onPhotoTap(View view, float x, float y) {
            popWin.dismiss();
        }
    }


    Map<String, int[]> imageSizes = new HashMap<String, int[]>();
    /**
     * 左右滑动显示大图
     */
    public void showPop2(View view, final ArrayList<String> imageurls, final int postion, final Context context) {
        View popView = this.getLayoutInflater().inflate(
                R.layout.layout_popwindwos2, null);
        popWin = new PopupWindow(popView, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        View pop_bg = popView.findViewById(R.id.pop_bg);
        pop_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWin.dismiss();
            }
        });
        // 需要设置一下此参数，点击外边可消失
        popWin.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        popWin.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popWin.setFocusable(true);
        popWin.setTouchable(true);
        popWin.showAtLocation(view, Gravity.CENTER, 0, 0);
        final HackyViewPager mViewPager = (HackyViewPager) popView.findViewById(R.id.pop_img);
        ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
        for (int i = 0; i < imageurls.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageViews.add(imageView);
        }
        final ViewGroup.LayoutParams mParams = mViewPager.getLayoutParams();
        WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        final int screenWidth = wm.getDefaultDisplay().getWidth();
        mParams.width = screenWidth;
        mParams.height = screenWidth;

        ImageAdapter mImageAdapter = new ImageAdapter(imageViews, imageurls, this);
        mImageAdapter.setCallback(new ImageAdapter.ImageAdapterCallback() {
            @Override
            public void onLoadingComplete(String url, Bitmap bitmap) {
                int curPosition = mViewPager.getCurrentItem();
                imageSizes.put(url, new int[]{bitmap.getWidth(), bitmap.getHeight()});
                int[] imageSize = imageSizes.get(imageurls.get(curPosition));
                if(imageSize != null){
                    int imgWidth = imageSize[0];
                    int imgHeight = imageSize[1];

                    float scale = (float)screenWidth/(float)imgWidth;

                    mParams.width = screenWidth;
                    mParams.height = (int) (scale*imgHeight);
                    mViewPager.setLayoutParams(mParams);
                }
            }
        });
        mViewPager.setAdapter(mImageAdapter);
        mViewPager.setCurrentItem(postion);
    }

    public View showPop3(
            View view, final ArrayList<String> imageurls,
            final int postion, final Context context, final showPop3Listener listener, boolean needDelBtn){
        View popView = this.getLayoutInflater().inflate(
                R.layout.layout_popwindow3, null);
        popWin = new PopupWindow(popView, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        // 需要设置一下此参数，点击外边可消失
        popWin.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        popWin.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popWin.setFocusable(true);
        popWin.setTouchable(true);
        popWin.showAtLocation(view, Gravity.CENTER, 0, 0);
        final HackyViewPager mViewPager = (HackyViewPager) popView.findViewById(R.id.pop_img);

        if(needDelBtn){
            LinearLayout layout_photo_del = (LinearLayout) popView.findViewById(R.id.layout_photo_del);
            layout_photo_del.setVisibility(View.VISIBLE);
            layout_photo_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onClick(3, mViewPager.getCurrentItem());
                        popWin.dismiss();
                    }
                }
            });
        }

        View.OnClickListener mOclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.layout_photo_forward){
                    if(listener != null){
                        listener.onClick(0, mViewPager.getCurrentItem());
                    }
                }else if(view.getId() == R.id.layout_photo_comment){
                    if(listener != null){
                        listener.onClick(1, mViewPager.getCurrentItem());
                    }
                }else if(view.getId() == R.id.tv_photo_detail){
                    if(listener != null){
                        listener.onClick(2, mViewPager.getCurrentItem());
                    }
                }else if(view.getId() == R.id.layout_photo_btn){

                }else if(view.getId() == R.id.pop_bg){
                    popWin.dismiss();
                }
            }
        };

        View pop_bg = popView.findViewById(R.id.pop_bg);
        View layout_photo_forward = popView.findViewById(R.id.layout_photo_forward);
        View layout_photo_comment = popView.findViewById(R.id.layout_photo_comment);
        View tv_photo_detail = popView.findViewById(R.id.tv_photo_detail);
        View layout_photo_btn = popView.findViewById(R.id.layout_photo_btn);

        pop_bg.setOnClickListener(mOclickListener);
        layout_photo_forward.setOnClickListener(mOclickListener);
        layout_photo_comment.setOnClickListener(mOclickListener);
        tv_photo_detail.setOnClickListener(mOclickListener);
        layout_photo_btn.setOnClickListener(mOclickListener);

        ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
        for (int i = 0; i < imageurls.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageViews.add(imageView);
        }

        final ViewGroup.LayoutParams mParams = mViewPager.getLayoutParams();
        WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        final int screenWidth = wm.getDefaultDisplay().getWidth();
        mParams.width = screenWidth;
        mParams.height = screenWidth;

        ImageAdapter mImageAdapter = new ImageAdapter(imageViews, imageurls, this);
        mImageAdapter.setCallback(new ImageAdapter.ImageAdapterCallback() {
            @Override
            public void onLoadingComplete(String url, Bitmap bitmap) {
//                android.util.Log.i(TAG, "url:"+url);
                int curPosition = mViewPager.getCurrentItem();
                imageSizes.put(url, new int[]{bitmap.getWidth(), bitmap.getHeight()});
                int[] imageSize = imageSizes.get(imageurls.get(curPosition));
                if(imageSize != null){
                    int imgWidth = imageSize[0];
                    int imgHeight = imageSize[1];
//                    android.util.Log.i(TAG, "imgWidth:"+imgWidth+" imgHeight:"+imgHeight);


                    float scale = (float)screenWidth/(float)imgWidth;

                    mParams.width = screenWidth;
                    mParams.height = (int) (scale*imgHeight);
//                    android.util.Log.i(TAG, "paramsWidth:"+mParams.width+" paramsHeight:"+mParams.height);
                    mViewPager.setLayoutParams(mParams);
                }
            }
        });
        mViewPager.setAdapter(mImageAdapter);
        mViewPager.setCurrentItem(postion);

        return popView;
    }

    public View showPop3(
            View view, final ArrayList<String> imageurls, final ArrayList<String> commentNums,
            final int postion, final Context context, final showPop3Listener listener, boolean needDelBtn){
        View popView = showPop3(view, imageurls, postion, context, listener, needDelBtn);
        final HackyViewPager mViewPager = (HackyViewPager) popView.findViewById(R.id.pop_img);
        final TextView iv_comment_text = (TextView) popView.findViewById(R.id.iv_comment_text);
        iv_comment_text.setText("评论("+commentNums.get(postion)+")");
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                iv_comment_text.setText("评论("+commentNums.get(position)+")");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return popView;
    }

    public void showPop4(View view, final ArrayList<String> imageurls, final ArrayList<String> names, final int postion, final Context context) {
        View popView = this.getLayoutInflater().inflate(
                R.layout.layout_popwindwos2, null);
        final TextView tv_name = (TextView) popView.findViewById(R.id.tv_name);
        tv_name.setVisibility(View.VISIBLE);
        tv_name.setText(names.get(postion));
        popWin = new PopupWindow(popView, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        View pop_bg = popView.findViewById(R.id.pop_bg);
        pop_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWin.dismiss();
            }
        });
        // 需要设置一下此参数，点击外边可消失
        popWin.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        popWin.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popWin.setFocusable(true);
        popWin.setTouchable(true);
        popWin.showAtLocation(view, Gravity.CENTER, 0, 0);
        final HackyViewPager mViewPager = (HackyViewPager) popView.findViewById(R.id.pop_img);
        ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
        for (int i = 0; i < imageurls.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageViews.add(imageView);
        }
        final ViewGroup.LayoutParams mParams = mViewPager.getLayoutParams();
        WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        final int screenWidth = wm.getDefaultDisplay().getWidth();
        mParams.width = screenWidth;
        mParams.height = screenWidth;

        ImageAdapter mImageAdapter = new ImageAdapter(imageViews, imageurls, this);
        mImageAdapter.setCallback(new ImageAdapter.ImageAdapterCallback() {
            @Override
            public void onLoadingComplete(String url, Bitmap bitmap) {
                int curPosition = mViewPager.getCurrentItem();
                imageSizes.put(url, new int[]{bitmap.getWidth(), bitmap.getHeight()});
                int[] imageSize = imageSizes.get(imageurls.get(curPosition));
                if(imageSize != null){
                    int imgWidth = imageSize[0];
                    int imgHeight = imageSize[1];

                    float scale = (float)screenWidth/(float)imgWidth;

                    mParams.width = screenWidth;
                    mParams.height = (int) (scale*imgHeight);
                    mViewPager.setLayoutParams(mParams);
                }
            }
        });

        mViewPager.setAdapter(mImageAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tv_name.setText(names.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(postion);
    }

    public static interface showPop3Listener{
        /**
         *
         * @param type（0:转发；1:评论 2:详情 3:删除照片）
         */
        public void onClick(int type, int position);
    }

}
