package com.jingdong.common.http;

public interface HttpSettingParams {

	public abstract void putJsonParam(String s, Object obj);

	public abstract void putMapParams(String s, String s1);

	public abstract void setReady(boolean flag);
}
