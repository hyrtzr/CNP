package net.oschina.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Process;
import android.os.storage.StorageManager;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.model.UserDetail;
import com.hyrt.cnp.base.account.utils.LogHelper;
import com.jingdong.common.frame.BaseActivity;
import com.jingdong.common.frame.IMyActivity;
import com.jingdong.common.utils.DPIUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;

import roboguice.RoboGuice;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class AppContext extends Application {

	private static AppContext app;
	private BaseActivity baseActivity;
    public int uuid = -1;
    public UserDetail mUserDetail;
    public ArrayList<BaseActivity> activities = new ArrayList<BaseActivity>();

    public DisplayImageOptions mImageloaderoptions;
    public DisplayImageOptions mNoCacheOnDiscImageloadoptions;

    public static final int RESULT_FOR_PHONE_ALBUM = 102;

	public static AppContext getInstance() {
		return app;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		Process.setThreadPriority(-20);
		//获取屏幕密度
		DPIUtil.setDensity(getResources().getDisplayMetrics().density);
        initImageLoader();
	}

    public void initImageLoader(){
//        File cacheDir = StorageUtils.getCacheDirectory(this);
//        UnlimitedDiscCache ud = new UnlimitedDiscCache(cacheDir);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .discCacheFileCount(100)
                .build();
        ImageLoader.getInstance().init(config);

        mImageloaderoptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.cnp_default_img)
                .showImageOnFail(R.drawable.cnp_default_img)
                .showImageForEmptyUri(R.drawable.cnp_default_img)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();

        mNoCacheOnDiscImageloadoptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.cnp_default_img)
                .showImageOnFail(R.drawable.cnp_default_img)
                .showImageForEmptyUri(R.drawable.cnp_default_img)
                .cacheInMemory(true)
                .build();
    }

	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

    public void runOnUIThread(Runnable uiTask){
        if(baseActivity != null)
            baseActivity.runOnUiThread(uiTask);
    }

	public void setBaseActivity(BaseActivity baseActivity) {
		this.baseActivity = baseActivity;
	}

	public BaseActivity getBaseActivity() {
		return baseActivity;
	}

    public void finishActivitys(){
        for(int i=0,j=activities.size(); i<j; i++){
            activities.get(i).finish();
        }
    }
}
