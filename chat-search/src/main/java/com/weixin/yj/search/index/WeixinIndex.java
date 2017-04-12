package com.weixin.yj.search.index;

import com.weixin.utils.jdbc.JdbcUtils;
import com.weixin.yj.search.CommonIndexThead;
import com.weixin.yj.search.ESHelper;
import com.weixin.yj.search.mappings.TigerEsConst;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by :Guozhihua
 * Date： 2017/4/12.
 */
public class WeixinIndex extends BaseIndex{

    private  final Log logger = LogFactory.getLog(WeixinIndex.class);


    /**
     * 多线程异步增量索引
     * @param beginTime
     */
    public void createIndex(long beginTime) {
        try {
            QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource("hxs_wx"));
            String baseSql = "select t.ID _id,t.ID courseId,t.TITLE courseTitle,t.PHASE_ID phaseId, t.GRADE_ID gradeId,t.SUBJECT_ID subjectId,t.TEACH_MATERIAL_ID teachMaterialId,t.CHAPTER_ID chapterId,t.SECTION_ID sectionId,t.TERM_IDS termIds,t.USER_ID userId,t.ICON_URL iconUrl,t.ICON_TITLE iconTitle,t.SUMMARY summary,t.UNIT_ID unitId,t.UNIT_PATH unitPath,t.STATUS status,t.HIDE_STATE hideStatus,t.FROM_SYS_CODE fromSysCode,t.CREATE_TIME createTime from t_course t where 1=1 and STATUS > 0";
            StringBuffer countSql = new StringBuffer("select count(*) totalCount from t_course t where 1=1 and STATUS > 0");
            StringBuffer sql = new StringBuffer(baseSql);

            if (beginTime > 0) {
                countSql.append(" and t.MODIFY_TIME >= ").append(beginTime);
                sql.append(" and t.MODIFY_TIME >= ").append(beginTime);
            }
            this.createByProcess(runner, countSql.toString(), sql.toString());
        }catch (Exception ex){
            logger.error("创建索引失败",ex);
        }
    }

    /**
     * 在异步线程run中执行的业务逻辑，即将数据存放于ES中
     * @param resultMapList
     * @param runner
     * @throws Exception
     */
    public static void createIndex(List<Map<String, Object>> resultMapList, QueryRunner runner) throws Exception {
        if (resultMapList != null && resultMapList.size() > 0) {
            List<Map<String, Object>> tempMapList = new ArrayList<Map<String, Object>>();
            List<String> deleteIdList = new ArrayList<String>();
            for (Map<String, Object> field : resultMapList) {
                //用于增量索引去删除索引
                int courseStatus = (int) field.get(TigerEsConst.FIELD_STATUS);
//                if (courseStatus == TConstDefine.STATUS_DEL) {
//                    deleteIdList.add(field.get(TigerEsConst.FIELD_COURSE_ID).toString());
//                } else {
//                    //完成课程信息
//                    TigerEsUtil.fillCourseField(field);
//                    //设置课程参与统计信息
//                    int courseId = (int)field.get(TigerEsConst.FIELD_COURSE_ID);
//                    Map<String,Object> studyCount = queryStudyCount(courseId, runner);
//                    field.put(TigerEsConst.FIELD_STUDY_COUNT,studyCount.get("studyCount"));
//                    Map<String,Object> attendStatisticsMap = queryCourseAttendStatistics(courseId, runner);
//                    if(attendStatisticsMap!=null && !attendStatisticsMap.isEmpty()){
//                        //field.put(TigerEsConst.FIELD_STUDY_COUNT, attendStatisticsMap.get(TigerEsConst.FIELD_STUDY_COUNT));
//                        field.put(TigerEsConst.FIELD_COURSE_SCORE,attendStatisticsMap.get(TigerEsConst.FIELD_COURSE_SCORE));
//                        field.put(TigerEsConst.FIELD_COURSE_WIGHT,attendStatisticsMap.get(TigerEsConst.FIELD_COURSE_WIGHT));
//                    }
//                    //课程统计表
//                    Map<String,Object> studyDesignCompleteRate = queryStudyDesignCompleteRate(courseId,runner);
//                    if(studyDesignCompleteRate!=null && !studyDesignCompleteRate.isEmpty()){
//                        field.put(TigerEsConst.FIELD_STUDY_DESIGN_COMPLETE_RATE,studyDesignCompleteRate.get(TigerEsConst.FIELD_STUDY_DESIGN_COMPLETE_RATE));
//                    }
//                    tempMapList.add(field);
//                }
//            }
                //创建主索引
                if (tempMapList.size() > 0) {
                    ESHelper.getYunInstance().bulkAddDoc(TigerEsConst.INDEX_TIGER_MASTER, TigerEsConst.TYPE_COURSE, tempMapList);
                }
                //删除索引
                if (deleteIdList.size() > 0) {
                    ESHelper.getYunInstance().bulkDeleteDoc(TigerEsConst.INDEX_TIGER_MASTER, TigerEsConst.TYPE_COURSE, deleteIdList);
                }
            }
        }
    }

    /**
     * 多线程创建索引
     *
     * @param runner
     * @param countSql
     * @param sql
     * @throws java.sql.SQLException
     */
    private void createByProcess(QueryRunner runner, String countSql, String sql) throws Exception {
        List<Map<String, Object>> countList = runner.query(countSql, new MapListHandler());
        long totalCount = (long) countList.get(0).get("totalCount");
        if (totalCount > 0) {
            long totalPage = getPageCount(totalCount);
            int maxThreadSize = Runtime.getRuntime().availableProcessors() - 1;//最多启用线程数
            //每个线程跑多少数据
            int threadSize = (int)(totalPage/5) + 1;
            if(threadSize>maxThreadSize){
                threadSize = maxThreadSize;
            }
            long interval = (int) (totalPage / threadSize);
            System.out.println("【tiger】创建课程记录索引线程数：" + threadSize);
            ExecutorService executor = Executors.newCachedThreadPool();
            CountDownLatch latch = new CountDownLatch(threadSize);
            for (int i = 1; i <= threadSize; i++) {
                long startPage = interval * (i - 1) + 1;
                long endPage = (i == threadSize) ? totalPage : startPage + interval - 1;
                CourseIndexThread thread = new CourseIndexThread(latch, startPage, endPage, runner, sql);
                executor.execute(thread);
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                logger.error("等待【tiger】创建课程记录索引的线程结束发送中断",e);
                e.printStackTrace();
            }
            executor.shutdown();
        }
    }



    class CourseIndexThread extends CommonIndexThead {
        public CourseIndexThread() {
        }
        public CourseIndexThread(CountDownLatch latch, long startPage, long endPage, QueryRunner runner, String sql) {
            this.latch = latch;
            this.startPage = startPage;
            this.endPage = endPage;
            this.runner = runner;
            this.sql = sql;
        }

        @Override
        public void run() {
            try {
                try {
                    List<Map<String, Object>> resultMapList = null;
                    for (long j = this.startPage; j <= this.endPage; j++) {
                        resultMapList = this.runner.query(JdbcUtils.getPageSql(this.sql, (int) j, 1000), new MapListHandler());
                        long t = System.currentTimeMillis();
                        createIndex(resultMapList, this.runner);
                    }
                } catch (Exception e) {
                    System.out.println("分批创建课程记录索引错误，对应分段为" + this.startPage + "到" + this.endPage + "页！");
                    e.printStackTrace();
                }
                this.latch.countDown();
                System.out.println("剩余线程数：" + this.latch.getCount());
            } catch (Exception e) {
                logger.error("创建课程索引出现错误!",e);
            }
        }
    }
}
