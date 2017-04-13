package com.weixin.utils.tyfdc;

import com.weixin.utils.util.HttpRequest;
import com.weixin.utils.util.JsonUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *http://wsfc.nsae.tyfdc.gov.cn/Firsthand/tyfc/publish/p/ProjInfo.do?propid=
 * Created by :Guozhihua
 * Date： 2017/4/13.
 */
public class HouseInfoProvider {
    private static final String houseList_url = "http://wsfc.nsae.tyfdc.gov.cn/Firsthand/tyfc/publish/ProjListForPassed.do";

    private static final String hosue_data_info ="http://wsfc.nsae.tyfdc.gov.cn/Firsthand/tyfc/publish/ProNBList.do";
    private static final String hosue_prj_info ="http://wsfc.nsae.tyfdc.gov.cn/Firsthand/tyfc/publish/p/ProjInfo.do?propid=";
    public static void getHoueseList(String url, Map<String, Object> params) {
        String respose = HttpRequest.postRequest(url, JsonUtils.toJson(params));

    }

    public static void main(String[] args) {
        //获取房产数据信息
//        getHouseDataInfo("43139");
        //获取项目信息
       getHousePrjInfo("43139");


    }


    /**
     * PerYear:
     * PerFlowNO:
     * PerType:0
     * ProType:0
     * ProName:东
     * OrgName:
     * HouseType:0
     * Region:0
     * ProAddress:
     * pageNo:1
     * pageSize:15
     */
    private static String getHouseList(Map<String, Object> paramMap) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("ProName", "");

            params.put("pageSize", 1000);
            params.put("PerYear", "");
            params.put("PerFlowNO", "");
            params.put("OrgName", "");
            params.put("ProAddress", "");
            params.put("ProType", 0);
            params.put("PerType", 0);
            params.put("HouseType", 0);
            params.put("Region", 0);
            StringBuilder sb = new StringBuilder();

            for(int i= 0;i<5;i++){
                params.put("pageNo", i+1);
                String respose = HttpRequest.postFormRequest(houseList_url, params);
                if(!respose.contains("objid")){
                    continue;
                }
                Document document = Jsoup.parse(respose);
                Elements elements = document.getElementsByAttribute("objid");
                for (Element element : elements) {
                    String id = element.attributes().get("objid");
                    List<Node> nodes = element.childNodes();
                    String houseName = nodes.get(2).childNode(0).childNode(0).childNode(0).toString();
                    try {
                        String areaName = nodes.get(4).childNode(0).toString();
                        String houseType ="";
                        if(nodes.get(6).childNodeSize()>0){
                            houseType = nodes.get(6).childNode(0).toString();
                        }
                        String yushou = nodes.get(8).childNode(0).toString();
                        String kaifa = nodes.get(10).childNode(0).childNode(0).childNode(0).toString();
                        sb.append("insert into house_info(id,hosueName,areaName,houseType,yushouCode,kaifashang)" +
                                " valuse('" + id + "','" + houseName + "','" + areaName + "','" + houseType + "','" + yushou + "','" + kaifa + "');").append("\r\n");
                    }catch (Exception ex){
                        System.out.println("id:"+id+"name:"+houseName+"=============================");
                        continue;
                    }
                }
            }

            File f = new File("d:\\test.sql");
            if(!f.exists()){
                f.createNewFile();
            }
            FileWriter fw =new FileWriter(f);
            fw.write(sb.toString());
            fw.flush();
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 房产数据信息
     *
     * @param id
     * @return
     */
    public static String getHouseDataInfo(String id) {
        try{
            Map<String, Object> params = new HashMap<>();
            params.put("pid", id);
            params.put("pageSize", 100);
            params.put("pageNo", 1);
            String rs =HttpRequest.postFormRequest(hosue_data_info,params);
            System.out.println(rs);
        }catch (Exception ex){
            ex.printStackTrace();
        }


        return null;
    }
    public static Map getHousePrjInfo(String id){
        try {
            String rs=HttpRequest.getRequest(hosue_prj_info+id);
            Document document = Jsoup.parse(rs);
            Elements elements = document.getElementsByTag("tbody");
           Element tby=elements.get(0);
            if(tby.childNodeSize()>0){
                List<Node> nodes=tby.childNodes();
                for(Node n:nodes){
                    if(n.childNodeSize()>0){
                        List<Node> nodes1=n.childNodes();
                        for(Node n1:nodes1){
                            if(n1.childNodeSize()>0){
                                System.out.println(n1.childNode(0).toString());
                            }
                        }
                    }
                }
            }
                System.out.println(rs);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  null;
    }

    public static final String getHouseUnSaleInfo() {
        return null;
    }


    class SimpleObj implements Serializable{
        private String code ;
        private  String name ;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
