package com.airensoft.skovp.sample.vo;

public class UserVO
{
	private Integer device_idx;
	private String device_access_token;
	private String device_token;
	private Integer device_type; //1:android 	2:ios	3:window
	private String app_version;
	private boolean is_delete;
	private Integer last_access_date;
	private Integer reg_date;
	private Integer storage_group_idx;
	public Integer getDevice_idx()
	{
		return device_idx;
	}
	public void setDevice_idx(Integer device_idx)
	{
		this.device_idx = device_idx;
	}
	public String getDevice_access_token()
	{
		return device_access_token;
	}
	public void setDevice_access_token(String device_access_token)
	{
		this.device_access_token = device_access_token;
	}
	public String getDevice_token()
	{
		return device_token;
	}
	public void setDevice_token(String device_token)
	{
		this.device_token = device_token;
	}
	public Integer getDevice_type()
	{
		return device_type;
	}
	public void setDevice_type(Integer device_type)
	{
		this.device_type = device_type;
	}
	public String getApp_version()
	{
		return app_version;
	}
	public void setApp_version(String app_version)
	{
		this.app_version = app_version;
	}
	public boolean isIs_delete()
	{
		return is_delete;
	}
	public void setIs_delete(boolean is_delete)
	{
		this.is_delete = is_delete;
	}
	public Integer getLast_access_date()
	{
		return last_access_date;
	}
	public void setLast_access_date(Integer last_access_date)
	{
		this.last_access_date = last_access_date;
	}
	public Integer getReg_date()
	{
		return reg_date;
	}
	public void setReg_date(Integer reg_date)
	{
		this.reg_date = reg_date;
	}
	public Integer getStorage_group_idx()
	{
		return storage_group_idx;
	}
	public void setStorage_group_idx(Integer storage_group_idx)
	{
		this.storage_group_idx = storage_group_idx;
	}
	
}
