package com.weixin.yj.search.mappings;

import com.weixin.yj.search.setting.EsMappingConst;
import com.weixin.yj.search.setting.EsMappingJsonVo;
import com.weixin.yj.search.setting.EsMappingPropertiesVo;
import com.weixin.yj.search.setting.TigerEsConst;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/4/14.
 */
public class HouseMapping {
    public static EsMappingJsonVo getMapping() {
        EsMappingJsonVo esMappingJsonVo = new EsMappingJsonVo();
        esMappingJsonVo.setIndex("house");
        esMappingJsonVo.setType("base_info");

        //设置属性
        Map<String, EsMappingPropertiesVo> proMap = new HashMap<String, EsMappingPropertiesVo>();
        //小区id
        proMap.put("id",new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_INTEGER));
        //houseName
        proMap.put("houseName",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK_MAX_WORD, true));
        proMap.put("areaName",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK, true));
        proMap.put("houseType",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK, true));
        proMap.put("yushouCode",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("kaifashang",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("startTime",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("endTime",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("address",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("mianji",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("zhuangxiu",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("nuan",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("qi",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("water",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("junjia",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("shigong",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("remark",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));

        Map<String, Object> mapping = new HashMap<String, Object>();
        mapping.put("properties", proMap);
        esMappingJsonVo.setMapping(mapping);

        return  esMappingJsonVo;
    }

}
