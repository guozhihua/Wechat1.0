package com.weixin.utils.tyfdc;

import com.weixin.utils.util.HttpRequest;
import com.weixin.utils.util.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/4/13.
 */
public class HouseInfoProvider {
    private static final String houseList_url = "http://wsfc.nsae.tyfdc.gov.cn/Firsthand/tyfc/publish/ProjListForPassed.do";
    private static final String houseinfo_url = "";

    public static void getHoueseList(String url, Map<String, Object> params) {
        String respose = HttpRequest.postRequest(url, JsonUtils.toJson(params));

    }

    public static void main(String[] args) {

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("ProName", "东");
            params.put("pageNo", 1);
            params.put("pageSize", 15);
            params.put("PerYear", "");
            params.put("PerFlowNO", "");
            params.put("OrgName", "");
            params.put("ProAddress", "");
            params.put("ProType", 0);
            params.put("PerType", 0);
            params.put("HouseType", 0);
            params.put("Region", 0);
            String respose = HttpRequest.postFormRequest(houseList_url, params);
            System.out.println(respose);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    /**
     * PerYear:
     PerFlowNO:
     PerType:0
     ProType:0
     ProName:东
     OrgName:
     HouseType:0
     Region:0
     ProAddress:
     pageNo:1
     pageSize:15
     */
    private  static  String getHouseList(Map<String,Object> paramMap){
        try {
            Map<String, Object> params = new HashMap<>();
            //项目的名称
            params.put("ProName", "东");
            params.put("pageNo", 1);
            params.put("pageSize", 15);
            //预售证年
            params.put("PerYear", "");
            params.put("PerFlowNO", "");
            params.put("OrgName", "");
            //项目地址
            params.put("ProAddress", "");
            params.put("ProType", 0);
            params.put("PerType", 0);
            params.put("HouseType", 0);
            params.put("Region", 0);
            String respose = HttpRequest.postFormRequest(houseList_url, params);
            return  respose;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * 根据房子的ID获取房子的基本信息
     * @param map
     * @return
     */
    public static String getHouseBaseInfo(Map<String,Object> map){
        return  null;
    }
    public static  final String getHouseUnSaleInfo(){
        return  null;
    }

}
