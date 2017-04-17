package com.weixin.utils.util.thread;

import com.weixin.utils.jdbc.JdbcUtils;
import com.weixin.utils.tyfdc.House;
import com.weixin.utils.tyfdc.HouseInfoProvider;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * 业务处理任务
 * Created by :Guozhihua
 * Date： 2017/4/14.
 */
public class HouseProperTask extends CommonIndexThead {

    public HouseProperTask() {
    }

    @Override
    public void run() {
        //具体的业务实现步骤。。
        HouseInfoProvider provider=new HouseInfoProvider();
         this.sql = "select id from house_info";
        try {
            for (long j = this.startPage; j <= this.endPage; j++) {
                List<House> llis = (List<House>) runner.query(JdbcUtils.getPageSql(sql, (int) j, MyTaskUtils.TASK_PAGE_SIZE), new BeanListHandler(House.class));
                for (House id : llis) {
                    System.out.println("当前执行的线程名称："+Thread.currentThread().getName());
                    provider.resetProperPriceDate(runner,id.getId());
                }
            }
            this.latch.countDown();
            System.out.println("剩余线程数===========：" + this.latch.getCount());
        } catch (Exception ex) {

        }
    }
}
