package com.sxsram.ssm.entity;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.sxsram.ssm.util.DateUtil;

public class BsTaskCommitRecord extends BaseEntity implements Comparable<BsTaskCommitRecord> {
	// id int auto_increment,
	// stuTaskId int,
	// commitTime datetime, -- 上传时间
	// commitFileName varchar(50), -- 上传文件名
	// commitFileNewName varchar(50), -- 服务器存储名
	// status int default 0, -- 任务执行状态（0成功 -1失败）
	// statusPhase varchar(50), -- 任务状态描述
	// extra varchar(255), -- 额外描述
	// addTime datetime not null, -- 添加时间
	// lmtTime timestamp, -- 最后修改时间
	// foreign key(stuTaskId) references t_bs_task_stu(id),
	// primary key(id)

	private Integer stuTaskId;
	private Date commitTime;
	private String commitFileName;
	private String commitFileNewName;
	private Integer status;
	private String statusPhase;
	private BsTaskStu task;

	public Integer getStuTaskId() {
		if (task != null)
			return task.getId();
		return stuTaskId;
	}

	public void setStuTaskId(Integer stuTaskId) {
		this.stuTaskId = stuTaskId;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public String getCommitFileName() {
		return commitFileName;
	}

	public void setCommitFileName(String commitFileName) {
		this.commitFileName = commitFileName;
	}

	public String getCommitFileNewName() {
		return commitFileNewName;
	}

	public void setCommitFileNewName(String commitFileNewName) {
		this.commitFileNewName = commitFileNewName;
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
	public int compareTo(BsTaskCommitRecord o) {
		if (!(o instanceof BsTaskCommitRecord))
			return -1;
		return (int) (this.addTime.getTime() - o.addTime.getTime());
	}
}
