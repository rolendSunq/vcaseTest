package com.airensoft.skovp.sample.utils.ovpconnector;

import org.apache.log4j.Logger;

import com.airensoft.skovp.sample.utils.http.HttpConnectorResponsable;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class OMSConnectorResponse extends HttpConnectorResponsable
{
	private Logger			logger	= Logger.getLogger(getClass());

	private boolean			succeed;
	private JsonElement		rootDataElement;
	private String			requestId;
	private String			resultCode;
	
	public OMSConnectorResponse()
	{
		super();
		
		this.succeed = false;
		this.rootDataElement = null;
		this.requestId = null;
		this.resultCode = null;
	}
	
	@Override
	public void parse(String jsonString)
	{
		JsonParser parser = new JsonParser();
		JsonElement element = null;
		
		if(jsonString != null)
		{
			try
			{
				element = parser.parse(jsonString);
				setRootObject(element.getAsJsonObject());

				// 실제 데이터 파싱
				this.rootDataElement = getRootObject().get("result");
				
				// requestId 파싱
				if(getRootObject().get("request_id") != null)
				{
					this.requestId = getRootObject().get("request_id").getAsString();
				}
				
				// resultCode 파싱
				if(getRootObject().get("result_code") != null)
				{
					this.resultCode = getRootObject().get("result_code").getAsString();
				}
				
				// errorMessage 파싱
				if(getRootObject().get("message") != null)
				{
					setErrorMessage(getRootObject().get("message").getAsString());
				}
				
				if((this.resultCode != null) && (this.resultCode.startsWith("S") == false))
				{
					this.succeed = false;
				}
				else
				{
					this.succeed = true;
				}
			}
			catch(Exception e)
			{
				logger.error("parse failed");
				e.printStackTrace();
				
				setRootObject(null);
				this.rootDataElement = null;
				this.succeed = false;
			}	
		}
		else
		{
			this.succeed = false;
		}
	}
	
	/**
	 * <pre>
	 * HttpResponse의 데이터를 초기화한다
	 * </pre>
	 */
	@Override
	public void clear()
	{
		this.succeed = false;
		this.rootDataElement = null;
		this.requestId = null;
		this.resultCode = null;
		
		super.clear();
	}
	
	public boolean isSucceed()
	{
		return succeed;
	}

	public void setSucceed(boolean succeed)
	{
		this.succeed = succeed;
	}

	public JsonElement getRootDataElement()
	{
		return rootDataElement;
	}
	
	public void setRootDataElement(JsonElement rootDataElement)
	{
		this.rootDataElement = rootDataElement;
	}

	public String getRequestId()
	{
		return requestId;
	}

	public void setRequestId(String requestId)
	{
		this.requestId = requestId;
	}

	public String getResultCode()
	{
		return resultCode;
	}

	public void setResultCode(String resultCode)
	{
		this.resultCode = resultCode;
	}
}
