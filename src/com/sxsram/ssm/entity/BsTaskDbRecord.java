package com.sxsram.ssm.entity;

import java.util.Date;

public class BsTaskDbRecord extends BaseEntity implements Comparable<BsTaskDbRecord>{
	// id int auto_increment,
	// stuTaskId int,
	// dbTime datetime, -- 答辩时间
	// dbQuestion text, -- 问题记录
	// status int default 0, -- 任务执行状态（0成功 -1失败）
	// statusPhase varchar(50), -- 任务状态描述
	// extra varchar(255), -- 额外描述
	// addTime datetime not null, -- 添加时间
	// lmtTime timestamp, -- 最后修改时间
	// foreign key(stuTaskId) references t_bs_task_stu(id),
	// primary key(id)
	private Integer stuTaskId;
	private Date dbTime;
	private String dbQuestion;
	private Integer status;
	private String statusPhase;
	private BsTaskStu task;

	public Integer getStuTaskId() {
		if(task != null)
			return task.getId();
		return stuTaskId;
	}

	public void setStuTaskId(Integer stuTaskId) {
		this.stuTaskId = stuTaskId;
	}

	public Date getDbTime() {
		return dbTime;
	}

	public void setDbTime(Date dbTime) {
		this.dbTime = dbTime;
	}

	public String getDbQuestion() {
		return dbQuestion;
	}

	public void setDbQuestion(String dbQuestion) {
		this.dbQuestion = dbQuestion;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusPhase() {
		return statusPhase;
	}

	public void setStatusPhase(String statusPhase) {
		this.statusPhase = statusPhase;
	}

	public BsTaskStu getTask() {
		return task;
	}

	public void setTask(BsTaskStu task) {
		this.task = task;
	}

	@Override
	public int compareTo(BsTaskDbRecord o) {
		if(!(o instanceof BsTaskDbRecord))
			return -1;
		return (int) (this.addTime.getTime() - o.addTime.getTime());
	}
}
