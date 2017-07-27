import com.yj.oauth.entity.OApps;
import com.yj.oauth.service.OAppsService;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by :Guozhihua
 * Date： 2017/7/26.
 */
public class TestApps extends BaseTest {

    @Resource
    private OAppsService oAppsService;


    @org.junit.Test
    public void insertBatch() {
        try {
            List<OApps> oAppsList = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                OApps apps = OApps.builder().build();
                apps.setAppName("测试123");
                apps.setAppSecret("2131231");
                apps.setAppId("131" + Math.random());
                apps.setCreateTime(new Date());
                apps.setStatus(1);
                oAppsList.add(apps);
            }
            int num = oAppsService.insertInBatch(oAppsList);
            System.out.println("插入：" + num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @org.junit.Test
    public void updateBatch() {
        try {
            List<OApps> oAppses = oAppsService.selectList(null);
            for(OApps oa :oAppses){
              oa.setStatus(2);
            }

            int num = oAppsService.updateInBatch(oAppses);
            System.out.println("更新：" + num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @org.junit.Test
    public void deleteBatch() {
        try {
            List<Serializable> idList =new ArrayList<>();
            idList.add(1);
            idList.add(21);
            idList.add(22);
            idList.add(34);
            int num = oAppsService.deleteByPrimaryKeyInBatch(idList);
            System.out.println("删除：" + num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
