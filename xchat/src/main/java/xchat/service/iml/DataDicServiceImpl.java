package xchat.service.iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xchat.dao.BaseDao;
import xchat.dao.DataDicDao;
import xchat.dao.UserDao;
import xchat.pojo.DataDic;
import xchat.pojo.User;
import xchat.service.BaseServiceImpl;
import xchat.service.DataDicService;
import xchat.service.UserService;

/**
 * 
 * <br>
 * <b>功能：</b>UserService<br>
 */
@Service
@Transactional
public class DataDicServiceImpl extends BaseServiceImpl<DataDic> implements DataDicService {


	@Autowired
    private DataDicDao dataDicDao;
	

	@Override
	protected BaseDao<DataDic> getBaseDao() {
		return dataDicDao;
	}

	@Override
	public DataDic queryValueByCode(String code) {
		return dataDicDao.queryValueByCode(code);
	}
}
