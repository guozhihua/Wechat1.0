package hxs.weixin.parent.service.impl;

import hxs.weixin.parent.dao.BaseDao;
import hxs.weixin.parent.dao.ProjectMapper;
import hxs.weixin.parent.entity.Project;
import hxs.weixin.parent.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceimp extends BaseServiceImpl<Project> implements ProjectService{

	@Autowired
	private ProjectMapper projectMapper;

	@Override
	protected BaseDao<Project> getBaseDao() {
		return this.projectMapper;
	}

	@Override
	public Project getByctbCode(String ctbCode) {

		return projectMapper.getByctbCode(ctbCode);
	}

	@Override
	public Project selectByPrimaryKey(Integer projectId) {
		return projectMapper.selectByPrimaryKey(projectId);
	}
}
