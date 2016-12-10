package hxs.weixin.parent.service.impl;

import hxs.weixin.parent.dao.BaseDao;
import hxs.weixin.parent.dao.PTestProjectDao;
import hxs.weixin.parent.entity.PTestProject;
import hxs.weixin.parent.service.PTestProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhusen on 2016/11/26.
 */
@Service("pTestProjectService")
public class PTestProjectServiceImpl extends BaseServiceImpl<PTestProject> implements PTestProjectService {
    @Autowired
    private PTestProjectDao pTestProjectDao;

    @Override
    protected BaseDao<PTestProject> getBaseDao() {
        return this.pTestProjectDao;
    }

}
