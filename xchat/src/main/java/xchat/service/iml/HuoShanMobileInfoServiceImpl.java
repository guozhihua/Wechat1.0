package xchat.service.iml;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xchat.dao.BaseDao;
import xchat.dao.HuoShanMobileInfoDao;
import xchat.pojo.HuoShanMobileInfo;
import xchat.service.BaseServiceImpl;
import xchat.service.HuoShanMobileInfoService;

/**
 * 
 * <br>
 * <b>功能：</b>HuoShanMobileInfoService<br>
 */
@Service
@Transactional
public class HuoShanMobileInfoServiceImpl extends BaseServiceImpl<HuoShanMobileInfo> implements HuoShanMobileInfoService {
  private final static Logger log= Logger.getLogger(HuoShanMobileInfoServiceImpl.class);
	

	@Autowired
    private HuoShanMobileInfoDao huoShanMobileInfoDao;
	

	@Override
	protected BaseDao<HuoShanMobileInfo> getBaseDao() {
		return huoShanMobileInfoDao;
	}

}
