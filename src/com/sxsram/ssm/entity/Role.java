package com.sxsram.ssm.entity;

import java.util.List;

public class Role extends BaseEntity {
	private String roleName;
	private String roleGrade;
	private Integer sequence;
	private List<Permission> permissionList;

	public List<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleGrade() {
		return roleGrade;
	}

	public void setRoleGrade(String roleGrade) {
		this.roleGrade = roleGrade;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", roleGrade=" + roleGrade + "]";
	}

}
