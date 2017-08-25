package com.sxsram.ssm.entity;

import java.util.List;

public class BsTaskStu extends BaseEntity {
	// id int auto_increment,
	// studentId int,
	// taskId int,
	// status int default 0, -- 任务执行状态（0未完成, 1任务完成）
	// statusPhase varchar(50), -- 任务状态描述
	// extra varchar(255), -- 额外描述
	// addTime datetime not null, -- 添加时间
	// lmtTime timestamp, -- 最后修改时间
	// foreign key(taskId) references t_bs_task_admin(id),
	// primary key(id)
	private Integer studentId;
	private Integer taskId;
	private Integer status;
	private String statusPhase;
	private Student student;
	private BsTaskAdmin task;
	private List<BsTaskCommitRecord> commitRecordList;
	private List<BsTaskDbRecord> dbRecordList;

	public List<BsTaskCommitRecord> getCommitRecordList() {
		return commitRecordList;
	}

	public void setCommitRecordList(List<BsTaskCommitRecord> commitRecordList) {
		this.commitRecordList = commitRecordList;
	}

	public List<BsTaskDbRecord> getDbRecordList() {
		return dbRecordList;
	}

	public void setDbRecordList(List<BsTaskDbRecord> dbRecordList) {
		this.dbRecordList = dbRecordList;
	}

	public Integer getStudentId() {
		if (student != null)
			return student.getId();
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getTaskId() {
		if (task != null)
			return task.getId();
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
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

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public BsTaskAdmin getTask() {
		return task;
	}

	public void setTask(BsTaskAdmin task) {
		this.task = task;
	}

}
