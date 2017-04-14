package houseTest;

import com.weixin.utils.jdbc.JdbcUtils;
import com.weixin.utils.tyfdc.House;
import com.weixin.utils.tyfdc.HouseInfoProvider;
import com.weixin.utils.util.thread.CommonIndexThead;
import com.weixin.utils.util.thread.MyTaskUtils;
import com.weixin.yj.search.ESHelper;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.util.List;
import java.util.Map;

/**
 * 业务处理任务
 * Created by :Guozhihua
 * Date： 2017/4/14.
 */
public class HouseAddIndexTask extends CommonIndexThead {
    private String index ;
    private String  type;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HouseAddIndexTask() {
    }

    @Override
    public void run() {

        try {
            try {
                List<Map<String, Object>> resultMapList = null;
                this.sql="select * from house_info";
                for (long j = this.startPage; j <= this.endPage; j++) {
                    resultMapList = this.runner.query(JdbcUtils.getPageSql(this.sql, (int) j, MyTaskUtils.TASK_PAGE_SIZE), new MapListHandler());
                    long t = System.currentTimeMillis();
                    System.out.println("开始第【" + j + "】个" + MyTaskUtils.TASK_PAGE_SIZE+ "条课程记录数据的索引……");
                    ESHelper.getInstance().bulkAddDoc(this.index,this.type,resultMapList,true);
                    System.out.println("第【" + j + "】个" +MyTaskUtils.TASK_PAGE_SIZE + "条课程记录数据的索引结束！耗时：" + (System.currentTimeMillis() - t) + "ms");
                }
            } catch (Exception e) {
                System.out.println("分批创建课程记录索引错误，对应分段为" + this.startPage + "到" + this.endPage + "页！");
                e.printStackTrace();
            }
            this.latch.countDown();
            System.out.println("剩余线程数===========：" + this.latch.getCount());
        } catch (Exception ex) {

        }
    }


}
