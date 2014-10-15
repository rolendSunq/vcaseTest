package com.airensoft.skovp.sample.utils.ovpconnector;

import com.airensoft.skovp.sample.utils.domain.GlobalObject;

public class OMSConfig
{
	// CONTENT
	public static final String	OVP_CONTENT_LIST			= "/content";
	
	// PUBLISH
	public static final String OVP_PUBLISH_DOWNLOAD_CONTENT =  "/publish/download/content";
	public static final String OVP_PUBLISH_STREAMING_CONTENT =  "/publish/streaming/content";
	
	// PLAYER 광고
	public static final String OVP_PLAYER_AD =  "/player/%s/ad";
	
	// FILE API URN
	public static String getAccessToken()
	{
		return GlobalObject.getInstance().getProperty("skovp.access_token", "");
	}
	
	public static String getHost()
	{
		return GlobalObject.getInstance().getProperty("skovp.domain", "");
	}
	
	public static int getPlayerId()
	{
		return Integer.parseInt(GlobalObject.getInstance().getProperty("skovp.playerid", ""));
	}
	public static int getJobProfileId()
	{
		return Integer.parseInt(GlobalObject.getInstance().getProperty("skovp.jobprofileid", ""));
	}
}


