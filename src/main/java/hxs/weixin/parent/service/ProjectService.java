package hxs.weixin.parent.service;

import hxs.weixin.parent.entity.Project;

public interface ProjectService extends BaseService<Project>{

	 /**
     * 根据 ctbCode 查询产品
     * @param ctbCode
     * @return
     */
    public Project getByctbCode(String ctbCode);

    Project selectByPrimaryKey(Integer projectId);
}
