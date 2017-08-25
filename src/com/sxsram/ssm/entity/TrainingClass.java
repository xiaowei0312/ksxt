package com.sxsram.ssm.entity;

import java.util.Date;

public class TrainingClass extends BaseEntity {
	private String name;
	private Date beginTime;
	private Date endTime;
	private Integer status;
	private Integer trainingId;
	private TrainingDir trainingDir;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Integer getTrainingId() {
		if (trainingDir != null)
			return trainingDir.getId();
		return trainingId;
	}

	public void setTrainingId(Integer trainingId) {
		this.trainingId = trainingId;
	}

	public TrainingDir getTrainingDir() {
		return trainingDir;
	}

	public void setTrainingDir(TrainingDir trainingDir) {
		this.trainingDir = trainingDir;
	}
}
