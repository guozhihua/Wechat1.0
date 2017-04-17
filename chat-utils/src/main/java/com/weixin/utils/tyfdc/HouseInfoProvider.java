package com.weixin.utils.tyfdc;

import com.weixin.utils.jdbc.JdbcUtils;
import com.weixin.utils.util.thread.*;
import com.weixin.utils.util.HttpRequest;
import com.weixin.utils.util.JsonUtils;
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


    private static final String simpe_house_obj1 = "http://wsfc.nsae.tyfdc.gov.cn/Firsthand/tyfc/publish/probld/NBView.do?nid=";

    private static final String sim_url2 = "http://wsfc.nsae.tyfdc.gov.cn/Firsthand/tyfc/publish/p/ProNBView.do?proPID=&nbid=10000201";

    public static void getHoueseList(String url, Map<String, Object> params) {
        String respose = HttpRequest.postRequest(url, JsonUtils.toJson(params));

    }

    public static void main(String[] args) {
        //补充房子信息
//       new HouseInfoProvider().fillInPropertuesVal();
        //获取房产数据信息
//        new HouseInfoProvider().fillAllBasePrice();
        //获取房子具体是否售出信息
        new HouseInfoProvider().setProPrice();
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
            QueryRunner queryRunner = getQueryRunner();
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
     * 房产数据信息，第一步，获取到具体的楼
     * 第二步，获取每个楼的出售情况和户型的具体数据
     *
     * @param pid
     * @return
     */
    public String setHouseDataInfo(QueryRunner queryRunner, String pid) {
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
            String insertSql = "insert into house_obj(houseId,houseName,remark,objId) values(?,?,?,?)";
            Object sqlParams[][] = new Object[elements.size()][];
            int i = 0;
            for (Element element : elements) {
                String id = element.attributes().get("objid");
                List<Node> nodes = element.childNodes();
                try {
                    String celianghao = nodes.get(2).childNode(0).childNode(0).childNode(0).attr("text");
                    String hosueNmae = nodes.get(4).childNode(0).childNode(0).childNode(0).toString();
                    String remaek = nodes.get(6).childNode(0).toString() + "—" + nodes.get(8).childNode(0).toString() + "  " + nodes.get(10).childNode(0).toString();
                    //在里面把数据抽取出来，sql参数数组里,例如
                    sqlParams[i] = new Object[]{pid, hosueNmae, remaek, id};
                    i++;
                } catch (Exception ex) {
                    System.out.println("插入小区楼号失败，houseId :" + id);
                    continue;
                }
            }
            //批量插入所有的楼的数据
            queryRunner.batch(insertSql, sqlParams);
            //根据小区和楼号，获取具体的出售情况
            List<HouseObj> houseobjs = (List<HouseObj>) queryRunner.query("select * from house_obj where houseId=" + pid, new BeanListHandler(HouseObj.class));
            if (CollectionUtils.isNotEmpty(houseobjs)) {

                for (HouseObj obj : houseobjs) {
                    try {
                        String url = simpe_house_obj1 + obj.getObjId() + "&projectid=" + obj.getHouseId();
                        Map<String, String> param = new HashMap<>();
                        param.put("projectid", obj.getHouseId());
                        param.put("nid", obj.getObjId());
                        String rs1 = HttpRequest.postRequest(url, param);
                        if (rs1.contains("bldlist") && rs1.contains("floorlist")) {
                            Document d1 = Jsoup.parse(rs1);
                            Element floorlist = d1.getElementById("floorlist");
                            if (floorlist != null && floorlist.childNodeSize() > 0) {
                                for (Element element : floorlist.children()) {
                                    if (element.childNodeSize() > 0) {
                                        Element element1 = element.children().get(0);
                                        if (element1 != null && element1.childNodeSize() >= 5) {
                                            for (Element element2 : element1.children()) {
                                                String foolr = element2.childNode(0).childNode(0).childNode(0).toString();
                                                if (element2.childNode(1).childNodeSize() > 0) {
                                                    String insertSql2 = "insert into m_house(houseId,houseObjId,floor,floorNum,address) values(?,?,?,?,?)";
                                                    Object sqlParams2[][] = new Object[element2.childNode(1).childNodeSize()][];
                                                    int j = 0;
                                                    for (Node element3 : element2.childNode(1).childNodes()) {
                                                        String id = element3.attr("id").toString();
                                                        //在里面把数据抽取出来，sql参数数组里,例如
                                                        sqlParams2[j] = new Object[]{pid, obj.getObjId(), foolr, id, element3.attr("title").toString()};
                                                        j++;
                                                    }
                                                    //批量插入所有的楼的数据
                                                    queryRunner.batch(insertSql2, sqlParams2);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("插入具体的楼的房间的信息失败：houseId=" + pid);
            ex.printStackTrace();
        }
        return null;
    }

    public void fillInPropertuesVal() {
        try {
            String countSql = "select count(id) as totalCount from house_info";
            CommonIndexThead thread = new HousePrjTask();
            new MyTaskUtils().MutiTasksRun("hxs_wx", countSql, "totalCount", thread);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void fillAllBasePrice() {
        try {
            String countSql = "select count(id) as totalCount from house_info";
            CommonIndexThead thread = new HousePriceTask();
            new MyTaskUtils().MutiTasksRun("hxs_wx", countSql, "totalCount", thread);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * 填充字段
     */
    public void setProPrice() {
        try {
            String countSql = "select count(id) as totalCount from house_info";
            CommonIndexThead thread = new HouseProperTask();
            new MyTaskUtils().MutiTasksRun("hxs_wx", countSql, "totalCount", thread);
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

    private static QueryRunner getQueryRunner() {
        QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource("hxs_wx"));
        return queryRunner;
    }


    public void resetProperPriceDate(QueryRunner runner, String houseId) {
        try {
            String sql1 = "select * from m_house where houseId="+houseId;
            List<Mhouse> mhouses = (List<Mhouse>) runner.query(sql1, new BeanListHandler(Mhouse.class));
            for (Mhouse mhouse : mhouses) {
                String s = mhouse.getAddress();
                if (s.isEmpty()) {
                    continue;
                }
                if(s.contains("建筑面积")&&s.contains("房屋单价")){
                    String sumMinjia = s.substring(s.indexOf("建筑面积")+5, s.indexOf("套内面积"));
                    String minajia2 = s.substring(s.indexOf("套内面积")+5, s.indexOf("分摊面积"));
                    String mianjia3 = s.substring(s.indexOf("分摊面积")+5, s.indexOf("房屋用途"));
                    String yongtu = s.substring(s.indexOf("房屋用途")+5, s.indexOf("房屋单价"));
                    String price = s.substring(s.indexOf("房屋单价")+5, s.indexOf("签约状态") == -1 ? s.length() - 1 : s.indexOf("签约状态"));
                    String qianyue = s.indexOf("签约状态") == -1 ? "可售" : s.substring(s.indexOf("签约状态")+5);

                    String updateSql="update m_house set sumMianji= '"+sumMinjia+"',useMianji='"+minajia2+"',gongtanMianji='"+mianjia3+"',forUsed='"+yongtu+"',price='"+price+"',status='"+qianyue+"' " +
                            "where uid='"+mhouse.getUid()+"'";
                    runner.update(updateSql);
                }

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
