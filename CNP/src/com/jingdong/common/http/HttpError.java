package com.jingdong.common.http;

/**
 * 错误实体类
 * @author yepeng
 *
 */
public class HttpError {

	public static final int EXCEPTION = 0;
	public static final String EXCEPTION_MESSAGE_ATTESTATION_RSA = "attestation RSA";
	public static final String EXCEPTION_MESSAGE_ATTESTATION_WIFI = "attestation WIFI";
	public static final String EXCEPTION_MESSAGE_NO_CACHE = "no cache";
	public static final String EXCEPTION_MESSAGE_NO_READY = "no ready";
	public static final int JSON_CODE = 3;
	public static final int RESPONSE_CODE = 2;
	public static final int TIME_OUT = 1;
	private int errorCode;
	private Throwable exception;
	private HttpResponse httpResponse;
	private int jsonCode;
	private String message;
	private boolean noRetry;
	private int responseCode;
	private int times;

	public HttpError() {
	}

	public HttpError(Throwable throwable) {
		errorCode = 0;
		exception = throwable;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorCodeStr() {
		String s = "UNKNOWN";

		switch (errorCode) {
		case 0:
			s = "EXCEPTION";
			break;
		case 1:
			s = "TIME_OUT";
			break;
		case 2:
			s = "RESPONSE_CODE";
			break;
		case 3:
			s = "JSON_CODE";
			break;
		}
		return s;
	}

	public Throwable getException() {
		return exception;
	}

	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

	public int getJsonCode() {
		return jsonCode;
	}

	public String getMessage() {
		return message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public int getTimes() {
		return times;
	}

	public boolean isNoRetry() {
		return noRetry;
	}

	public void setErrorCode(int i) {
		errorCode = i;
	}

	public void setException(Throwable throwable) {
		exception = throwable;
	}

	public void setHttpResponse(HttpResponse httpresponse) {
		httpResponse = httpresponse;
	}

	public void setJsonCode(int i) {
		jsonCode = i;
	}

	public void setMessage(String s) {
		message = s;
	}

	public void setNoRetry(boolean flag) {
		noRetry = flag;
	}

	public void setResponseCode(int i) {
		responseCode = i;
	}

	public void setTimes(int i) {
		times = i;
	}

	public String toString() {
		return (new StringBuilder("HttpError [errorCode="))
				.append(getErrorCodeStr()).append(", exception=")
				.append(exception).append(", jsonCode=").append(jsonCode)
				.append(", message=").append(message)
				.append(", responseCode=").append(responseCode)
				.append(", time=").append(times).append("]").toString();
	}

}
