package com.sxsram.ssm.entity;

public class BsDbType extends BaseEntity{
	//name varchar(20),	-- 【开题预答辩、开题答辩、中期预答辩、中期答辩、。。。】
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
