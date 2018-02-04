package xchat.service.iml;

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


	@Autowired
    private HuoShanMobileInfoDao huoShanMobileInfoDao;
	

	@Override
	protected BaseDao<HuoShanMobileInfo> getBaseDao() {
		return huoShanMobileInfoDao;
	}

}
