package com.sxsram.ssm.service;

import java.util.List;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxsram.ssm.entity.User;
import com.sxsram.ssm.entity.UserQueryVo;
import com.sxsram.ssm.mapper.UserMapper;
import com.sxsram.ssm.util.MD5Util;

@Service("userService")
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public void addNewUser(User user) throws Exception {
		user.setPassword(MD5Util.MD5Encode(user.getPassword(),null));
		userMapper.insertNewUser(user);
	}

	public void delUser(User user) throws Exception {
		userMapper.delUser(user.getId());
	}

	public void updateUser(User user) throws Exception {
		user.setPassword(MD5Util.MD5Encode(user.getPassword(),null));
		userMapper.updateUser(user);
	}

	public List<User> findUsers(UserQueryVo userQueryVo) throws Exception {
		return userMapper.queryMultiUsers(userQueryVo);
	}

	public User findUser(UserQueryVo userQueryVo) throws Exception {
		return userMapper.querySingleUser(userQueryVo);
	}
}