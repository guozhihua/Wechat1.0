package com.weixin.utils.tyfdc;

import com.weixin.utils.jdbc.JdbcUtils;
import com.weixin.utils.util.thread.*;
import com.weixin.utils.util.HttpRequest;
import com.weixin.utils.util.JsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
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
//        new HouseInfoProvider().setProPrice();


//        new HouseInfoProvider().setHouseDataInfo(getQueryRunner(),"35396");
//        new HouseInfoProvider().resetProperPriceDate(getQueryRunner(),"35396");

        String[] dd=new String[]{
                "43139","43046","43008","41917","42811","42791","42691","42731","42752","42575","41667","42491","42331","42159","39226","39134","39077","40094","40084","42071","41115","41935","41723","41739","41733","41686","41491","41512","41294","38937","29751","41431","41271","41191","41232","40892","41152","40532","40551","40160","40653","40915","40766","37666","40732","40512","39174","40611","40549","40274","40312","40314","40184","40192","39113","40015","39960","40052","39920","39192","39987","39612","39635","39881","39034","37713","39565","39377","39688","39557","39452","19736","38794","37756","38965","38918","37641","38456","38436","38779","38542","38842","38728","38698","38757","38187","38676","33636","38332","37725","38287","25038","37156","37089","37316","36863","37895","33888","37584","37445","37381","37897","36765","19864","37949","37186","37802","37579","-10000748","25857","37194","35658","37056","34176","36536","32484","36918","24982","-10000838","36788","35520","36856","36895","36658","-10000677","32456","32631","36416","36733","36823","36538","36496","36418","31086","36620","36216","35709","19862","20245","36296","36351","33736","35809","36005","35916","33608","35959","35785","35494","35573","35636","32768","35226","34337","35156","35196","32993","35318","34731","34843","34841","34943","35107","34527","34355","34516","34522","34705","34541","34198","33872","31728","32508","32559","33900","33655","33738","32935","32635","-10000528","33506","33124","33374","33171","-10000870","33184","33231","33056","32894","32816","31494","32696","32338","32723","32115","32234","32336","31914","30974","32119","32172","29554","31583","31991","31954","31440","27091","22673","31234","29449","31267","28924","31068","30992","30915","30708","30839","30674","30610","30434","30436","30304","30260","30194","30209","29927","29794","30041","29972","-10000591","30164","29835","29714","29352","29346","29501","29120","-10000493","29483","29220","27238","28744","27256","29111","29092","28865","28757","28239","27474","25358","23512","28606","28651","28366","28242","28192","28056","24767","27991","28161","27901","27993","27950","27899","27424","27750","27776","27469","27789","27321","27196","-10000296","20240","26427","26946","27107","23218","22198","26841","26755","26546","23464","26292","26333","26298","26312","23717","25743","25750","26137","26080","26146","25633","25650","25771","25913","23379","25253","25710","24741","25044","25033","24959","19708","25489","24823","25104","23917","24689","23935","24012","24504","24623","22366","22312","22269","22452","24219","23655","21820","24014","24028","24043","-10000882","23912","23785","22899","23539","23393","21531","23041","-10000531","23223","23248","23172","23114","22989","22252","22812","22887","-10000821","22454","22535","22563","22314","22189","22140","22164","20653","-10000741","22020","21851","21853","20447","21161","21700","-10000142","21513","21587","20625","21585","20021","21281","20851","20575","20573","20242","21195","20781","20762","20697","20884","20251","20405","20113","20023","20043","20055","19815","19786","-10000626","19265","19615","19764","-10000415","19474","19531","-10000861","19323","-10000444","19271","-10000794","-10000583","-10000847","-10000796","18983","-10000846","-10000877","-10000876","-10000845","26274","19234","-10000840","-10000867","-10000881","-10000891","-10000875","-10000901","-10000804","-10000777","-10000795","-10000749","-10000801","-10000815","-10000813","-10000789","-10000757","-10000647","-10000905","-10000786","-10000799","-10000309","-10000697","-10000695","-10000732","-10000669","-10000705","-10000719","-10000703","-10000688","-10000662","-10000633","-10000663","-10000664","-10000565","-10000561","-10000578","-10000577","-10000571","-10000568","-10000569","-10000443","-10000412","-10000435","-10000434","-10000376","-10000374","-10000393","-10000131","-10000135","-10000390","-10000327","-10000378","-10000346","-10000086","-10000344","-10000345","-10000307","-10000481","-10000395","-10000533","-10000497","-10000273","-10000475","-10000491","-10000447","-10000489","-10000469","-10000274","-10000208","-10000177","-10000291","-10000204","-10000187","-10000011","-10000199","-10000181","-10000153","-10000269","-10000012","-10000270","-10000144","-10000161","-10000191","-10000140","-10000136","-10000245","-10000252","-10000214","-10000066","-10000033","-10000063","-10000055"

        };
        for(String d:dd){
            new HouseInfoProvider().setHouseDataInfo(getQueryRunner(),d);
//            new HouseInfoProvider().resetProperPriceDate(getQueryRunner(),d);
        }

