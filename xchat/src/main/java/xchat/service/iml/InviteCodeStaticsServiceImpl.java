package xchat.service.iml;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xchat.dao.BaseDao;
import xchat.dao.InviteCodeStaticsDao;
import xchat.pojo.InviteCodeStatics;
import xchat.service.BaseServiceImpl;
import xchat.service.InviteCodeStaticsService;

/**
 * 
 * <br>
 * <b>功能：</b>InviteCodeStaticsService<br>
 */
@Service
@Transactional
public class InviteCodeStaticsServiceImpl extends BaseServiceImpl<InviteCodeStatics> implements InviteCodeStaticsService {
  private final static Logger log= Logger.getLogger(InviteCodeStaticsServiceImpl.class);
	

	@Autowired
    private InviteCodeStaticsDao inviteCodeStaticsDao;
	

	@Override
	protected BaseDao<InviteCodeStatics> getBaseDao() {
		return inviteCodeStaticsDao;
	}

}
