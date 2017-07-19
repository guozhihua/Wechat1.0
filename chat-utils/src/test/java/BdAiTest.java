import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * Created by :Guozhihua
 * Date： 2017/7/19.
 */
public class BdAiTest {

    //设置APPID/AK/SK
    public static final String APP_ID = "9908364";
    public static final String API_KEY = "Fm8DFWZ3RvOz8gD1uiyTOuG6";
    public static final String SECRET_KEY = "BYKZw0VoqwhZkKseQEcdV3j2gPGT4yhE";

    @Test
    public void TestAiHC(){
        try{
            // 初始化一个FaceClient
            AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);


            // 设置可选参数
            HashMap<String, Object> options = new HashMap<String, Object>();
            options.put("spd", "4");
            options.put("pit", "6");
            options.put("per", "2");
            options.put("vol", "12");
            TtsResponse res1 = client.synthesis("今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚打老虎今晚打老虎今晚打今晚打老虎今晚打老虎今晚", "zh", 1, options);
            System.out.println(res1.getErrorCode());
            byte[] data = res1.getData();
            File file = new File("D:\\test\\test21234");
            if (!file.exists()) {
                file.mkdirs();
            }
            File tempWav = new File(file, "temp2.wav");
            if (!tempWav.exists())
                tempWav.createNewFile();
            FileOutputStream fos = new FileOutputStream(tempWav);
            fos.write(data);
            fos.flush();
            fos.close();


        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


}
