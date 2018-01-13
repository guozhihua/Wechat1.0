package xchat.sys;

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
     * @returnSimpleDateFormat
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
//                       System.out.println( type+"  " +name +"   "+id);
                   }
               }

           }


       }catch (Exception ex){
           ex.printStackTrace();
       }
       return  videos;
    }



    public static List<Video> setVideoHtml(List<Video> videos){

        if (!org.springframework.util.CollectionUtils.isEmpty(videos)) {
            for (Video video : videos) {
                if (video.getVideoHtml() != null) continue;
                String url = Video4480Config.single_video_html.replace("{vid}", video.getVideoUuid() + "");
                String bodyHtml = HttpUtils.get(url);
                Document document = Jsoup.parse(bodyHtml);
                Elements elements = document.getElementsByTag("iframe");
                if (!elements.isEmpty()) {
                    String src = elements.get(0).attr("src");
                    video.setVideoName(video.getVideoName().trim());
                    video.setVideoHtml(src);
                    System.out.println(video.getVideoName()+"      "+video.getVideoHtml());
                }
            }
        }
        return videos;
    }

    public static List<VideoItem> videoItemList(Video video) throws Exception{
        List<VideoItem>  result = null;
        String bodyHtml = HttpUtils.get(video.getVideoHtml());
        Document document = Jsoup.parse(bodyHtml);
        Elements elements = document.getElementsByClass("play-list");
        if(elements!=null&&elements.size()>0){
            result = new ArrayList<>();
            Element element = elements.get(0);
            for(Element element1 :element.children()){
                Elements a = element1.getElementsByTag("a");
                String href ="http://aaxxy.com"+ a.attr("href");
                String text=  a.get(0).childNodes().get(0).attr("text");;
                System.out.println(text+" -----------" +   href);
            }
        }
        return  result ;
    }



    public static void main(String[] args) {
        try{

            List<Video> videos = initVideoList();
            setVideoHtml(videos);
            videoItemList(videos.get(0));

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    class VideoItem{
        private  String  name ;

        private  String itemUrl;

        public VideoItem(String name, String itemUrl) {
            this.name = name;
            this.itemUrl = itemUrl;
        }

        public String getName() {
            return name;
        }

        public String getItemUrl() {
            return itemUrl;
        }
    }

}
