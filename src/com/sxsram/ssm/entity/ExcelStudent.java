package com.sxsram.ssm.entity;

public class ExcelStudent {

	private String studentName;
	private String studentPwd;
	private String studentClassName;
	private String studentId;

	public ExcelStudent(String studentName, String studentPwd, String studentClassName, String studentId) {
		super();
		this.studentName = studentName;
		this.studentPwd = studentPwd;
		this.studentClassName = studentClassName;
		this.studentId = studentId;
	}

	public ExcelStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentPwd() {
		return studentPwd;
	}

	public void setStudentPwd(String studentPwd) {
		this.studentPwd = studentPwd;
	}

	public String getStudentClassName() {
		return studentClassName;
	}

	public void setStudentClassName(String studentClassName) {
		this.studentClassName = studentClassName;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

}
