package com.airensoft.skovp.sample.utils.http;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ContentType;

public interface HttpConnector
{
	public static final String		CHAR_ENCODING	= "UTF-8";
	public static final ContentType	CONTENT_TYPE	= ContentType.create("application/json", CHAR_ENCODING); 
	public static final int			HTTP_TIME_OUT	= 5000;

	public void addURIParam(String path);
	public void addParam(String key, String value);
	public void addParam(String key, Integer value);
	public void addParam(String key, Boolean value);
	public void addParam(String key, Double value);
	public void setUploadFile(File file, String fileName, String fileDescription);
	public String getRequestBody();
	public void setRequestBody(String body);
	public void setCharset(String charset);
	public void setContentType(ContentType contentType);
	public void setAccept(String accept);
	public String getHost();
	public void setHost(String host);
	public void setMethod(String method);
	public String getMethod();
	public void setResult(HttpConnectorResponse result);
	public HttpConnectorResponse getResult();
	public void clearParam();
	public void clear();
	public String getUri(boolean withQueryStr);
	public String getUri();
	public String getQueryString();
	public HttpConnectorResponse request() throws URISyntaxException, ClientProtocolException, IOException, SocketTimeoutException, HttpHostConnectException, NoHttpResponseException;
}
