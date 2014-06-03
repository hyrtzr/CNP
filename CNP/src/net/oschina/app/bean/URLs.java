package net.oschina.app.bean;

import java.io.Serializable;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import net.oschina.app.common.StringUtils;

/**
 * 接口URL实体类
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class URLs implements Serializable {
	
	public final static String HOST = "192.168.13.114:8080";//192.168.1.213  www.oschina.net 192.168.2.220:8080
	public final static String HTTP = "http://";
	public final static String HTTPS = "https://";
	public final static String URL_SPLITTER = "/";
	
	public final static String URL_API_HOST = HTTP + HOST + URL_SPLITTER;
	public final static String PRO_LIST = URL_API_HOST+"SpringMVC/rest/buildproj/findschemabyid/1";
	public final static String UPDATE_VERSION = URL_API_HOST+"MobileAppVersion.xml";
		
}
