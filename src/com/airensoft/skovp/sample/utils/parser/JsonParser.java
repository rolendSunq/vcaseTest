package com.airensoft.skovp.sample.utils.parser;

import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class JsonParser
{
	private Gson gson;
	
	public JsonParser()
	{
		this(false);
	}
	
	public JsonParser(boolean serializeNulls)
	{
		gson = new Gson();
		
		if(serializeNulls)
		{
			gson = new GsonBuilder().serializeNulls().create();
		}
		else
		{
			gson = new Gson();
		}
	}
	
	public String encode(Object obj)
	{
		return gson.toJson(obj);
	}
	
	public String encode(String key, Object obj)
	{
		HashMap<String, Object> map;
		
		map = new HashMap<String, Object>();
		
		map.put(key, obj);
		
		return gson.toJson(map);
	}
	
	public <T> T decodeFromJSON(String json, Class<T> classOfT)
	{
		return gson.fromJson(json, classOfT);
	}
	
	public <T> T decodeFromJSON(String json, Type typeOfT)
	{
		return gson.fromJson(json, typeOfT);
	}
	
	public <T> T decodeFromJSON(JsonElement element, Class<T> classOfT)
	{
		return gson.fromJson(element, classOfT);
	}
	
	public <T> T decodeFromJSON(JsonElement element, Type typeOfT)
	{
		return gson.fromJson(element, typeOfT);
	}
}
