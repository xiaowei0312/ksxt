package com.sxsram.ssm.entity;

public class CollageMajor extends BaseEntity {
	// parentId int,
	// name varchar(50) not null,
	// sequence int default 0,
	private Integer parentId;
	private String name;
	private Integer grade;
	private Integer sequence;
	private CollageMajor parent;
	
	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getParentId() {
		if (parentId != null)
			return parentId;
		if (parent != null)
			return parent.getId();
		return null;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public CollageMajor getParent() {
		return parent;
	}

	public void setParent(CollageMajor parent) {
		this.parent = parent;
	}

}
