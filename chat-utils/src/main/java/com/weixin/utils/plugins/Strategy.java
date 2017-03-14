package com.weixin.utils.plugins;



import com.weixin.utils.sys.exceptions.ProBaseException;

import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/3/6.
 */
public interface Strategy {
    /**
     * 传入一个需要分表的表名，返回一个处理后的表名
     * Strategy必须包含一个无参构造器
     * @param tableName 表名
     * @return
     */
    public String convert(String tableName, String strategy, String[] cloumnCode, Map map) throws ProBaseException;
}
