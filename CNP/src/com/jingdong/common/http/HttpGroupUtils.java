package com.jingdong.common.http;

public class HttpGroupUtils
{
  public static HttpGroup getHttpGroupaAsynPool()
  {
    return getHttpGroupaAsynPool(HttpGroupSetting.PRIORITY_JSON);
  }

  public static HttpGroup getHttpGroupaAsynPool(int paramInt)
  {
    HttpGroupSetting localHttpGroupSetting = new HttpGroupSetting();
    localHttpGroupSetting.setType(paramInt);
    return getHttpGroupaAsynPool(localHttpGroupSetting);
  }

  public static HttpGroup getHttpGroupaAsynPool(HttpGroupSetting paramHttpGroupSetting)
  {
    return new HttpGroup.HttpGroupaAsynPool(paramHttpGroupSetting);
  }
}
