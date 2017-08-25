package com.sxsram.ssm.entity;

import java.util.Date;
import java.util.List;

public class BsTaskAdmin extends BaseEntity {
	// id int auto_increment,
	// userId int not null, -- 任务分派人（系统管理员）
	// beginTime datetime not null, -- 任务开始时间
	// endTime datetime not null, -- 任务逾期时间
	// -- expireTime datetime not null, -- 任务失效时间
	// majorId int, -- 任务对应的目标（院校）
	// gradeNo int, -- 任务对应的目标（2014级）
	// status int default 0, -- 任务状态（0 is 已激活,1 is 已失效/已删除）
	// type int default 0, -- 任务类型（0 is 文件提交任务,1 is 参加答辩任务）
	// bsFormatId int, -- 提交格式Id
	// bsDbTypeId int, -- 答辩类型Id
	// extra varchar(255), -- 额外描述
	// addTime datetime not null, -- 添加时间
	// lmtTime timestamp, -- 最后修改时间
	// primary key(id),
	// foreign key(userId) references t_user(id),
	// foreign key(majorId) references t_collage_major(id),
	// foreign key(bsFormatId) references t_bs_commit_format(id),
	// foreign key(bsDbTypeId) references t_bs_db_type(id)
	private Integer userId;
	private Date beginTime;
	private Date endTime;
	private Date expireTime;
	private Integer majorId;
	private Integer gradeNo;
	private Integer status;
	private String statusPhase;
	private Integer type;
	private Integer bsFormatId;
	private Integer bsDbTypeId;
	private User user; // 任务分派人
	private CollageMajor major; // 院校
	private BsCommitFormat bsCommitFormat; // 文档提交格式
	private BsDbType bsDbType; // 毕设答辩类型
	private List<BsTaskStu> stuTasks;

	public List<BsTaskStu> getStuTasks() {
		return stuTasks;
	}

	public void setStuTasks(List<BsTaskStu> stuTasks) {
		this.stuTasks = stuTasks;
	}

	public Integer getUserId() {
		if (user != null)
			return user.getId();
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Integer getMajorId() {
		if(major != null)
			return major.getId();
		return majorId;
	}

	public void setMajorId(Integer majorId) {
		this.majorId = majorId;
	}

	public Integer getGradeNo() {
		return gradeNo;
	}

	public void setGradeNo(Integer gradeNo) {
		this.gradeNo = gradeNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getBsFormatId() {
		if(bsCommitFormat!=null)
			return bsCommitFormat.getId();
		return bsFormatId;
	}

	public void setBsFormatId(Integer bsFormatId) {
		this.bsFormatId = bsFormatId;
	}

	public Integer getBsDbTypeId() {
		if(bsDbType != null)
			return bsDbType.getId();
		return bsDbTypeId;
	}

	public void setBsDbTypeId(Integer bsDbTypeId) {
		this.bsDbTypeId = bsDbTypeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CollageMajor getMajor() {
		return major;
	}

	public void setMajor(CollageMajor major) {
		this.major = major;
	}

	public BsCommitFormat getBsCommitFormat() {
		return bsCommitFormat;
	}

	public void setBsCommitFormat(BsCommitFormat bsCommitFormat) {
		this.bsCommitFormat = bsCommitFormat;
	}

	public BsDbType getBsDbType() {
		return bsDbType;
	}

	public void setBsDbType(BsDbType bsDbType) {
		this.bsDbType = bsDbType;
	}

	public String getStatusPhase() {
		return statusPhase;
	}

	public void setStatusPhase(String statusPhase) {
		this.statusPhase = statusPhase;
	}
}
