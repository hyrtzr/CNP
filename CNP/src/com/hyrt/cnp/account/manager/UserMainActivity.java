package com.hyrt.cnp.account.manager;

import net.oschina.app.AppContext;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.annotation.TargetApi;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hyrt.cnp.R;
import com.hyrt.cnp.account.LoginActivity;
import com.hyrt.cnp.account.request.UserDetailRequest;
import com.hyrt.cnp.account.requestListener.UserDetailRequestListener;
import com.hyrt.cnp.base.account.AccountUtils;
import com.hyrt.cnp.base.account.model.UserDetail;
import com.hyrt.cnp.base.account.utils.FaceUtils;
import com.hyrt.cnp.base.account.utils.ScreenAdaptHelper;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserMainActivity extends BaseActivity {

    private UserDetail.UserDetailModel userDetail;

//    private WeakReference<ImageView> weakImageView;

    public final static int UPDATE_FACE = 1;

//    private GlobalImageCache.BitmapDigest localBitmapDigest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        AppContext.getInstance().activities.add(this);
        findViewById(R.id.user_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userDetail != null) {
                    Intent intent = new Intent(UserMainActivity.this, UserInfoActivity.class);
                    intent.putExtra("vo", userDetail);
                    intent.putExtra("mybabayinfo", true);
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.update_face).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userDetail!=null){
                    Intent intent = new Intent(UserMainActivity.this,UserFaceActivity.class);
                    intent.putExtra("vo",userDetail);
                    startActivityForResult(intent, UPDATE_FACE);
                }
            }
        });
        findViewById(R.id.update_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userDetail!=null){
                    Intent intent = new Intent(UserMainActivity.this,UserPasswordActivity.class);
                    intent.putExtra("vo",userDetail);
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.userout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View popView = getLayoutInflater().inflate(
                        R.layout.layout_sign_out_dialog, null);
                final PopupWindow mPopWin = new PopupWindow(popView, ScreenAdaptHelper.getInstance(UserMainActivity.this).dipToPx(260),
                        ScreenAdaptHelper.getInstance(UserMainActivity.this).dipToPx(130));
                mPopWin.setOutsideTouchable(true);
                mPopWin.setBackgroundDrawable(new BitmapDrawable());
                mPopWin.setFocusable(true);
                mPopWin.setTouchable(true);
                mPopWin.showAtLocation(UserMainActivity.this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                TextView tv_sign_out_cancle = (TextView) popView.findViewById(R.id.tv_sign_out_cancle);
                TextView tv_sign_out_submit = (TextView) popView.findViewById(R.id.tv_sign_out_submit);
                tv_sign_out_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPopWin.dismiss();
                    }
                });
                tv_sign_out_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppContext.getInstance().mUserDetail = null;
                        AppContext.getInstance().uuid = -1;
                        AccountManager.get(UserMainActivity.this).removeAccount(
                                AccountUtils.getAccount(UserMainActivity.this),
                                new AccountManagerCallback<Boolean>() {
                                    @Override
                                    public void run(AccountManagerFuture<Boolean> booleanAccountManagerFuture) {
                                        Intent intent = new Intent();
                                        intent.setClass(UserMainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, new AsyncQueryHandler(new ContentResolver(getApplication()) {
                                    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                                    @Override
                                    public String[] getStreamTypes(Uri url, String mimeTypeFilter) {
                                        return super.getStreamTypes(url, mimeTypeFilter);
                                    }
                                }) {
                                    @Override
                                    protected Handler createHandler(Looper looper) {
                                        return super.createHandler(looper);
                                    }
                                }
                        );
                    }
                });


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppContext.getInstance().setBaseActivity(this);
        initData();
    }

    /**
     * 获取数据
     */
    private void initData() {
        UserDetailRequest userDetailRequest = new UserDetailRequest(this);
        UserDetailRequestListener userDetailRequestListener = new UserDetailRequestListener(this);
        spiceManager.execute(userDetailRequest, userDetailRequest.createCacheKey(),
                1000, userDetailRequestListener.start());
    }

    /**
     * 更新页面元素数据
     * @param userDetail
     */
    public void updateUI(UserDetail.UserDetailModel userDetail) {
        this.userDetail = userDetail;
        String facePath = FaceUtils.getAvatar(userDetail.getData().getUser_id(),FaceUtils.FACE_BIG);
        ImageView imageView = (ImageView)findViewById(R.id.user_face);
        ImageLoader.getInstance().displayImage(facePath+"?time="+userDetail.getData().getLogo(), imageView, AppContext.getInstance().mNoCacheOnDiscImageloadoptions);
//        weakImageView = new WeakReference<ImageView>(imageView);
        ((TextView) findViewById(R.id.class_tv)).setText(userDetail.getData().getNurseryName());
        ((TextView) findViewById(R.id.name_tv)).setText(userDetail.getData().getRenname());
       /* HandlerRecycleBitmapDrawable localHandlerRecycleBitmapDrawable = new HandlerRecycleBitmapDrawable(null, this);
        imageView.setImageDrawable(localHandlerRecycleBitmapDrawable);
        localBitmapDigest = new GlobalImageCache.BitmapDigest(facePath+"?time="+userDetail.getData().getLogo());
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
     * 如果头像更新的话，则需要回收原来的图片，并从缓存中清除 localBitmapDigest
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


}
