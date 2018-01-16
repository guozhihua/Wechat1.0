package xchat.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by :Guozhihua
 * Date： 2018/1/12.
 */
public class XGCode {


   //A38YB
    public static int setCode(String inviteCode,int size ,int fistCode ) {
        if(fistCode<6000000){
            fistCode=7122000;
        }
        int lastnum = 0 ;
        String url1="https://api-spe.snssdk.com/h/1/cli/check_invite/?" +
                "iid=23221104831&device_id=35800373860&ac=wifi&channel=video-ky-and238" +
                "&aid=32&app_name=video_article&version_code=628&version_name=6.2.8&" +
                "device_platform=android&ab_version=221019%2C236847%2C246277%2C249883%2C253345%2C229577%2C237852%2C254738%2C249817%2C249821%2C249631%2C250929%2C252881%2C239019%2C255490%2C254271%2C252979%2C235685%2C249824%2C249816%2C249827%2C249829%2C248257%2C150150" +
                "&ssmix=a&device_type=vivo+Y67&device_brand=vivo&language=zh&os_api=23&os_version=6.0" +
                "&uuid=864282030024243" +
                "&openudid=9c44daa398745111" +
                "&manifest_version_code=228" +
                "&resolution=720*1280&dpi=320&update_version_code=6285&_rticket="+new Date().getTime();
        for(int i =0;i<1;i++){
            if(lastnum==size||lastnum>size){
                break;
            }
                try {
                    String code ="A38YB";
                    URL u = new URL(url1);
                    HttpURLConnection uc = (HttpURLConnection) u.openConnection();
                    uc.setDoOutput(true);
                    uc.setDoInput(true);
                    uc.setUseCaches(false);
                    uc.setRequestProperty("Content-Type","application/raw");
                    uc.setRequestMethod("POST");
                    uc.addRequestProperty("Cookie", "_ga=GA1.2.2120708950.1515758666");
                    uc.addRequestProperty("Cookie", "_gid=GA1.2.2083295210.1515994003");
                    uc.addRequestProperty("Cookie", "alert_coverage=4");
                    uc.addRequestProperty("Cookie", "install_id=23221104831");
                    uc.addRequestProperty("Cookie", "login_flag=db36f315d025990fcdf3f67175fc7109");
                    uc.addRequestProperty("Cookie", "odin_tt=9da4bf711b0aa4563cad63af91c2ad665baf08e34928b80462c102b3ce27aade636061022636dfbf09c5dd0ac02c9e89");
                    uc.addRequestProperty("Cookie", "qh[360]=1");
                    uc.addRequestProperty("Cookie", "sessionid=9e69ae258886fc9eb860144d867b1366");
                    uc.addRequestProperty("Cookie", "sid_guard=9e69ae258886fc9eb860144d867b1366%7C1516002066%7C2592000%7CWed%2C+14-Feb-2018+07%3A41%3A06+GMT");
                    uc.addRequestProperty("Cookie", "sid_tt=9e69ae258886fc9eb860144d867b1366");
                    uc.addRequestProperty("Cookie", "ttreq=1$b80c5dc3fd322e6f99143e45ad982789a8817f77");
                    uc.addRequestProperty("Cookie", "uid_tt=828426ce5782d0bb3388759ce4eba163");

                    byte[] data = code.getBytes();
                    uc.connect();
                    OutputStream out = uc.getOutputStream();
                    out.write((code.toString()).getBytes());
                    out.flush();
                    out.close();

                    System.out.println(uc.getResponseCode());
                    // 请求返回的状态
                    if (uc.getResponseCode() == 200) {
                        System.out.println("连接成功");
                        // 请求返回的数据
                        InputStream in = uc.getInputStream();
                        String a = null;
                        try {
                            byte[] data1 = new byte[in.available()];
                            in.read(data1);
                            // 转成字符串
                            a = new String(data1);
                            System.out.println(a);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        System.out.println("请求失败了");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        return  lastnum;
    }

    public static  void   setXGCode(){
        String url="https://api-spe.snssdk.com/h/1/cli/check_invite/?" +
                "iid=23221104831&device_id=35800373860&ac=wifi&channel=video-ky-and238" +
                "&aid=32&app_name=video_article&version_code=628&version_name=6.2.8&" +
                "device_platform=android&ab_version=221019%2C236847%2C246277%2C249883%2C253345%2C229577%2C237852%2C254738%2C249817%2C249821%2C249631%2C250929%2C252881%2C239019%2C255490%2C254271%2C252979%2C235685%2C249824%2C249816%2C249827%2C249829%2C248257%2C150150" +
                "&ssmix=a&device_type=vivo+Y67&device_brand=vivo&language=zh&os_api=23&os_version=6.0" +
                "&uuid=864282030024243" +
                "&openudid=9c44daa398745111" +
                "&manifest_version_code=228" +
                "&resolution=720*1280&dpi=320&update_version_code=6285&_rticket="+new Date().getTime();
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
           post.addHeader(new BasicHeader("Cookie", "_ga=GA1.2.2120708950.1515758666"));
           post.addHeader(new BasicHeader("Cookie", "_gid=GA1.2.2083295210.1515994003"));
           post.addHeader(new BasicHeader("Cookie", "alert_coverage=4"));
           post.addHeader(new BasicHeader("Cookie", "install_id=23221104831"));
           post.addHeader(new BasicHeader("Cookie", "login_flag=db36f315d025990fcdf3f67175fc7109"));
           post.addHeader(new BasicHeader("Cookie", "qh[360]=1"));
           post.addHeader(new BasicHeader("Cookie", "sessionid=976ff96f9feecdcd08ba4925be0b2d8a"));
           post.addHeader(new BasicHeader("Cookie", "sid_tt=9e69ae258886fc9eb860144d867b1366"));
           post.addHeader(new BasicHeader("Cookie", "ttreq=1$b80c5dc3fd322e6f99143e45ad982789a8817f77"));
           post.addHeader(new BasicHeader("Cookie", "uid_tt=828426ce5782d0bb3388759ce4eba163"));

            StringEntity postingString = new StringEntity("A37YB");// json传递
            post.setEntity(postingString);
            //			httppost.addHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
            post.addHeader(new BasicHeader("Content-Type", "'application/x-www-form-urlencoded charset=utf-8"));
             post.addHeader("Connection","Keep-Alive");
//            post.setHeader("Content-type", "application/json");
            post.setEntity(new StringEntity("A38YB", "UTF-8"));
            HttpResponse response = httpClient.execute(post);
            String content = EntityUtils.toString(response.getEntity());
            System.out.println(content);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        setXGCode();
    }

//   setCode("111",1,190291);
}
