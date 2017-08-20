package xchat.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xchat.dao.BaseDao;
import xchat.dao.VideoDao;
import xchat.pojo.Video;
import xchat.services.VideoService;

/**
 * 
 * <br>
 * <b>功能：</b>VideoService<br>
 */
@Service
//@Transactional
public class VideoServiceImpl extends BaseServiceImpl<Video> implements VideoService {
  private final static Logger logger= LogManager.getLogger("VideoServiceImpl");
	

	@Autowired
    private VideoDao videoDao;
	

	@Override
	protected BaseDao<Video> getBaseDao() {
		return videoDao;
	}

}
