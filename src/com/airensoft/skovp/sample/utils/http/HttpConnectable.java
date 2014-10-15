package com.airensoft.skovp.sample.utils.http;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;

public class HttpConnectable implements HttpConnector
{
	private Logger					logger						= Logger.getLogger(getClass());

	private String					method;
	private String					host;
	private String					charset;
	private ContentType				contentType;
	private String					accept;
	private String					requestBody;
	private List<NameValuePair>		paramList;
	private StringBuilder			uriParams;
	private File					uploadFile;
	private String					uploadFileName;
	private String					uploadFileDescription;

	private HttpResponse			httpResponse;
	private HttpRequestBase			httpBaseMethod;
	private HttpClient				httpClient;
	private HttpParams				httpParams;
	private HttpConnectorResponse	result;
	
	private String					requestKey;

	public HttpConnectable()
	{
		this(null);
	}

	public HttpConnectable(String host)
	{
		this.method = "GET";
		this.host = host;
		this.charset = CHAR_ENCODING;
		this.contentType = CONTENT_TYPE;
		this.accept = null;
		this.requestBody = null;
		this.paramList = new ArrayList<NameValuePair>();
		this.uriParams = null;
		this.uploadFile = null;
		this.uploadFileName = null;
		this.uploadFileDescription = null;
		
		this.httpResponse = null;
		this.httpBaseMethod = null;
		this.httpClient = new DefaultHttpClient();
		this.httpParams = null;
		this.result = null;
		
		this.requestKey = null;
	}
	
	@Override
	public void addURIParam(String path)
	{
		if(this.uriParams == null)
		{
			this.uriParams = new StringBuilder();
		}
		
		if(path != null)
		{
			this.uriParams.append("/");
			this.uriParams.append(path);
		}
	}
	
	@Override
	public void addParam(String key, String value)
	{
		if((key != null) && (value != null))
		{
			this.paramList.add(new BasicNameValuePair(key, value));
		}
	}
	
	@Override
	public void addParam(String key, Integer value)
	{
		if((key != null) && (value != null))
		{
			this.paramList.add(new BasicNameValuePair(key, Integer.toString(value)));
		}
	}
	
	@Override
	public void addParam(String key, Boolean value)
	{
		if((key != null) && (value != null))
		{
			this.paramList.add(new BasicNameValuePair(key, Boolean.toString(value)));
		}
	}
	
	@Override
	public void addParam(String key, Double value)
	{
		if((key != null) && (value != null))
		{
			this.paramList.add(new BasicNameValuePair(key, Double.toString(value)));
		}
	}
	
	@Override
	public void setUploadFile(File file, String fileName, String fileDescription)
	{
		this.uploadFile = file;
		this.uploadFileName = fileName;
		this.uploadFileDescription = fileDescription;
	}

	@Override
	public String getRequestBody()
	{
		return this.requestBody;
	}
	
	@Override
	public void setRequestBody(String body)
	{
		this.requestBody = body;
	}

	@Override
	public void setCharset(String charset)
	{
		this.charset = charset;
	}
	
	@Override
	public void setContentType(ContentType contentType)
	{
		this.contentType = contentType;
	}

	@Override
	public void setAccept(String accept)
	{
		this.accept = accept;
	}

	@Override
	public String getHost()
	{
		return this.host;
	}
	
	@Override
	public void setHost(String host)
	{
		this.host = host;
	}
	
	@Override
	public void setMethod(String method)
	{
		this.method = method;
	}
	
	@Override
	public String getMethod()
	{
		return this.method;
	}
	
	@Override
	public void setResult(HttpConnectorResponse result)
	{
		this.result = result;
	}
	
	@Override
	public HttpConnectorResponse getResult()
	{
		return this.result;
	}

	@Override
	public void clearParam()
	{
		paramList.clear();
		this.uriParams = null;
		this.uploadFile = null;
		this.uploadFileName = null;
		this.uploadFileDescription = null;
	}
	
