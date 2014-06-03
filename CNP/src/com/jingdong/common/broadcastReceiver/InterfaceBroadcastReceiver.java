package com.jingdong.common.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jingdong.common.frame.BaseActivity;
import com.jingdong.common.utils.Log;
import java.util.Date;
import java.util.Iterator;

import net.oschina.app.AppContext;

/**
 * 接收跳转请求
 * 
 * @author yepeng
 * 
 */
public class InterfaceBroadcastReceiver extends BroadcastReceiver {
	public static final String ACTION = "com.hyrt.androidpad.interfaceBroadcastReceiver";
	public static final int MODULE_ID_HOME = 1;
	public static final int MODULE_ID_MESSAGE = 2;
	public static final int MODULE_ID_MESSAGE_LIST = 6;
	public static final int MODULE_ID_PRODUCT = 4;
	public static final int MODULE_ID_SEARCH = 3;
	public static final int MODULE_ID_TOKEN = 5;
	public static final String MODULE_NAME_HOME = "home";
	public static final String MODULE_NAME_MESSAGE = "message";
	public static final String MODULE_NAME_PRODUCT = "product";
	public static final String MODULE_NAME_SEARCH = "search";
	public static final String MODULE_NAME_TOKEN = "token";
	public static String function;
	public static String keyword;
	public static String subunionId;
	public static Date timestamp;
	public static String type;
	public static String unionId;
	public static String usid;

	public static Intent createIntent(int paramInt) {
		Intent localIntent = new Intent(ACTION);
		Bundle localBundle = new Bundle();
		localBundle.putInt("moduleId", paramInt);
		localIntent.putExtras(localBundle);
		return localIntent;
	}

	public void onReceive(Context paramContext, Intent paramIntent) {
		if (Log.D)
			Log.d("Temp", "InterfaceBroadcastReceiver onReceive() -->> ");
		Command localCommand = new Command(paramIntent.getIntExtra("moduleId",0),paramIntent.getExtras());
		if (localCommand.getModuleId() == 0)
			return;
		Intent localIntent = new Intent(paramContext, AppContext.getInstance()
				.getBaseActivity().getClass());
		BaseActivity localIMainActivity = AppContext.getInstance().getBaseActivity();
		if (localIMainActivity == null) {
			if (Log.D)
				Log.d("Temp",
						"InterfaceBroadcastReceiver onReceive() pad -->> not run");
			localIntent.putExtras(localCommand.getBundle());
			localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			paramContext.startActivity(localIntent);
		} else {
			localIMainActivity.toTargetActivity(localCommand);
		}
	}

	/**
	 * 请求命令
	 * 
	 * @author yepeng
	 * 
	 */
	public static class Command {

		private int moduleId = 0;

		private Bundle outBundle = new Bundle();

		private Command(int paramInt, Bundle paramBundle) {
			this.moduleId = paramInt;
			this.outBundle = paramBundle;
		}

		public Bundle getBundle() {
			Bundle localBundle = new Bundle();
			localBundle.putInt("moduleId", this.moduleId);
			outBundleToBundle(outBundle, localBundle);
			return localBundle;
		}

		public static void outBundleToBundle(Bundle paramBundle1,
				Bundle paramBundle2) {
			Iterator localIterator = paramBundle1.keySet().iterator();
			while (true) {
				if (!localIterator.hasNext())
					return;
				String str = (String) localIterator.next();
				Object localObject = paramBundle1.get(str);
				if ((localObject instanceof String))
					paramBundle2.putString(str, (String) localObject);
				else if ((localObject instanceof Integer))
					paramBundle2
							.putInt(str, ((Integer) localObject).intValue());
				else if ((localObject instanceof Long))
					paramBundle2.putLong(str, ((Long) localObject).longValue());
			}
		}

		public int getModuleId() {
			return this.moduleId;
		}

	}
}