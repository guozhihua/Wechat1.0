package hxs.weixin.parent.service.impl;

import hxs.weixin.parent.dao.BaseDao;
import hxs.weixin.parent.dao.PRankingRelationDao;
import hxs.weixin.parent.entity.PRankingRelation;
import hxs.weixin.parent.service.PRankingRelationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * <br>
 * <b>功能：</b>PRankingRelationService<br>
 */
@Service("PRankingRelationServiceImpl")
public class PRankingRelationServiceImpl extends BaseServiceImpl<PRankingRelation> implements PRankingRelationService {

	@Autowired
    private PRankingRelationDao pRankingRelationDao;
	

	@Override
	protected BaseDao<PRankingRelation> getBaseDao() {
		return pRankingRelationDao;
	}

}
