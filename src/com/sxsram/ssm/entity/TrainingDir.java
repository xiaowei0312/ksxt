package com.sxsram.ssm.entity;

public class TrainingDir extends BaseEntity{
	private String name;
	private Integer sequence;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
}
