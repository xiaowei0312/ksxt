package com.sxsram.ssm.entity;

public class Permission extends BaseEntity {
	private String name;
	private Operation operation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
}
