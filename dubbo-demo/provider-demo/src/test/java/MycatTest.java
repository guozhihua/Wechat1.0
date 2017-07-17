import com.weixin.utils.util.DBHelper;
import org.apache.commons.dbutils.DbUtils;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;

/**
 * Created by :Guozhihua
 * Date： 2017/7/17.
 */
public class MycatTest {


    @Test
    public  void insertUser(){
        try{
            String sql = "insert into user (user_id,user_name) values (?,?)";
            String  uuid = UUID.randomUUID().toString().toLowerCase();
            String  name="Test131";
            String[] names =new String[]{uuid,name};
            for(int i =0;i<names.length;i++){
                sql=sql.replaceFirst("\\?","'"+names[i]+"'");
            }
            System.out.println("sql :"+sql);
            DBHelper dbHelper =new DBHelper();
            dbHelper.execute(sql);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
