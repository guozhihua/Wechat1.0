package com.weixin.utils.util.thread;

import com.weixin.utils.jdbc.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程任务处理数据工具类
 * Created by :Guozhihua
 * Date： 2017/4/14.
 */
public class MyTaskUtils {
    //指定每个分页多少数目
    public static  final int TASK_PAGE_SIZE=100;
    //指定每个线程的处理的页数
    public static  final int TASK_PAGE_NUM=3;
    private static QueryRunner getQueryRunner(String datasourceName){
        QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource(datasourceName));
        return queryRunner;
    }

    /**
     * 多线程执行数据任务
     *    String countSql = "select count(id) as totalCount from house_info";
     * @param dataSourceName
     * @param countSql
     */
    public void MutiTasksRun(String dataSourceName,String countSql,String totalCoumnName,CommonIndexThead thead){
        ExecutorService executor = Executors.newCachedThreadPool();
        try{
            QueryRunner queryRunner =getQueryRunner(dataSourceName);
            List<Map<String, Object>> countList = queryRunner.query(countSql, new MapListHandler());
            long totalCount = (long) countList.get(0).get(totalCoumnName);
            if (totalCount > 0) {
                int pageSize = TASK_PAGE_SIZE;
                long pages = totalCount / pageSize;
                if (!(totalCount % pageSize == 0)) {
                    pages++;
                }
                int maxThreadSize = Runtime.getRuntime().availableProcessors() - 1;//最多启用线程数
                //根据每个线程处理页来计算出来需要启用的线程数目
                int threadSize = (int) (pages / TASK_PAGE_NUM) + 1;
                if (threadSize > maxThreadSize) {
                    threadSize = maxThreadSize;
                }
                long interval = (int) (pages / threadSize);
                CountDownLatch latch = new CountDownLatch(threadSize);
                for (int i = 1; i <= threadSize; i++) {
                    long startPage = interval * (i - 1) + 1;
                    long endPage = (i == threadSize) ? pages : startPage + interval - 1;
                    if(thead!=null){
                        thead.setLatch(latch);
                        thead.setStartPage(startPage);
                        thead.setEndPage(endPage);
                        thead.setRunner(queryRunner);
                    }
                    executor.execute(thead);
                }
            }
        }catch (Exception ex){
             ex.printStackTrace();
        }finally {
            executor.shutdown();
        }


    }




}
