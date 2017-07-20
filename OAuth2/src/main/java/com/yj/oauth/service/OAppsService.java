package com.yj.oauth.service;

import com.yj.base.services.BaseService;
import com.yj.oauth.entity.OApps;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>OAppsService<br>
 */
public interface OAppsService extends BaseService<OApps> {
    public OApps createClient(OApps client);// 创建客户端
    public OApps updateClient(OApps client);// 更新客户端
    public void deleteClient(String appId);// 删除客户端
    OApps findOne(String appId);// 根据id查找客户端
    List<OApps> findAll();// 查找所有
    OApps findByClientId(String  appId);// 根据客户端id查找客户端
    OApps findByClientSecret(String clientSecret);//根据客户端安全KEY查找客户端
}