	@Override
	public void clear()
	{
		this.method = "GET";
		this.host = null;
		this.charset = CHAR_ENCODING;
		this.contentType = CONTENT_TYPE;
		this.accept = null;
		this.requestBody = null;
		
		this.httpResponse = null;
		this.httpBaseMethod = null;
		this.httpClient = new DefaultHttpClient();
		this.httpParams = null;
		this.result = null;
		
		this.requestKey = null;
		
		clearParam();
	}

	@Override
	public String getUri(boolean withQueryStr)
	{
		StringBuilder uri = new StringBuilder();

		if(this.host != null)
		{
			uri.append(this.host);
		}

		if(this.uriParams != null)
		{
			uri.append(this.uriParams);
		}
		
		if(withQueryStr && !this.paramList.isEmpty())
		{
			uri.append(getQueryString());
		}

		return uri.toString();
	}
	
	@Override
	public String getUri()
	{
		return getUri(false);
	}
	
	@Override
	public String getQueryString()
	{
		StringBuilder queryStr = new StringBuilder();
		
		queryStr.append("?");
		queryStr.append(URLEncodedUtils.format(this.paramList, CHAR_ENCODING));
		
		return queryStr.toString();
	}

	@Override
	public HttpConnectorResponse request() throws URISyntaxException, ClientProtocolException, IOException, SocketTimeoutException, HttpHostConnectException, NoHttpResponseException
	{
		if(this.method == "GET")
		{
			this.httpBaseMethod = makeGetMethod();
		}
		else if(this.method == "POST")
		{
			this.httpBaseMethod = makePostMethod();
		}
		else if(this.method == "PUT")
		{
			this.httpBaseMethod = makePutMethod();
		}
		else if(this.method == "DELETE")
		{
			this.httpBaseMethod = makeDeleteMethod();
		}
		else
		{
			this.httpBaseMethod = null;
		}
		
		if(this.httpBaseMethod != null)
		{
			this.httpParams = this.httpClient.getParams();
			
			// httpClient settings
			HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_TIME_OUT);
			HttpConnectionParams.setSoTimeout(httpParams, HTTP_TIME_OUT);
			
			// https settings
			if(getHost().startsWith("https"))
			{
				SSLSocketFactory socketFactory = buildSSLSocketFactory();
				Scheme https = new Scheme("https", 443, socketFactory);
				SchemeRegistry schemeRegistry = httpClient.getConnectionManager().getSchemeRegistry();
				
				schemeRegistry.register(https);
			}
			
			// header settings 
			if(this.accept != null)
			{
				this.httpBaseMethod.setHeader("Accept", this.accept);
			}
			this.httpBaseMethod.setHeader("charset", this.charset);
			this.httpBaseMethod.addHeader("Connection", "close");
			
			// execute
			try
			{
				this.requestKey = System.currentTimeMillis() + String.format("%03d", new Random().nextInt(1000));
				this.httpResponse = httpClient.execute(httpBaseMethod);
				parseHttpResponse();
			}
			catch(HttpHostConnectException e)
			{
				if(this.result == null)
				{
					this.result = new HttpConnectorResponsable();
				}
				this.result.setErrorMessage("HttpHostConnectException");
				
				throw e;
			}
			catch(SocketTimeoutException e)
			{
				if(this.result == null)
				{
					this.result = new HttpConnectorResponsable();
				}
				this.result.setErrorMessage("SocketTimeoutException");
				
				throw e;
			}
			catch(NoHttpResponseException e)
			{
				if(this.result == null)
				{
					this.result = new HttpConnectorResponsable();
				}
				this.result.setErrorMessage("NoHttpResponseException");
				
				throw e;
			}
			finally
			{
				this.httpBaseMethod.abort();
				// release conneciton
				this.httpBaseMethod.releaseConnection();
				this.httpClient.getConnectionManager().shutdown();				
			}
		}

