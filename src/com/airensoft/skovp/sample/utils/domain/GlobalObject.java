/**
 * 
 */
package com.airensoft.skovp.sample.utils.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Hyun Jun Jang
 * 
 */
public class GlobalObject
{
	private final static String	PROPERTIES_NAME = "ovpsample.properties";
	private static GlobalObject	instance;
	private String				currentPath;
	private String				rootPath;
	private Properties			properties;

	private GlobalObject()
	{
		currentPath = "";
		properties = null;
	}

	public static GlobalObject getInstance()
	{
		// DCL
		if(instance == null)
		{
			synchronized(GlobalObject.class)
			{
				if(instance == null)
				{
					instance = new GlobalObject();
				}
			}
		}

		return instance;
	}

	public String getCurrentPath()
	{
		return currentPath;
	}

	public void setCurrentPath(String currentPath)
	{
		this.currentPath = currentPath;
	}
	
	public String getProperty(String key)
	{
		return getProperty(key, "");
	}

	public String getProperty(String key, String defaultValue)
	{
		if(currentPath.equals("") == true)
		{
			return "";
		}
		
		if(properties == null)
		{
			loadProperties();
		}
		
		String value;
		
		value = properties.getProperty(key);
		
		if(value == null)	
		{
			return defaultValue;
		}
		
		return value;
	}
	
	public String getRootPath()
	{
		return rootPath;
	}

	public void setRootPath(String rootPath)
	{
		this.rootPath = rootPath;
	}

	private void loadProperties()
	{
		properties = new Properties();
		
		FileInputStream fis;
		try
		{
			fis = new FileInputStream(getCurrentPath() + File.separator + "WEB-INF" + File.separator + "properties" + File.separator + PROPERTIES_NAME);
			
			properties.load(fis);
			
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
