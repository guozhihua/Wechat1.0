package com.weixin.utils.tyfdc;

import com.weixin.utils.jdbc.JdbcUtils;
import com.weixin.utils.util.thread.CommonIndexThead;
import com.weixin.utils.util.HttpRequest;
import com.weixin.utils.util.JsonUtils;
import com.weixin.utils.util.thread.HousePrjTask;
import com.weixin.utils.util.thread.MyTaskUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http://wsfc.nsae.tyfdc.gov.cn/Firsthand/tyfc/publish/p/ProjInfo.do?propid=
 * Created by :Guozhihua
 * Date： 2017/4/13.
 */
public class HouseInfoProvider {
    private static final String houseList_url = "http://wsfc.nsae.tyfdc.gov.cn/Firsthand/tyfc/publish/ProjListForPassed.do";

    private static final String hosue_data_info = "http://wsfc.nsae.tyfdc.gov.cn/Firsthand/tyfc/publish/ProNBList.do";
    private static final String hosue_prj_info = "http://wsfc.nsae.tyfdc.gov.cn/Firsthand/tyfc/publish/p/ProjInfo.do?propid=";

    public static void getHoueseList(String url, Map<String, Object> params) {
        String respose = HttpRequest.postRequest(url, JsonUtils.toJson(params));

    }

    public static void main(String[] args) {
        //获取房产数据信息
        new HouseInfoProvider().setHouseDataInfo(getQueryRunner(),"43139");
        //补充房子信息
//       new HouseInfoProvider().fillInPropertuesVal();
        //获取房子具体是否售出信息
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
    private static String getAllHouseList(Map<String, Object> paramMap) {
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
            QueryRunner queryRunner=getQueryRunner();
            for (int i = 0; i < 5; i++) {
                params.put("pageNo", i + 1);
                String respose = HttpRequest.postFormRequest(houseList_url, params);
                if (!respose.contains("objid")) {
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
                        String houseType = "";
                        if (nodes.get(6).childNodeSize() > 0) {
                            houseType = nodes.get(6).childNode(0).toString();
                        }
                        String yushou = nodes.get(8).childNode(0).toString();
                        String kaifa = nodes.get(10).childNode(0).childNode(0).childNode(0).toString();
                        sb.append("insert into house_info(id,hosueName,areaName,houseType,yushouCode,kaifashang)" +
                                " valuse('" + id + "','" + houseName + "','" + areaName + "','" + houseType + "','" + yushou + "','" + kaifa + "');").append("\r\n");
                        queryRunner.insert(sb.toString(), new BeanHandler(String.class) {
                        });
                    } catch (Exception ex) {
                        System.out.println("id:" + id + "name:" + houseName + "=============================");
                        continue;
                    }
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * 房产数据信息，第一步，获取到具体的楼
     *               第二步，获取每个楼的出售情况和户型的具体数据
     *
     * @param pid
     * @return
     */
    public String setHouseDataInfo( QueryRunner queryRunner,String pid) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("pid", pid);
            params.put("pageSize", 50);
            params.put("pageNo", 1);
            String respose = HttpRequest.postFormRequest(hosue_data_info, params);
            if (!respose.contains("objid")) {
                return null;
            }
            Document document = Jsoup.parse(respose);
            //有多少objid,就有多少行
            Elements elements = document.getElementsByAttribute("objid");
            String insertSql="insert into house_obj(nid,houseId,houseName,remark,objId) values(?,?,?,?,?)";
            Object sqlParams[][] = new Object[elements.size()][];
            int i =0;
            for (Element element : elements) {
                String id = element.attributes().get("objid");
                List<Node> nodes = element.childNodes();
                try {
                   //在里面把数据抽取出来，sql参数数组里,例如
                    sqlParams[i] = new Object[] { "aa" , "123", "aa@sina.com","",""};
                    i++;
                } catch (Exception ex) {
                    System.out.println("id:" +id );
                    continue;
                }
            }
            //批量插入所有的楼的数据
           queryRunner.batch(insertSql,sqlParams);
            //根据小区和楼号，获取具体的出售情况
           List<HouseObj> houseobjs= (List<HouseObj>) queryRunner.query("select * from house_obj",new BeanListHandler(HouseObj.class));
           if(CollectionUtils.isNotEmpty(houseobjs)){



           }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void fillInPropertuesVal() {
        try {
            String countSql = "select count(id) as totalCount from house_info";
            CommonIndexThead thread=new HousePrjTask();
            new MyTaskUtils().MutiTasksRun("hxs_wx",countSql,"totalCount",thread);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }




    /**
     * 填充单个房子的信息
     *
     * @param id
     */
    public static void setHousePrjInfo(String id) {
        try {
            QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource("hxs_wx"));
            String sql = "select * from house_info where id = " + id;
            House house = (House) queryRunner.query(sql, new BeanHandler(House.class));

            String rs = HttpRequest.getRequest(hosue_prj_info + id);
            Document document = Jsoup.parse(rs);
            Elements elements = document.getElementsByTag("tbody");
            List<String> values = new ArrayList<>();
            Element tby = elements.get(0);
            if (house != null && tby.childNodeSize() > 0) {
                List<Node> nodes = tby.childNodes();
                for (Node n : nodes) {
                    if (n.childNodeSize() > 0) {
                        List<Node> nodes1 = n.childNodes();
                        for (Node n1 : nodes1) {
                            if (n1.childNodeSize() > 0) {
                                values.add(n1.childNode(0).toString().replace("&nbsp;", ""));
                            }
                        }
                    }
                }
            }
            if (values.size() == 48) {
                house.setAddress(values.get(5));
                house.setMianji(values.get(15));
                house.setStartTime(values.get(25));
                house.setEndTime(values.get(27));
                house.setNuan(values.get(29));
                house.setQi(values.get(31));
                house.setWater(values.get(33));
                house.setJunjia(values.get(35));
                house.setZhuangxiu(values.get(37));
                house.setShigong(values.get(41));
                house.setRemark(values.get(47));
                String updateSql = "update house_info set startTime ='" + house.getStartTime() + "',endTime ='" + house.getEndTime() + "'," +
                        "address ='" + house.getAddress() + "',mianji='" + house.getMianji() + "',zhuangxiu='" + house.getZhuangxiu() + "'," +
                        "nuan='" + house.getNuan() + "',qi='" + house.getQi() + "',water='" + house.getWater() + "',junjia='" + house.getJunjia() + "'" +
                        ",shigong='" + house.getShigong() + "',remark='" + house.getRemark() + "' where id = " + id;
                int num = queryRunner.update(updateSql);
            }

        } catch (Exception ex) {

            System.out.println("id =【" + id + "】 更新失败" + ex.getMessage());
        }
    }

    public void setHouseUnSaleInfo(String id) {

    }

    private static QueryRunner getQueryRunner(){
        QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource("hxs_wx"));
        return queryRunner;
    }

}
