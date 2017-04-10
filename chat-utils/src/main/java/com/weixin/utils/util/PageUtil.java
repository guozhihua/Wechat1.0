package com.weixin.utils.util;

/**
 * 分页工具类
 * @author rl
 * 2015-10-9
 */
public class PageUtil {

    /** 批量处理数 */
    public static int batchSize = 1000;
    /** 默认当前页为1 */
    public static final int PAGE_NO = 1;
    /** 默认每页显示10条 */
    public static final int PAGE_SIZE = 10;

    /**
     * 返回总页数
     * @author Jianpin.Li
     * @param totalCount
     * @return
     */
    public static long getPageCount(long totalCount) {
        long pageCount = 0;
        if (totalCount % batchSize == 0L) {
            pageCount = (totalCount / batchSize);
        } else {
            pageCount = (totalCount / batchSize + 1);
        }
        return pageCount;
    }

    /**
     * 根据指定的pageSize返回总页数
     * @author rl
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
