package xchat.service.iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xchat.dao.BaseDao;
import xchat.dao.UserTicketDao;
import xchat.pojo.UserTicket;
import xchat.service.BaseServiceImpl;
import xchat.service.UserTicketService;

/**
 * 
 * <br>
 * <b>功能：</b>UserTicketService<br>
 */
@Service
@Transactional
public class UserTicketServiceImpl extends BaseServiceImpl<UserTicket> implements UserTicketService {


	@Autowired
    private UserTicketDao userTicketDao;
	

	@Override
	protected BaseDao<UserTicket> getBaseDao() {
		return userTicketDao;
	}

	@Override
	public UserTicket selectByTicket(String ticket) {
		return userTicketDao.selectByTicket(ticket);
	}
}
