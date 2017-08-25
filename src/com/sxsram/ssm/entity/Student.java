package com.sxsram.ssm.entity;

import org.apache.jasper.tagplugins.jstl.core.If;

public class Student extends BaseEntity {
	/*
	 * name varchar(50) not null, gender int, idcard varchar(19), stuNo
	 * varchar(20), gradeNo int, -- 2014级 majorId int, -- 院校/专业 trainingClassId
	 * int, -- 实训班级 --> 实训方向 bsDirId int, -- 毕业设计方向 bsTitleId int, -- 毕设选题
	 * bsInnerTeacher varchar(50), -- 校内指导老师 bsOuterTeacher varchar(50), --
	 * 校外指导老师 password varchar(50), -- 修改后的密码，缺省为null
	 */

	private String name;
	private Integer gender;
	private String idCard;
	private String stuNo;
	private Integer gradeNo;
	private String classNo;
	private String phone;
	private Integer majorId;
	private Integer trainingClassId;
	// private Integer bsDirId;
	private Integer bsTitleId;
	private String bsInnerTeacher;
	private String bsOuterTeacher;
	private String password;
	private CollageMajor major;
	private TrainingClass trainingClass;
	// private TrainingDir bsDir;
	private BsTitle bsTitle;

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNO) {
		this.classNo = classNO;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public void setGender(String str) {
		str = str.trim();
		if (str.equals("男") || str.equals("male") || str.equals("m"))
			this.gender = 1;
		else
			this.gender = 0;
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

	public Integer getGradeNo() {
		return gradeNo;
	}

	public void setGradeNo(Integer gradeNo) {
		this.gradeNo = gradeNo;
	}

	public Integer getMajorId() {
		if (major != null)
			return major.getId();
		return majorId;
	}

	public void setMajorId(Integer majorId) {
		this.majorId = majorId;
	}

	public Integer getTrainingClassId() {
		if (trainingClass != null)
			return trainingClass.getId();
		return trainingClassId;
	}

	public void setTrainingClassId(Integer trainingClassId) {
		this.trainingClassId = trainingClassId;
	}

	public Integer getBsTitleId() {
		if (bsTitle != null)
			return bsTitle.getId();
		return bsTitleId;
	}

	public void setBsTitleId(Integer bsTitleId) {
		this.bsTitleId = bsTitleId;
	}

	public String getBsInnerTeacher() {
		return bsInnerTeacher;
	}

	public void setBsInnerTeacher(String bsInnerTeacher) {
		this.bsInnerTeacher = bsInnerTeacher;
	}

	public String getBsOuterTeacher() {
		return bsOuterTeacher;
	}

	public void setBsOuterTeacher(String bsOuterTeacher) {
		this.bsOuterTeacher = bsOuterTeacher;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CollageMajor getMajor() {
		return major;
	}

	public void setMajor(CollageMajor major) {
		this.major = major;
	}

	public TrainingClass getTrainingClass() {
		return trainingClass;
	}

	public void setTrainingClass(TrainingClass trainingClass) {
		this.trainingClass = trainingClass;
	}

	public BsTitle getBsTitle() {
		return bsTitle;
	}

	public void setBsTitle(BsTitle bsTitle) {
		this.bsTitle = bsTitle;
	}
}
