package houseTest;

import com.weixin.utils.util.thread.MyTaskUtils;

/**
 * Created by :Guozhihua
 * Date： 2017/4/14.
 */
public class AddhouseIndexData {

    private static final String index = "house";
    private static final String  type="base_info";


    public static void main(String[] args) {
        //添加索引
        new AddhouseIndexData().addDataForHouse();
    }

    /**
     * 从数据库添加数据到索引中
     */
    public void addDataForHouse(){
       try{
           HouseAddIndexTask commonIndexThead = new HouseAddIndexTask();
           commonIndexThead.setIndex(index);
           commonIndexThead.setType(type);
           String countSql = "select count(*) as count from house_info";
           new MyTaskUtils().MutiTasksRun("hxs_wx",countSql,"count",commonIndexThead);

       }catch (Exception ex){
           ex.printStackTrace();
       }


    }


}