package com.sxsram.ssm.exception;

public class CommitFormatInvalidException extends Exception {

	private String message;

	public CommitFormatInvalidException() {

	}

	public CommitFormatInvalidException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
