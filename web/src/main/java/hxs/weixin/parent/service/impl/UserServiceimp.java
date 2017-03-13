package hxs.weixin.parent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hxs.weixin.parent.dao.UserMapper;
import hxs.weixin.parent.entity.User;
import hxs.weixin.parent.service.UserService;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceimp implements UserService{

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
