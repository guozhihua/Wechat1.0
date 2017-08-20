package xchat.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xchat.pojo.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 志华 on 2017/8/19.
 */
public class Video4480Config {
    public  static  final String videoTreeList ="http://aaqqy.com/tree_items.js";
    public  static  final String single_video_html ="http://aaqqy.com/video/{vid}.html";


    /**
     * 初始化视频列表基础信息
     * @return
     */
   public  static   List<Video> initVideoList(){
       List<Video> videos = new ArrayList<>();
       try{
           String  jsonObject= HttpUtils.get(videoTreeList);
           int begin =jsonObject.indexOf("[[");
           int end =jsonObject.indexOf("]]");
           jsonObject= jsonObject.substring(begin,end+1);
           String regex = "\\[[^\\]]*\\]";
           Pattern pattern = Pattern.compile(regex);
           Matcher m = pattern.matcher(jsonObject);
           List<String> matchRegexList = new ArrayList<String>();
           while(m.find()){
               matchRegexList.add(m.group());
           }
           if(!matchRegexList.isEmpty()){
               String regex1 = "\\(\\d+\\,";
               Pattern pattern1 = Pattern.compile(regex1);
               for(int i =2 ;i<matchRegexList.size();i++){

                   String videoPageinfo =matchRegexList.get(i);
                   if(videoPageinfo.contains("︱")&&videoPageinfo.contains("4480")){
                       System.out.println("**********"+videoPageinfo);
                       int typeindex =videoPageinfo.indexOf("︱");
                       int nameIndex=videoPageinfo.indexOf("4480");
                       String type =videoPageinfo.substring(2,typeindex);
                       String name =videoPageinfo.substring(typeindex+1,nameIndex);
                       Matcher m1 = pattern1.matcher(videoPageinfo.substring(nameIndex+1));
                       String id = null;
                       while (m1.find()){
                           id =m1.group(0);
                           if(id!=null){
                               id=id.substring(1,id.length()-1);
                           }
                           break;
                       }
                      Video video = Video.builder().videoUuid(Integer.valueOf(id)).videoName(name.trim()).videoType(type.trim()).build();
                       videos.add(video);
                       System.out.println( type+"  " +name +"   "+id);
                   }
               }

           }


       }catch (Exception ex){
           ex.printStackTrace();
       }
       return  videos;
    }



    public static void main(String[] args) {
        String  url  = Video4480Config.single_video_html.replace("{vid}",15107+"");
        String bodyHtml= HttpUtils.get(url);
        Document document = Jsoup.parse(bodyHtml);
       Elements elements = document.getElementsByTag("iframe");
       String src =  elements.get(0).attr("src");
        System.out.println(1);

    }


}
