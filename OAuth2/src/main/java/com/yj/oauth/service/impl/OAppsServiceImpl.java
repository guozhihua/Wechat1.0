package com.yj.oauth.service.impl;

import com.yj.oauth.service.OAppsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import  com.yj.base.services.BaseDao;
import  com.yj.base.services.impl.BaseServiceImpl;
import com.yj.oauth.entity.OApps;
import com.yj.oauth.dao.OAppsDao;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>OAppsService<br>
 */
@Service
//@Transactional
public class  OAppsServiceImpl  extends BaseServiceImpl<OApps> implements OAppsService {
    private static Logger logger = LogManager.getLogger("OAppsServiceImpl");

	@Autowired
    private OAppsDao oAppsDao;


	@Override
	protected BaseDao<OApps> getBaseDao() {
		return oAppsDao;
	}

	@Override
	public OApps createClient(OApps client) {
		return null;
	}

	@Override
	public OApps updateClient(OApps client) {
		return null;
	}

	@Override
	public void deleteClient(Long clientId) {

	}

	@Override
	public OApps findOne(Long clientId) {
		return null;
	}

	@Override
	public List<OApps> findAll() {
		return null;
	}

	@Override
	public OApps findByClientId(String clientId) {
		return null;
	}

	@Override
	public OApps findByClientSecret(String clientSecret) {
		return null;
	}

	public static void main(String[] args) {
		logger.debug("hha{} wihi  {}",13123,23423);
	}
}
