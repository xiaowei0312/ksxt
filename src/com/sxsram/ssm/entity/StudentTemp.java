package com.sxsram.ssm.entity;

public class StudentTemp extends Student {
	private String colName;
	private String depName;
	private String majorName;
	private String trainingClassName;
	private String bsDirName;
	private String bsTitleName;

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getTrainingClassName() {
		return trainingClassName;
	}

	public void setTrainingClassName(String trainingClassName) {
		this.trainingClassName = trainingClassName;
	}

	public String getBsDirName() {
		return bsDirName;
	}

	public void setBsDirName(String bsDirName) {
		this.bsDirName = bsDirName;
	}

	public String getBsTitleName() {
		return bsTitleName;
	}

	public void setBsTitleName(String bsTitleName) {
		this.bsTitleName = bsTitleName;
	}
}
