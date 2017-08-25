package com.sxsram.ssm.mapper;

import java.util.List;

import com.sxsram.ssm.entity.User;
import com.sxsram.ssm.entity.UserQueryVo;

public interface UserMapper {
	public void insertNewUser(User bsTitle);

	public void delUser(Integer id);

	public void updateUser(User bsTitle);

	public List<User> queryMultiUsers(UserQueryVo bsTitleQueryVo);

	public User querySingleUser(UserQueryVo bsTitleQueryVo);
}