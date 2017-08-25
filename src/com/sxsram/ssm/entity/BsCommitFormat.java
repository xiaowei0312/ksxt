package com.sxsram.ssm.entity;

public class BsCommitFormat extends BaseEntity {
//	format varchar(50),
//	eg varchar(100),			-- 格式示例
	private String format;
	private String eg;
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getEg() {
		return eg;
	}
	public void setEg(String eg) {
		this.eg = eg;
	}
	
	
}
