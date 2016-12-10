package hxs.weixin.parent.dao;

import hxs.weixin.parent.entity.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMapper extends BaseDao<Project>{
    int deleteByPrimaryKey(Integer projectId);

    int insert(Project record);

    Project selectByPrimaryKey(Integer projectId);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);

    /**
     * 根据 ctbCode 查询产品
     * @param ctbCode
     * @return
     */
    public Project getByctbCode(String ctbCode);
}