		return this.result;
	}
	
	protected void parseHttpResponse()
	{
		if(this.result == null)
		{
			this.result = new HttpConnectorResponsable();			
		}
		
		// logging
		logger.info(toString());
		
		result.setRequestKey(this.requestKey);
		
		// parse
		try
		{
			this.result.parse(this.httpResponse);
		}
		catch(IllegalStateException e)
		{
			this.result.setErrorMessage("IllegalStateException");
			e.printStackTrace();
		}
		catch(IOException e)
		{
			this.result.setErrorMessage("IOException");
			e.printStackTrace();
		}
		catch(RuntimeException e)
		{
			this.result.setErrorMessage("RuntimeExceiption");
			e.printStackTrace();
		}
	}

	protected HttpGet makeGetMethod()
	{
		HttpGet httpGet = new HttpGet(getUri(true));
		
		return httpGet;
	}

	protected HttpPost makePostMethod() throws URISyntaxException, UnsupportedEncodingException
	{
		URI uri;
		HttpPost httpPost = new HttpPost();
		HttpEntity httpEntity;

		if(this.uploadFile != null)
		{
			MultipartEntity multipartEntity = new MultipartEntity();
			FileBody fileBody;
			
			uri = new URI(getUri(true));
			
			multipartEntity.addPart("fileDescription", new StringBody((this.uploadFileDescription != null) ? this.uploadFileDescription : ""));
			multipartEntity.addPart("fileName", new StringBody((this.uploadFileName != null) ? this.uploadFileName : this.uploadFile.getName()));

			if(this.uploadFileName != null)
			{
				fileBody = new FileBody(this.uploadFile, this.uploadFileName, "application/octect-stream", "UTF-8");
			}
			else
			{
				fileBody = new FileBody(this.uploadFile, "application/octect-stream");
			}

			multipartEntity.addPart("attachment", fileBody);
			
			httpEntity = multipartEntity;
		}
		else if(this.requestBody != null)
		{
			uri = new URI(getUri(true));
			httpEntity = new StringEntity(this.requestBody, this.contentType);
		}
		else
		{
			uri = new URI(getUri());
			httpEntity = new UrlEncodedFormEntity(this.paramList, CHAR_ENCODING);
		}

		httpPost.setURI(uri);
		httpPost.setEntity(httpEntity);
		
		return httpPost;
	}

	protected HttpPut makePutMethod() throws URISyntaxException, UnsupportedEncodingException
	{
		URI uri;
		HttpPut httpPut = new HttpPut();
		HttpEntity httpEntity;
		
		if(this.requestBody != null)
		{
			uri = new URI(getUri(true));
			httpEntity = new StringEntity(this.requestBody, this.contentType);
		}
		else
		{
			uri = new URI(getUri());
			httpEntity = new UrlEncodedFormEntity(this.paramList, CHAR_ENCODING);
		}
		
		httpPut.setURI(uri);
		httpPut.setEntity(httpEntity);
		
		return httpPut;
	}

	protected HttpDelete makeDeleteMethod()
	{
		HttpDelete httpDelete = new HttpDelete(getUri(true));
		
		return httpDelete;
	}
	
	private SSLSocketFactory buildSSLSocketFactory()
	{
		TrustStrategy trustStrategy = new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
			{
				return true;
			}
		};
		
		SSLSocketFactory socketFactory = null;
		
		try
		{
			socketFactory = new SSLSocketFactory(trustStrategy, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		}
		catch(KeyManagementException e)
		{
			e.printStackTrace();
		}
		catch(UnrecoverableKeyException e)
		{
			e.printStackTrace();
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch(KeyStoreException e)
		{
			e.printStackTrace();
		}
		
		return socketFactory;
	}
	
	@Override
	public String toString()
	{
		StringBuilder logString = new StringBuilder();
		
		logString.append("======== HTTP Request ( ").append(this.requestKey).append(" ) ========\n");
		logString.append("URL => ").append(getUri()).append("\n");
		logString.append("Method => ").append(this.method).append("\n");
		logString.append("Timeout => ").append(HTTP_TIME_OUT).append("\n");
		logString.append("RequestBody => ").append(this.requestBody).append("\n");
		logString.append("Parameters => ");
		for(NameValuePair pair : this.paramList)
		{
			logString.append("[ ").append(pair.getName()).append(": ")
					.append(pair.getValue()).append(" ]");
		}
		
		return logString.toString();
	}
}
