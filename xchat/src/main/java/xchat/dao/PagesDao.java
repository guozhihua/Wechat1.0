package xchat.dao;


import org.springframework.stereotype.Repository;
import xchat.pojo.Pages;
import xchat.pojo.vo.PagesVo;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>PagesDao<br>
 */
 @Repository
public interface PagesDao extends BaseDao<Pages> {

 public List<Pages> getPagesByParentList(Integer parentId) ;
 public List<PagesVo> getGrandPagesByParentId(Integer parentId) ;
}
