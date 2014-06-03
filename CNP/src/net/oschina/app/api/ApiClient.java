package net.oschina.app.api;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import net.oschina.app.AppContext;
import net.oschina.app.AppException;
import net.oschina.app.bean.Base;
import net.oschina.app.bean.Entity;
import net.oschina.app.bean.Result;
import net.oschina.app.bean.URLs;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jingdong.common.http.HttpGroup;
import com.jingdong.common.utils.IOUtil;

/**
 * API客户端接口：用于访问网络数据
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class ApiClient {

	public static final String UTF_8 = "UTF-8";

	private final static int TIMEOUT_CONNECTION = 20000;
	private final static int TIMEOUT_SOCKET = 20000;
	private final static int RETRY_TIME = 3;

	private static HttpClient getHttpClient() {
		HttpClient httpClient = new HttpClient();
		// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		// 设置 默认的超时重试处理策略
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置 连接超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(TIMEOUT_CONNECTION);
		// 设置 读数据超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setSoTimeout(TIMEOUT_SOCKET);
		// 设置 字符集
		httpClient.getParams().setContentCharset(UTF_8);
		return httpClient;
	}

	private static GetMethod getHttpGet(String url, String cookie,
			String userAgent) {
		GetMethod httpGet = null;
		try {
			httpGet = new GetMethod(url);
			// 设置 请求超时时间
			httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
			httpGet.setRequestHeader("Host", URLs.HOST);
			httpGet.setRequestHeader("Connection", "Keep-Alive");
			httpGet.setRequestHeader("Cookie", cookie);
			httpGet.setRequestHeader("User-Agent", userAgent);
			httpGet.setRequestHeader("Accept", "*/*");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpGet;
	}

	private static PostMethod getHttpPost(String url, String cookie,
			String userAgent) {
		PostMethod httpPost = new PostMethod(url);
		// 设置 请求超时时间
		httpPost.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpPost.setRequestHeader("Host", URLs.HOST);
		httpPost.setRequestHeader("Connection", "Keep-Alive");
		httpPost.setRequestHeader("Cookie", cookie);
		httpPost.setRequestHeader("User-Agent", userAgent);
		return httpPost;
	}

    /**
     * 获取网络图片流
     *
     * @param url
     * @return
     */
    public static void getImageFileByNetUrl(String url,File file,HttpGroup.StopController stopcontroller) throws AppException {
        HttpClient httpClient = null;
        GetMethod httpGet = null;
        int time = 0;
        InputStream inStream = null;
        FileOutputStream outStream = null;
        do {
            try {
                httpClient = getHttpClient();
                httpGet = getHttpGet(url, null, null);
                int statusCode = httpClient.executeMethod(httpGet);
                if (statusCode != HttpStatus.SC_OK) {
                    throw AppException.http(statusCode);
                }
                outStream = new FileOutputStream(file);
                inStream = httpGet.getResponseBodyAsStream();
                try {
                    IOUtil.readAsFile(inStream,outStream,null,stopcontroller);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw AppException.io(e);
                }
                break;
            } catch (HttpException e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
// 发生致命的异常，可能是协议不对或者返回的内容有问题
                e.printStackTrace();
                throw AppException.http(e);
            } catch (IOException e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
// 发生网络异常
                e.printStackTrace();
                throw AppException.network(e);
            } finally {
// 释放连接
                try {
                    if(inStream != null){
                        inStream.close();
                    }

                } catch (IOException e) {
                    throw AppException.network(e);
                }
                httpGet.releaseConnection();
                httpClient = null;
            }
        } while (time < RETRY_TIME);
    }




    private static String _MakeURL(String p_url, Map<String, Object> params) {
		StringBuilder url = new StringBuilder(p_url);
		if (url.indexOf("?") < 0)
			url.append('?');

		for (String name : params.keySet()) {
			url.append('&');
			url.append(name);
			url.append('=');
			url.append(String.valueOf(params.get(name)));
			// 不做URLEncoder处理
			// url.append(URLEncoder.encode(String.valueOf(params.get(name)),
			// UTF_8));
		}
		return url.toString().replace("?&", "?");
	}

	/**
	 * get请求URL
	 * 
	 * @param url
	 * @throws AppException
	 */
	private static InputStream http_get(HttpGroup.HttpRequest httpRquest, String url)
			throws AppException {
		HttpClient httpClient = null;
		GetMethod httpGet = null;
		String responseBody = "";
		int time = 0;
		do {
			try {
				httpClient = getHttpClient();
				httpGet = getHttpGet(url, "", "");
				int statusCode = httpClient.executeMethod(httpGet);
				if (statusCode != HttpStatus.SC_OK) {
					throw AppException.http(statusCode);
				}
				responseBody = httpGet.getResponseBodyAsString();
				// System.out.println("XMLDATA=====>"+responseBody);
				break;
			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} finally {
				// 释放连接
				httpGet.releaseConnection();
				httpClient = null;
			}
		} while (time < RETRY_TIME);

		responseBody = responseBody.replaceAll("\\p{Cntrl}", "");
		if (responseBody.contains("result")
				&& responseBody.contains("errorCode")) {
			try {
				Result res = Result.parse(new ByteArrayInputStream(responseBody
						.getBytes()));
				if (res.getErrorCode() == 0) {
					//httpRquest.Logout();
					//httpRquest.getUnLoginHandler().sendEmptyMessage(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ByteArrayInputStream(responseBody.getBytes());
	}

	/**
	 * 公用post方法
	 * 
	 * @param url
	 * @param params
	 * @param files
	 * @throws AppException
	 */
	private static InputStream _post(String url,
			Map<String, Object> params, Map<String, File> files)
			throws AppException {
		HttpClient httpClient = null;
		PostMethod httpPost = null;

		// post表单参数处理
		int length = (params == null ? 0 : params.size())
				+ (files == null ? 0 : files.size());
		Part[] parts = new Part[length];
		int i = 0;
		if (params != null)
			for (String name : params.keySet()) {
				parts[i++] = new StringPart(name, String.valueOf(params
						.get(name)), UTF_8);
			}
		if (files != null)
			for (String file : files.keySet()) {
				try {
					parts[i++] = new FilePart(file, files.get(file));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

		String responseBody = "";
		int time = 0;
		do {
			try {
				httpClient = getHttpClient();
				httpPost = getHttpPost(url, "", "");
				httpPost.setRequestEntity(new MultipartRequestEntity(parts,
						httpPost.getParams()));
				int statusCode = httpClient.executeMethod(httpPost);
				if (statusCode != HttpStatus.SC_OK) {
					throw AppException.http(statusCode);
				} else if (statusCode == HttpStatus.SC_OK) {
					Cookie[] cookies = httpClient.getState().getCookies();
					String tmpcookies = "";
					for (Cookie ck : cookies) {
						tmpcookies += ck.toString() + ";";
					}
				}
				responseBody = httpPost.getResponseBodyAsString();
				// System.out.println("XMLDATA=====>"+responseBody);
				break;
			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} finally {
				// 释放连接
				httpPost.releaseConnection();
				httpClient = null;
			}
		} while (time < RETRY_TIME);

		responseBody = responseBody.replaceAll("\\p{Cntrl}", "");
		if (responseBody.contains("result")
				&& responseBody.contains("errorCode")) {
			try {
				Result res = Result.parse(new ByteArrayInputStream(responseBody
						.getBytes()));
				if (res.getErrorCode() == 0) {
					/*appContext.Logout();
					appContext.getUnLoginHandler().sendEmptyMessage(1);*/
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ByteArrayInputStream(responseBody.getBytes());
	}


	/**
	 * 获取数据的通用方法
	 *
	 * @return
	 * @throws AppException
	 */
	public static Entity getDataByAutoType(HttpGroup.HttpRequest httpRequest) throws AppException {
		try {
			Class entityClass = httpRequest.getHttpSetting().getCurrentEntity();
			if (!httpRequest.getHttpSetting().isPost() && entityClass != null) {
				return new Gson().fromJson(
						new JsonReader(
								new InputStreamReader(http_get(
										httpRequest,
										_MakeURL(httpRequest.getHttpSetting().getFinalUrl(),
												httpRequest.getHttpSetting().getParamMaps())),
										Base.UTF8)), entityClass);
			} else if (entityClass != null) {
				return new Gson().fromJson(
						new JsonReader(new InputStreamReader(_post(httpRequest.getHttpSetting().getFinalUrl(),
								(Map<String, Object>) httpRequest.getHttpSetting()
										.getParamMaps(), null), Base.UTF8)),
						null);
			}

		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
		return null;
	}

}
