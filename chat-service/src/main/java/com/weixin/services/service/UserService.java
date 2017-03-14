package com.weixin.services.service;


import com.weixin.entity.chat.User;

import java.util.List;
import java.util.Map;

public interface UserService {

	 public boolean insertSelective(User record);
	 
 	/**
     * 根据openid查询user
     * @param openid
     * @return
     */
    public User getByOpenid(String openid);
    
    public User selectByPrimaryKey(String userId);

    public List<User> selectList(Map<String,Object> param);
    
}
