package com.weixin.utils.plugins;



import com.weixin.utils.sys.exceptions.ProBaseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/3/6.
 */

public class YYYYStrage implements Strategy {
    /**
     * 传入一个需要分表的表名，返回一个处理后的表名
     * Strategy必须包含一个无参构造器
     *
     * @param tableName
     * @return
     */
    /**
     * 传入一个需要分表的表名，返回一个处理后的表名
     * Strategy必须包含一个无参构造器
     *
     * @param tableName 表名
     * @param map
     * @return
     */
    @Override
    public String convert(String tableName,String strategy,String[] cloumnCode, Map map) throws ProBaseException {
        if(!"YYYY".equals(strategy)){
            throw  new ProBaseException("YYYYStrage strategy must be  YYYY");
        }else{
            SimpleDateFormat sd  = new SimpleDateFormat("YYYY");
            StringBuilder sb = new StringBuilder(tableName);
            sb.append("_").append(sd.format(new Date()));
            return  sb.toString();
        }
    }
}
