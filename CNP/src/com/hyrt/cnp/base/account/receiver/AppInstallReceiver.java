package com.hyrt.cnp.base.account.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

/**
 * Created by Zoe on 2014-05-22.
 */
public class AppInstallReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        PackageManager manager = context.getPackageManager();
        if(intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)){
            String packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "覆盖安装" + packageName, Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "安装成功" + packageName, Toast.LENGTH_LONG).show();
        }
    }
}
