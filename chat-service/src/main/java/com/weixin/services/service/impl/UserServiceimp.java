package com.weixin.services.service.impl;

import com.weixin.entity.chat.User;
import com.weixin.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.services.dao.UserMapper;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceimp implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public boolean insertSelective(User record) {
		
		return userMapper.insertSelective(record)>0?true:false;
	}

	@Override
	public User getByOpenid(String openid) {
		
		return userMapper.getByOpenid(openid);
	}

	@Override
	public User selectByPrimaryKey(String userId) {
		
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public List<User> selectList(Map<String, Object> param) {
		return userMapper.selectList(param);
	}
}
