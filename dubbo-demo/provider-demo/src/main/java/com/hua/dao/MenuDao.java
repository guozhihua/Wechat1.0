package com.hua.dao;

import com.test.pojo.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by :Guozhihua
 * Date： 2016/11/4.
 */
@Repository
public interface MenuDao {
    List<Menu> getChildListMenu(@Param("parentId") int parentId);

    Menu selectById(@Param("id") int Id);

    List<Menu> findByIdsMap(List<Integer> list);

}
