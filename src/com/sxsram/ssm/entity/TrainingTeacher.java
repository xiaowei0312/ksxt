package com.sxsram.ssm.entity;

import java.util.Date;

public class TrainingTeacher extends BaseEntity {
	private String name;
	private Date beginTime;
	private Integer trainingId;
	private TrainingDir trainingDir;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
