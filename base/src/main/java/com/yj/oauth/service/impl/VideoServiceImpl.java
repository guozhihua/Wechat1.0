package com.yj.oauth.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import  com.yj.base.services.BaseDao;
import  com.yj.base.services.impl.BaseServiceImpl;
import com.yj.oauth.entity.Video;
import com.yj.oauth.dao.VideoDao;
import com.yj.oauth.service.VideoService;

/**
 * 
 * <br>
 * <b>功能：</b>VideoService<br>
 */
@Service
//@Transactional
public class  VideoServiceImpl  extends BaseServiceImpl<Video> implements VideoService {
  private final static Logger logger= LogManager.getLogger("VideoServiceImpl");
	

	@Autowired
    private VideoDao videoDao;
	

	@Override
	protected BaseDao<Video> getBaseDao() {
		return videoDao;
	}

}
