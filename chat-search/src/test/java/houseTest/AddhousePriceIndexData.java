package houseTest;

import com.weixin.utils.util.thread.CommonIndexThead;
import com.weixin.utils.util.thread.MyTaskUtils;
import com.weixin.yj.search.ESHelper;

/**
 * Created by :Guozhihua
 * Date： 2017/4/14.
 */
public class AddhousePriceIndexData {

    private static final String index = "house";
    private static final String  type="price_info";


    public static void main(String[] args) {
        //添加索引
        new AddhousePriceIndexData().addDataForHouse();
        //删除索引,
//        new AddhouseIndexData().deleteDataForHouse();
    }

    /**
     * 从数据库添加数据到索引中
     */
    public void addDataForHouse(){
       try{
           HousePrceAddIndexTask commonIndexThead = new HousePrceAddIndexTask();
           commonIndexThead.setIndex(index);
           commonIndexThead.setType(type);
           String countSql = "select count(*) as count from house_info";
           new MyTaskUtils().MutiTasksRun("hxs_wx",countSql,"count",commonIndexThead);

       }catch (Exception ex){
           ex.printStackTrace();
       }




    }

    /**
     * 删除索引，相当于数据库
     */
    public void deleteDataForHouse(){
          try{
             ESHelper.getInstance().deleteIndex(index);

          }catch (Exception ex){
              ex.printStackTrace();
          }
    }

}
