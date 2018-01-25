package xchat.dao;


import org.springframework.stereotype.Repository;
import xchat.pojo.Pages;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>PagesDao<br>
 */
 @Repository
public interface PagesDao extends BaseDao<Pages> {

 public List<Pages> getPagesByParentList(Integer parentId) ;
 public List<Pages> getGrandPagesByParentId(Integer parentId) ;
}
