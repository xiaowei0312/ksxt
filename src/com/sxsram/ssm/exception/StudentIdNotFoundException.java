package com.sxsram.ssm.exception;

public class StudentIdNotFoundException extends Exception {
	private String message;

	public StudentIdNotFoundException() {
		super();
	}

	public StudentIdNotFoundException(String message) {
		this.message = "无法找到对应id的学生：" + message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
