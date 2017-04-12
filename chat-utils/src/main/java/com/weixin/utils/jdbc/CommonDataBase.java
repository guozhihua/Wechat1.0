package com.weixin.utils.jdbc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by :Guozhihua
 * Date： 2017/4/12.
 */
public class CommonDataBase implements Serializable {
    //所有落地数据库名称，判断的时候都采用小写，自动适配xxTimely
    public final static List<String> LOCAL_APP_DATABSENAMES = new ArrayList<String>(){
        {
            this.add("hxs_wx");
            this.add("apple");
            this.add("test");
            this.add("honeybeeTimely");
        }
    };
}
