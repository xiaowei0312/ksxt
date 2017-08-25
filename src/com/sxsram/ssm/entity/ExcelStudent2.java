package com.sxsram.ssm.entity;

public class ExcelStudent2 {

	private String name;
	private String gender;
	private String idCard;
	private String stuNo;

	private String password;

	public ExcelStudent2(String name, String gender, String idCard, String stuNo) {
		super();
		this.name = name;
		this.gender = gender;
		this.idCard = idCard;
		this.stuNo = stuNo;
	}

	public ExcelStudent2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

}
