package com.hyrt.cnp.base.account.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;

import com.hyrt.cnp.base.account.model.Update;
import com.hyrt.cnp.base.account.request.UpdateRequest;
import com.hyrt.cnp.base.account.requestListener.UpdateRequestListener;
import com.jingdong.common.frame.BaseActivity;

import net.oschina.app.AppContext;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Zoe on 2014-05-21.
 */
public class UpdateHepler {

    private AlertDialog dialog;
    private ProgressDialog mProgressDialog;

    private String downloadPath;
    private String localVersion;
    private String newVersion;
    private File downloadfile;
    private boolean downloadStoped = false;

    private Thread downloadThread;

    private Context context;

    private static final String TAG = "UpdateHepler";

    public UpdateHepler(Context context) {
        this.context = context;
    }

    /**
     * 检测更新
     */
    public void DetectUpdate(){
        PackageInfo packInfo = AppContext.getInstance().getPackageInfo();
        localVersion = packInfo.versionName;
        LogHelper.i(TAG, "localVersion:"+localVersion);

        UpdateRequest request = new UpdateRequest(context);
        UpdateRequestListener requestListener = new UpdateRequestListener((BaseActivity)context);
        requestListener.setListener(mRequestListener);
        ((BaseActivity)context).spiceManager.execute(request, requestListener);
    }

    private UpdateRequestListener.RequestListener mRequestListener = new UpdateRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Update update) {
            newVersion = update.getVersion();
            downloadPath = update.getDownload();
            LogHelper.i(TAG, "newVersion:"+newVersion+" downloadPath:"+downloadPath);
            if(newVersion != null && newVersion.length() > 0
                    && downloadPath != null && downloadPath.length() > 0
                    && !localVersion.equals(newVersion)){
                promptUpdate();
            }
        }

        @Override
        public void onRequestFailure() {

        }
    };

    private void promptUpdate(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("版本升级");
        builder.setMessage("检测到最新版本，请及时更新！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(dialog != null){
                    dialog.dismiss();
                    dialog = null;
                }
                downloadApk();
            }
        });
        builder.setNegativeButton("取消", null);
        dialog = builder.create();
        dialog.show();
    }


    /**
     * 下载apk
     */
    private void downloadApk(){
        downloadfile = FileUtils.createFile("cnp", "cnp_update.apk");
        if(downloadfile == null){
            AlertUtils.getInstance().showCenterToast(context, "未检测到可用SD卡，无法下载！");
            return;
        }

        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle("下载中");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "取消下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (downloadThread != null && !downloadThread.currentThread().isInterrupted()) {
                    LogHelper.i(TAG, "取消下载");
                    downloadStoped = true;
                    downloadThread.interrupt();
                    downloadThread = null;
                }
            }
        });
        mProgressDialog.setProgress(0);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        downloadThread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(downloadPath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    if(mProgressDialog != null){
                        mProgressDialog.setMax(connection.getContentLength());
                    }
                    InputStream is = connection.getInputStream();
                    FileOutputStream fos = new FileOutputStream(downloadfile);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len ;
                    int total=0;
                    while((len =bis.read(buffer))!=-1){
                        if(downloadStoped){
                            return;
                        }
                        fos.write(buffer, 0, len);
                        total += len;
//                        LogHelper.i(TAG, "下载进度："+total);
                        if(mProgressDialog != null){
                            mProgressDialog.setProgress(total);
                        }
                    }
                    bis.close();
                    fos.close();
                    is.close();
                    ((BaseActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            installApk();
                        }
                    });

                }catch (MalformedURLException e){
                    e.printStackTrace();
                    LogHelper.d(TAG, "downloadPath is unknown:"+downloadPath);
                } catch (IOException e) {
                    e.printStackTrace();
                    LogHelper.d(TAG, "downloadPath is unknown:"+downloadPath);
                }
            }
        };
        downloadThread.start();
    }

    private void installApk(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(downloadfile), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

}
