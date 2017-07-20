package com.yj.oauth.service;

import com.yj.base.services.BaseService;
import com.yj.oauth.entity.OUser;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>OUserService<br>
 */
public interface OUserService extends BaseService<OUser> {
    public OUser updateUser(OUser user);// 更新用户

    public void deleteUser(String userId);// 删除用户

    public void changePassword(String userId, String newPassword); //修改密码

    OUser findOne(String userId);// 根据id查找用户

    List<OUser> findAll();// 得到所有用户

    public OUser findByUsername(String username);// 根据用户名查找用户
}
