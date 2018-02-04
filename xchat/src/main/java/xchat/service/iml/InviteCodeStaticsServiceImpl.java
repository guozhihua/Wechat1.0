package xchat.service.iml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private final static Logger log= LoggerFactory.getLogger(InviteCodeStatics.class);
	

	@Autowired
    private InviteCodeStaticsDao inviteCodeStaticsDao;
	

	@Override
	protected BaseDao<InviteCodeStatics> getBaseDao() {
		return inviteCodeStaticsDao;
	}

}
