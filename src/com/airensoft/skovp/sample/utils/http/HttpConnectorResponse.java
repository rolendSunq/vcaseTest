package com.airensoft.skovp.sample.utils.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import com.google.gson.JsonObject;

public interface HttpConnectorResponse
{
	public static final String		CHARSET				= "UTF-8";
	
	public void parse(HttpResponse httpResponse) throws IllegalStateException, IOException, RuntimeException;
	public void parse(String jsonString);
	public void clear();
	public String getResponseBody();
	public void setResponseBody(String responseBody);
	public void setResponseBody(HttpResponse httpResponse) throws IllegalStateException, IOException, RuntimeException;
	public JsonObject getRootObject();
	public void setRootObject(JsonObject rootObject);
	public StatusLine getStatusLine();
	public void setStatusLine(StatusLine statusLine);
	public String getErrorMessage();
	public void setErrorMessage(String errorMessage);
	public String getRequestKey();
	public void setRequestKey(String requestKey);
}
