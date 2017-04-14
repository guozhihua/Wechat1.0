package com.weixin.utils.util.thread;

import com.weixin.utils.jdbc.JdbcUtils;
import com.weixin.utils.tyfdc.HouseInfoProvider;
import com.weixin.utils.tyfdc.house;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * 业务处理任务
 * Created by :Guozhihua
 * Date： 2017/4/14.
 */
public class HousePrjTask extends CommonIndexThead {

    public HousePrjTask() {
    }

    @Override
    public void run() {
        //具体的业务实现步骤。。
        HouseInfoProvider provider=new HouseInfoProvider();
         this.sql = "select id from house_info";
        try {
            for (long j = this.startPage; j <= this.endPage; j++) {
                List<house> llis = (List<house>) runner.query(JdbcUtils.getPageSql(sql, (int) j, MyTaskUtils.TASK_PAGE_SIZE), new BeanListHandler(house.class));
                for (house id : llis) {
                    provider.setHousePrjInfo(id.getId());
                }
            }
            this.latch.countDown();
            System.out.println("剩余线程数===========：" + this.latch.getCount());
        } catch (Exception ex) {

        }
    }
}
