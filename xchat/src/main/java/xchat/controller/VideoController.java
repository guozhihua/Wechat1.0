package xchat.controller;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xchat.pojo.Video;
import xchat.services.VideoService;
import xchat.sys.HttpUtils;
import xchat.sys.Video4480Config;
import xchat.sys.WebModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by :Guozhihua
 * Date： 2017/8/2.
 */
@Controller
@RequestMapping("/video")
public class VideoController extends ABaseController {
    private final Logger logger = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private VideoService videoService;

    @RequestMapping("/init")
    @ResponseBody
    public WebModel init() {
        WebModel webModel = WebModel.getInstance();
        try {
            List<Video> videos = Video4480Config.initVideoList();
            videoService.insertInBatch(videos);
            webModel.setDatas(videos);
        } catch (Exception ex) {
            logger.error("video init error", ex);
            webModel.isFail();
        }
        return webModel;
    }

    @RequestMapping("/list")
    @ResponseBody
    public WebModel list() {
        WebModel webModel = WebModel.getInstance();
        try {
            List<Video> videos = videoService.selectList(null);
            webModel.setDatas(videos);
        } catch (Exception ex) {
            logger.error("video list error", ex);
            webModel.isFail();
        }
        return webModel;
    }


    @RequestMapping("/setHtml")
    @ResponseBody
    public WebModel setHtml() {
        WebModel webModel = WebModel.getInstance();
        try {
            List<Video> videos = videoService.selectList(null);
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
                        System.out.println(src);
                        video.setVideoHtml(src);
                        videoService.updateByIdSelective(video);
                    }
                }
            }
            webModel.setDatas(videos);
        } catch (Exception ex) {
            logger.error("video list error", ex);
            webModel.isFail();
        }
        return webModel;
    }

    @RequestMapping("/setDetail")
    @ResponseBody
    public WebModel setDetail() {
        WebModel webModel = WebModel.getInstance();
        try {
            List<Video> videos = videoService.selectList(null);
            String regex1 = "\\-\\d+\\.";
            Pattern pattern1 = Pattern.compile(regex1);
            Map<String, Object> map = new LinkedHashMap<>();
            if (!org.springframework.util.CollectionUtils.isEmpty(videos)) {
                for (Video video : videos) {
                    if (video.getVideoHtml() == null) continue;
                    Matcher m1 = pattern1.matcher(video.getVideoHtml());
                    if (m1.find()) {
                        String bodyHtml = HttpUtils.get(video.getVideoHtml());
                        Document document = Jsoup.parse(bodyHtml);
                        Elements elements = document.getElementsByClass("play-list-box");
                        if (!elements.isEmpty()) {
                            for (Element element : elements) {
                                Elements elementsByClass = element.getElementsByClass("play-list");
                                if(!elementsByClass.isEmpty()){
                                    Elements children = elementsByClass.get(0).children();
                                    if (!children.isEmpty()) {
                                        Map<String,Object> hashMap  =new LinkedHashMap<>();
                                        for (Element element1 : children) {
                                            String url ="http://aaxxy.com"+element1.attr("href");
                                            String name = element1.text();

                                            hashMap.put(name,url);
                                        }
                                        map.put(element.attr("id"),hashMap);
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                webModel.setDatas(map);
            }
        } catch (Exception ex) {
            logger.error("video list error", ex);
            webModel.isFail();
        }
        return webModel;
    }

    @RequestMapping(value = "/getHtml",produces = MediaType.TEXT_HTML_VALUE)
    public void getOutHtml(@RequestParam("url")  String  url){
        String res=HttpUtils.get(url);
        PrintWriter pw =null;
        try {
             pw =super.response.getWriter();
            pw.print(res);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(pw!=null){
                pw.close();
            }
        }
    }
}
