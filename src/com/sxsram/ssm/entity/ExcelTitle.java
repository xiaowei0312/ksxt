package com.sxsram.ssm.entity;

public class ExcelTitle {
	private String seqNum;	//序号
	private String questionClass; // 试卷名称
	private String questionLevel; // ?
	private String questionType; // 类型
	private String questionTitle; // 题目
	private String questionItems; // 选项
	private String questionAnswer; // 答案
	private String questionScore; // 分值

	public ExcelTitle(String questionClass, String questionLevel, String questionType, String questionTitle,
			String questionItems, String questionAnswer, String questionScore,String seqNum) {
		super();
		this.questionClass = questionClass;
		this.questionLevel = questionLevel;
		this.questionType = questionType;
		this.questionTitle = questionTitle;
		this.questionItems = questionItems;
		this.questionAnswer = questionAnswer;
		this.questionScore = questionScore;
		this.seqNum = seqNum;
	}

	
	public ExcelTitle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getSeqNum() {
		return seqNum;
	}


	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}


	public String getQuestionClass() {
		return questionClass;
	}

	public void setQuestionClass(String questionClass) {
		this.questionClass = questionClass;
	}

	public String getQuestionLevel() {
		return questionLevel;
	}

	public void setQuestionLevel(String questionLevel) {
		this.questionLevel = questionLevel;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getQuestionItems() {
		return questionItems;
	}

	public void setQuestionItems(String questionItems) {
		this.questionItems = questionItems;
	}

	public String getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String getQuestionScore() {
		return questionScore;
	}

	public void setQuestionScore(String questionScore) {
		this.questionScore = questionScore;
	}
}