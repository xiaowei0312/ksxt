package com.sxsram.ssm.util;

public class WechatNews {
	private String title;
	private String desc;
	private String picUrl;
	private String url;
	
	

	public WechatNews() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WechatNews(String title, String desc, String picUrl, String url) {
		super();
		this.title = title;
		this.desc = desc;
		this.picUrl = picUrl;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
