package com.chat.sso.dao;

import com.weixin.entity.chat.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * 根据openid查询user
     * @param openid
     * @return
     */
    public User getByOpenid(String openid);

    public List<User> selectList(Map<String, Object> param);
    
}