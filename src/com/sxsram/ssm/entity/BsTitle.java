package com.sxsram.ssm.entity;

public class BsTitle extends BaseEntity {
	// name varchar(20), -- 选题名称
	// taskBookId int,
	private String name; // -- 选题名称
	private Integer taskBookId; // -- 对应任务书
	private Integer bsDirId;
	private BsTaskBook bsTaskBook;
	private TrainingDir bsDir;

	public Integer getBsDirId() {
		return bsDirId;
	}

	public void setBsDirId(Integer bsDirId) {
		this.bsDirId = bsDirId;
	}

	public TrainingDir getBsDir() {
		return bsDir;
	}

	public void setBsDir(TrainingDir bsDir) {
		this.bsDir = bsDir;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTaskBookId() {
		if (bsTaskBook != null)
			return bsTaskBook.getId();
		return taskBookId;
	}

	public void setTaskBookId(Integer taskBookId) {
		this.taskBookId = taskBookId;
	}

	public BsTaskBook getBsTaskBook() {
		return bsTaskBook;
	}

	public void setBsTaskBook(BsTaskBook bsTaskBook) {
		this.bsTaskBook = bsTaskBook;
	}

}
