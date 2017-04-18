package houseTest;

import com.weixin.utils.jdbc.JdbcUtils;
import com.weixin.utils.tyfdc.Mhouse;
import com.weixin.utils.util.JsonUtils;
import com.weixin.utils.util.thread.CommonIndexThead;
import com.weixin.utils.util.thread.MyTaskUtils;
import com.weixin.yj.search.ESHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 业务处理任务
 * Created by :Guozhihua
 * Date： 2017/4/14.
 */
public class HousePrceAddIndexTask extends CommonIndexThead {


    public HousePrceAddIndexTask() {
    }

    @Override
    public void run() {

        try {
            try {
                List<Map<String, Object>> resultMapList = null;
                this.sql="select * from house_info";
                for (long j = this.startPage; j <= this.endPage; j++) {
                    resultMapList = this.runner.query(JdbcUtils.getPageSql(this.sql, (int) j, MyTaskUtils.TASK_PAGE_SIZE), new MapListHandler());
                    if(resultMapList!=null){
                        for(Map<String,Object> m:resultMapList){
                            String querySql="select * from m_house where houseId="+m.get("id").toString();
                            List<Mhouse> mhouses= (List<Mhouse>) this.runner.query(querySql,new BeanListHandler(Mhouse.class));
                            if(CollectionUtils.isNotEmpty(mhouses)){
                                List<Map<String, Object>> resultMapList1=new ArrayList<>();
                                for(Mhouse mhouse:mhouses){
                                    Map<String,Object> map1= (Map<String, Object>) JsonUtils.parseMap(JsonUtils.toJson(mhouse));
                                    map1.put("houseName",m.get("houseName"));
                                    map1.put("areaName",m.get("areaName"));
                                    map1.put("address",m.get("address"));
                                    map1.put("startTime",m.get("startTime"));
                                    map1.put("endTime",m.get("endTime"));
                                    resultMapList1.add(map1);
                                }

                                ESHelper.getInstance().bulkAddDoc(super.index,super.type,resultMapList1,true);
                            }

                        }
                    }


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
