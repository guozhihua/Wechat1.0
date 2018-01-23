package xchat.service.iml;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xchat.dao.BaseDao;
import xchat.dao.DeviceInfoDao;
import xchat.pojo.DeviceInfo;
import xchat.service.BaseServiceImpl;
import xchat.service.DeviceInfoService;

/**
 * 
 * <br>
 * <b>功能：</b>DeviceInfoService<br>
 */
@Service
@Transactional
public class DeviceInfoServiceImpl extends BaseServiceImpl<DeviceInfo> implements DeviceInfoService {
  private final static Logger log= Logger.getLogger(DeviceInfoServiceImpl.class);
	

	@Autowired
    private DeviceInfoDao deviceInfoDao;
	

	@Override
	protected BaseDao<DeviceInfo> getBaseDao() {
		return deviceInfoDao;
	}

}
