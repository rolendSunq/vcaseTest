package com.airensoft.skovp.sample.utils.ovpconnector;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;

import com.airensoft.skovp.sample.utils.http.HttpConnectable;
import com.airensoft.skovp.sample.utils.parser.JsonParser;

public class OMSConnector extends HttpConnectable
{
	private JsonParser		jsonParser;
	private String				accessToken;
	private String				protocol;
	HashMap<String, Object> 	paramData;
	
	public OMSConnector()
	{
		this(OMSConfig.getHost());
	}
	
	public OMSConnector(String host)
	{
		super(null);
		
		setHost(host);
		setResult(new OMSConnectorResponse());
		
		setAccessToken(OMSConfig.getAccessToken());
		
		this.protocol = "http";
		this.paramData = new HashMap<String, Object>();
		
		this.jsonParser = new JsonParser();
	}
	
	/**
	 * 컨텐츠 목록 조회
	 */
	public OMSConnectorResponse RequestContentList(String media_type, String file_type, String state, String search_type, String search, Integer search_start_date, Integer search_end_date, Integer page, Integer page_size, String sort, String order, boolean with_extra)
	{
		setProtocol("http");
		setMethod("GET");
		addURIParam(OMSConfig.OVP_CONTENT_LIST);
		
		addParam("media_type", media_type);
		addParam("file_type", file_type);
		addParam("state", state);
		addParam("search_type", search_type);
		addParam("search", search);
		addParam("search_start_date", search_start_date);
		addParam("search_end_date", search_end_date);
		addParam("page", page);
		addParam("page_size", page_size);
		addParam("sort", sort);
		addParam("order", order);
		addParam("with_extra", with_extra);
		
		return requestAPI();	
	}
	
	/**
	 * 컨텐츠 다운로드 URL 발급
	 */
	public OMSConnectorResponse RequestPulbishDownloadContent(Integer content_id)
	{
		setProtocol("http");
		setMethod("GET");
		addURIParam(OMSConfig.OVP_PUBLISH_DOWNLOAD_CONTENT +"/" + content_id);
		
		return requestAPI();	
	}
	
	/**
	 * 컨텐츠 스트리밍 URL 발급
	 */
	public OMSConnectorResponse RequestPulbishStreamingContent(Integer content_id, Integer allow_count, Integer allow_start_Date, Integer allow_end_date, String protocol, boolean encryption)
	{
		setProtocol("http");
		setMethod("GET");
		addURIParam(OMSConfig.OVP_PUBLISH_STREAMING_CONTENT +"/" + content_id);
		
		addParam("allow_count", allow_count);
		addParam("allow_start_Date", allow_start_Date);
		addParam("allow_end_date", allow_end_date);
		addParam("protocol", protocol);
		addParam("encryption", encryption);
		
		return requestAPI();	
	}
	
	
	/**
	 * 광고 조회
	 */
	public OMSConnectorResponse RequestPlayerAd(String player_id)
	{
		setProtocol("http");
		setMethod("GET");
		addURIParam(String.format(OMSConfig.OVP_PLAYER_AD, player_id));
		
		return requestAPI();	
	}
	

	@Override
	public void clear()
	{
		super.clear();
		
		setHost(OMSConfig.getHost());
		setResult(new OMSConnectorResponse());
		setAccessToken(OMSConfig.getAccessToken());
		
		this.protocol = "http";
		this.paramData = new HashMap<String, Object>();
	}

	@Override
	public void setHost(String host)
	{
		// 아래의 형식으로 맞춘다
		// http://dev.airensoft.com:8080
		int beginIndex;
		String protocol;
		
		if(host != null)
		{
			if(this.protocol != null)
			{
				protocol = this.protocol;
			}
			else
			{
				protocol = "http";
			}
			
			if(!host.startsWith(protocol))
			{
				beginIndex = host.indexOf("://");
				
				if(beginIndex != -1)
				{
					host = host.substring(beginIndex + 3);
				}
				
				host = protocol.concat("://").concat(host);
			}	
		}
		
		super.setHost(host);
	}
	
	private OMSConnectorResponse requestAPI()
	{
		return requestAPI(true);
	}
	
	private OMSConnectorResponse requestAPI(boolean prepare)
	{
		OMSConnectorResponse response = null;
		
		try
		{
			if(prepare)
			{
				prepareRequest();
			}
			
			response = (OMSConnectorResponse) request();
		}
		catch(ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch(URISyntaxException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return response;
	}
	
	private void prepareRequest()
	{
		if(getMethod().equals("GET") || getMethod().equals("DELETE"))
		{
			addParam("access_token", getAccessToken());
		}
		else
		{
			HashMap<String, Object> bodyData = new HashMap<String, Object>();
			
			this.paramData.put("access_token", getAccessToken());
			
			bodyData.put("params", this.paramData);
			
			setRequestBody(jsonParser.encode(bodyData));
		}
	}

	public String getAccessToken()
	{
		return accessToken;
	}
	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}
	public String getProtocol()
	{
		return protocol;
	}
	public void setProtocol(String protocol)
	{
		this.protocol = protocol;
		setHost(getHost());
	}
	
	
}
