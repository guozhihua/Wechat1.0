package xchat.service;


import xchat.pojo.Pages;
import xchat.pojo.vo.PagesVo;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>PagesService<br>
 */
public interface PagesService extends BaseService<Pages> {

    List<Pages> getPagesByParentList(Integer parentId);
    List<PagesVo> getGrandPagesByParentId(Integer parentId);

}
