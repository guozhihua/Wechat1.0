package hxs.weixin.web.controller;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.HashMap;

/**
 * Created by :Guozhihua
 * Date： 2017/7/19.
 */
@Controller
@RequestMapping("ai")
public class AiController {

    //设置APPID/AK/SK
    public static final String APP_ID = "9908364";
    public static final String API_KEY = "Fm8DFWZ3RvOz8gD1uiyTOuG6";
    public static final String SECRET_KEY = "BYKZw0VoqwhZkKseQEcdV3j2gPGT4yhE";

    @RequestMapping("/audio")
    public void  getAudioByte(HttpServletResponse response){

        HashMap map = new HashMap();
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
            response.setContentType("application/x-shockwave-flash");
            response.getOutputStream().write(data);
            response.flushBuffer();
        }catch (Exception ex){
            ex.printStackTrace();

        }

    }

    @RequestMapping("/img")
    public void  getmg(HttpServletResponse response){
  String  url = "D:\\xxx.jpg";
        try{
            FileInputStream hFile = new FileInputStream(url);
            int i=hFile.available();
            byte data[]=new byte[i];
          //读数据
            hFile.read(data);
            response.setHeader("Content-Type","image/jpeg");
            response.getOutputStream().write(data);
            response.flushBuffer();
        }catch (Exception ex){
            ex.printStackTrace();

        }

    }


}
