package com.yj.oauth.service.impl;

import com.yj.base.services.BaseDao;
import com.yj.base.services.impl.BaseServiceImpl;
import com.yj.oauth.dao.OUserDao;
import com.yj.oauth.entity.OUser;
import com.yj.oauth.service.OUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>OUserService<br>
 */
@Service
//@Transactional
public class OUserServiceImpl extends BaseServiceImpl<OUser> implements OUserService {
  private final static Logger logger= LogManager.getLogger("OUserServiceImpl");
	

	@Autowired
    private OUserDao oUserDao;
	

	@Override
	protected BaseDao<OUser> getBaseDao() {
		return oUserDao;
	}

	@Override
	public OUser updateUser(OUser user) {
		return null;
	}

	@Override
	public void deleteUser(String userId) {

	}

	@Override
	public void changePassword(String userId, String newPassword) {

	}

	@Override
	public OUser findOne(String userId) {
		return null;
	}

	@Override
	public List<OUser> findAll() {
		return null;
	}

	@Override
	public OUser findByUsername(String username) {
		return null;
	}
}
