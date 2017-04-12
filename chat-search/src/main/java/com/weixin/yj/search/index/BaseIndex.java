package com.weixin.yj.search.index;

/**
 * Created by :Guozhihua
 * Date： 2017/4/12.
 */
public class BaseIndex {


    /**
     * 返回总页数
     * @author Jianpin.Li
     * @param totalCount
     * @return
     */
    public static long getPageCount(long totalCount) {
        long pageCount = 0;
        if (totalCount % 1000 == 0L) {
            pageCount = (totalCount / 1000);
        } else {
            pageCount = (totalCount / 1000 + 1);
        }
        return pageCount;
    }

    /**
     * 根据指定的pageSize返回总页数
     * @author Jianpin.Li
     * @param totalCount 总记录数
     * @param pageSize 每页显示多少条
     * @return
     */
    public static long getPageCount(long totalCount, int pageSize) {
        long pageCount = 0;
        if (totalCount % pageSize == 0L) {
            pageCount = (totalCount / pageSize);
        } else {
            pageCount = (totalCount / pageSize + 1);
        }
        return pageCount;
    }

}
