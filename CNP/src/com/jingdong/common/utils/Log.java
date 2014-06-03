package com.jingdong.common.utils;

import android.os.Environment;

import com.hyrt.cnp.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Log {
	
	public static boolean D;
	public static boolean E;
	public static boolean I;
	public static final boolean IS_TEST;
	public static final String LOG_FILE;
	public static boolean V;
	public static boolean W;
	private static RandomAccessFile accessFile;
	private static boolean printLog;
	
	static class Configuration{
		static boolean printLog = BuildConfig.DEBUG;
		static boolean debugLog = true;
		static boolean viewLog;
		static boolean infoLog;
		static boolean warnLog;
		static boolean errorLog = true;
		static boolean testMode;
	}

	static {
		boolean flag = false;
		printLog = Configuration.printLog;
		boolean flag1;
		boolean flag2;
		boolean flag3;
		boolean flag4;
		if (printLog)
			flag1 = Configuration.debugLog;
		else
			flag1 = flag;
		D = flag1;
		if (printLog)
			flag2 = Configuration.viewLog;
		else
			flag2 = flag;
		V = flag2;
		if (printLog)
			flag3 = Configuration.infoLog;
		else
			flag3 = flag;
		I = flag3;
		if (printLog)
			flag4 = Configuration.warnLog;
		else
			flag4 = flag;
		W = flag4;
		if (printLog)
			flag = Configuration.errorLog;
		E = flag;
		LOG_FILE = (new StringBuilder())
				.append(Environment.getExternalStorageDirectory())
				.append("/jd_log.txt").toString();
		IS_TEST = Configuration.testMode;
		if (IS_TEST) {
			File file = new File(LOG_FILE);
			if (file.exists())
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public Log() {
	}

	public static void d(String s, String s1) {
		if (printLog) {
			android.util.Log.d(s, s1);
			saveLog((new StringBuilder(String.valueOf(s))).append(":")
					.append(s1).toString());
		}
	}

	public static void d(String s, String s1, Throwable throwable) {
		if (printLog) {
			android.util.Log.d(s, s1, throwable);
			saveLog((new StringBuilder(String.valueOf(s))).append(":")
					.append(s1).append(throwable.getMessage()).toString());
		}
	}

	public static void e(String s, String s1) {
		if (printLog) {
			android.util.Log.e(s, s1);
			saveLog((new StringBuilder(String.valueOf(s))).append(":")
					.append(s1).toString());
		}
	}

	public static void e(String s, String s1, Throwable throwable) {
		if (printLog) {
			android.util.Log.e(s, s1, throwable);
			saveLog((new StringBuilder(String.valueOf(s))).append(":")
					.append(s1).append(throwable.getMessage()).toString());
		}
	}

	public static void i(String s, String s1) {
		if (printLog) {
			android.util.Log.i(s, s1);
			saveLog((new StringBuilder(String.valueOf(s))).append(":")
					.append(s1).toString());
		}
	}

	public static void i(String s, String s1, Throwable throwable) {
		if (printLog) {
			android.util.Log.i(s, s1, throwable);
			saveLog((new StringBuilder(String.valueOf(s))).append(":")
					.append(s1).append(throwable.getMessage()).toString());
		}
	}

	private static void saveLog(String s) {
			/*try {
				if (accessFile == null)
					accessFile = new RandomAccessFile(LOG_FILE, "rw");
				long l = accessFile.length();
				accessFile.seek(l);
				accessFile.writeBytes((new StringBuilder(String.valueOf(FormatUtils.formatDate(new Date())))).append(":").toString());
				accessFile.writeBytes(s);
				accessFile.writeBytes("\r\n");
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		
	}

	public static void v(String s, String s1) {
		if (printLog) {
			android.util.Log.v(s, s1);
			saveLog((new StringBuilder(String.valueOf(s))).append(":")
					.append(s1).toString());
		}
	}

	public static void v(String s, String s1, Throwable throwable) {
		if (printLog) {
			android.util.Log.v(s, s1, throwable);
			saveLog((new StringBuilder(String.valueOf(s))).append(":")
					.append(s1).append(throwable.getMessage()).toString());
		}
	}

	public static void w(String s, String s1) {
		if (printLog) {
			android.util.Log.w(s, s1);
			saveLog((new StringBuilder(String.valueOf(s))).append(":")
					.append(s1).toString());
		}
	}

	public static void w(String s, String s1, Throwable throwable) {
		if (printLog) {
			android.util.Log.w(s, s1, throwable);
			saveLog((new StringBuilder(String.valueOf(s))).append(":")
					.append(s1).append(throwable.getMessage()).toString());
		}
	}

	public static void w(String s, Throwable throwable) {
		if (printLog) {
			android.util.Log.w(s, throwable);
			saveLog((new StringBuilder(String.valueOf(s))).append(":")
					.append(throwable.getMessage()).toString());
		}
	}
}
