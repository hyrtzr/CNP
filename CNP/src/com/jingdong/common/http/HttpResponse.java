package com.jingdong.common.http;

import java.io.File;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.util.Map;

import net.oschina.app.bean.Entity;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class HttpResponse {

	private int code;
	private byte inputData[];
	private long length;
	private File saveFile;
	private String shareImagePath;
	private String string;
	private String type;
	private JSONObject jsonObject;
	private Entity resultObject;
	
	public Entity getResultObject() {
		return resultObject;
	}

	public void setResultObject(Entity resultObject) {
		this.resultObject = resultObject;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public int getCode() {
		return code;
	}

	public byte[] getInputData() {
		return inputData;
	}

	public long getLength() {
		return length;
	}

	public File getSaveFile() {
		return saveFile;
	}

	public String getShareImagePath() {
		return shareImagePath;
	}

	public String getString() {
		return string;
	}

	public String getType() {
		return type;
	}

	public void setCode(int i) {
		code = i;
	}

	public void setInputData(byte abyte0[]) {
		inputData = abyte0;
	}

	public void setLength(long l) {
		length = l;
	}

	public void setSaveFile(File file) {
		saveFile = file;
	}

	public void setShareImagePath(String s) {
		shareImagePath = s;
	}

	public void setString(String s) {
		string = s;
	}

	public void setType(String s) {
		type = s;
	}
}