//        new HouseInfoProvider().checkHouse();
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
                System.out.println("========pid=================错误"+pid);
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

    /**
     *错误33738 33655 25743 23539 22020  到18983
     * ["43139","43046","43008","41917","42811","42791","42691","42731","42752",
     * "42575","41667","42491","42331","42159","39226","39134","39077","40094","40084",
     * "42071","41115","41935","41723","41739","41733","41686","41491","41512","41294",
     * "38937","29751","41431","41271","41191","41232","40892","41152","40532","40551",
     * "40160","40653","40915","40766","37666","40732","40512","39174","40611","40549",
     * "40274","40312","40314","40184","40192","39113","40015","39960","40052","39920",
     * "39192","39987","39612","39635","39881","39034","37713","39565","39377","39688",
     * "39557","39452","19736","38794","37756","38965","38918","37641","38456","38436",
     * "38779","38542","38842","38728","38698","38757","38187","38676","33636","38332",
     * "37725","38287","25038","37156","37089","37316","36863","37895","33888","37584",
     * "37445","37381","37897","36765","19864","37949","37186","37802","37579","-10000748",
     * "25857","37194","35658","37056","34176","36536","32484","36918","24982","-10000838",
     * "36788","35520","36856","36895","36658","-10000677","32456","32631","36416","36733",
     * "36823","36538","36496","36418","31086","36620","36216","35709","19862","20245",
     * "36296","36351","33736","35809","36005","35916","33608","35959","35785","35494",
     * "35573","35636","32768","35226","34337","35156","35196","32993","35318","34731",
     * "34843","34841","34943","35107","34527","34355","34516","34522","34705","34541",
     * "34198","33872","31728","32508","32559","33900","33655","33738","32935","32635","-10000528","33506","33124","33374","33171",
     * "-10000870","33184","33231","33056","32894","32816","31494","32696","32338","32723","32115","32234","32336","31914","30974","32119",
     * "32172","29554","31583","31991","31954","31440","27091","22673","31234","29449","31267","28924","31068","30992","30915","30708","30839",
     * "30674","30610","30434","30436","30304","30260","30194","30209","29927","29794","30041","29972","-10000591","30164","29835","29714","29352",
     * "29346","29501","29120","-10000493","29483","29220","27238","28744","27256","29111","29092","28865","28757","28239","27474","25358","23512","28606",
     * "28651","28366","28242","28192","28056","24767","27991","28161","27901","27993","27950","27899","27424","27750","27776","27469","27789","27321","27196",
     * "-10000296","20240","26427","26946","27107","23218","22198","26841","26755","26546","23464","26292","26333","26298","26312","23717","25743","25750",
     * "26137","26080","26146","25633","25650","25771","25913","23379","25253","25710","24741","25044","25033","24959","19708","25489","24823","25104","23917",
     * "24689","23935","24012","24504","24623","22366","22312","22269","22452","24219","23655","21820","24014","24028","24043",
     * "-10000882","23912","23785","22899","23539","23393","21531","23041","-10000531","23223","23248","23172","23114","22989",
     * "22252","22812","22887","-10000821","22454","22535","22563","22314","22189","22140","22164","20653","-10000741","22020",
     * "21851","21853","20447","21161","21700","-10000142","21513","21587","20625","21585","20021","21281","20851","20575","20573","
     * 20242","21195","20781","20762","20697","20884","20251","20405","20113","20023","20043","20055","19815","19786","-10000626",
     * "19265","19615","19764","-10000415","19474","19531","-10000861","19323","-10000444","19271","-10000794","-10000583","-10000847",
     * "-10000796","18983","-10000846","-10000877","-10000876","-10000845","26274","19234","-10000840","-10000867","-10000881","-10000891",
     * "-10000875","-10000901","-10000804","-10000777","-10000795","-10000749","-10000801","-10000815","-10000813","-10000789","-10000757",
     * "-10000647","-10000905","-10000786","-10000799","-10000309","-10000697","-10000695","-10000732","-10000669","-10000705","-10000719",
     * "-10000703","-10000688","-10000662","-10000633","-10000663","-10000664","-10000565","-10000561","-10000578","-10000577","-10000571","-10000568",
     * "-10000569","-10000443","-10000412","-10000435","-10000434","-10000376","-10000374","-10000393","-10000131","-10000135","-10000390","-10000327",
     * "-10000378","-10000346","-10000086","-10000344","-10000345","-10000307","-10000481","-10000395","-10000533","-10000497","-10000273","-10000475",
     * "-10000491","-10000447","-10000489","-10000469","-10000274","-10000208","-10000177","-10000291","-10000204","-10000187","-10000011","-10000199",
     * "-10000181","-10000153","-10000269","-10000012","-10000270",
     * "-10000144","-10000161","-10000191","-10000140","-10000136","-10000245","-10000252","-10000214","-10000066","-10000033","-10000063","-10000055"]
     * 检查是否遗漏了房子
     * @return
     */
   public List<String> checkHouse(){
       QueryRunner queryRunner=getQueryRunner();

       List<String> strings = new ArrayList<>();
       try{
           String sql="select id from house_info";
           List<House> ids = (List<House>) queryRunner.query(sql ,new BeanListHandler(House.class));
           for(House house:ids){
               try{
                   String countSql = "select count(*) as totalCount from m_house where houseId="+house.getId();
                   List<Map<String, Object>> countList = queryRunner.query(countSql, new MapListHandler());
                   long totalCount = (long) countList.get(0).get("totalCount");
                   if(totalCount==0){
                       strings.add(house.getId());
                   }
               }catch (Exception ex){
                   System.out.println("check 失败,id is:"+house.getId());
               }

           }
       }catch (Exception ex){
           ex.printStackTrace();
       }
       System.out.println(JsonUtils.toJson(strings));
       return strings;
   }


}
