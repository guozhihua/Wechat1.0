package xchat.service.iml;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xchat.dao.BaseDao;
import xchat.dao.PagesDao;
import xchat.pojo.Pages;
import xchat.pojo.vo.PagesVo;
import xchat.service.BaseServiceImpl;
import xchat.service.PagesService;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>PagesService<br>
 */
@Service
@Transactional
public class PagesServiceImpl extends BaseServiceImpl<Pages> implements PagesService {
  private final static Logger log= Logger.getLogger(PagesServiceImpl.class);
	

	@Autowired
    private PagesDao pagesDao;
	

	@Override
	protected BaseDao<Pages> getBaseDao() {
		return pagesDao;
	}

	@Override
	public List<Pages> getPagesByParentList(Integer parentId) {
		return pagesDao.getPagesByParentList(parentId);
	}

	@Override
	public List<PagesVo> getGrandPagesByParentId(Integer parentId) {
		return pagesDao.getGrandPagesByParentId(parentId);
	}
}
