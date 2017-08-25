package com.sxsram.ssm.entity;

import java.util.List;

public class Operation extends BaseEntity implements Comparable<Operation> {
	private String name;
	private String url;
	private String imgUrl;
	private String style;
	private Integer seqnum;
	private boolean isMenu;
	private Integer parentId;
	public List<Operation> childOperations;
	private boolean active;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Operation> getChildOperations() {
		return childOperations;
	}

	public void setChildOperations(List<Operation> childOperations) {
		this.childOperations = childOperations;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Integer getSeqnum() {
		return seqnum;
	}

	public void setSeqnum(Integer seqnum) {
		this.seqnum = seqnum;
	}

	public Integer getParentId() {
		return parentId;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getMenu() {
		return isMenu ? 1 : 0;
	}

	// public boolean isMenu() {
	// return isMenu;
	// }

	public void setMenu(int isMenu) {
		this.isMenu = isMenu == 1 ? true : false;
	}

	public void setMenu(boolean isMenu) {
		this.isMenu = isMenu;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Operation [id=" + id + ", name=" + name + ", url=" + url + ", imgUrl=" + imgUrl + ", style=" + style
				+ ", seqnum=" + seqnum + ", isMenu=" + isMenu + ", parentId=" + parentId + "]";
	}

	@Override
	public int compareTo(Operation o) {
		return seqnum.intValue() - o.seqnum.intValue();
	}
}
