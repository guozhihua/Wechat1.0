package xchat.service.iml;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xchat.dao.BaseDao;
import xchat.dao.UserDao;
import xchat.pojo.User;
import xchat.service.BaseServiceImpl;
import xchat.service.UserService;

/**
 * 
 * <br>
 * <b>功能：</b>UserService<br>
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
  private final static Logger log= Logger.getLogger(UserServiceImpl.class);
	

	@Autowired
    private UserDao userDao;
	

	@Override
	protected BaseDao<User> getBaseDao() {
		return userDao;
	}

}
