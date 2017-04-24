package com.weixin.yj.search.mappings;

import com.weixin.yj.search.setting.EsMappingConst;
import com.weixin.yj.search.setting.EsMappingJsonVo;
import com.weixin.yj.search.setting.EsMappingPropertiesVo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/4/14.
 */
public class HouseObjPriceMapping {
    public static EsMappingJsonVo getMapping() {
        EsMappingJsonVo esMappingJsonVo = new EsMappingJsonVo();
        esMappingJsonVo.setIndex("house");
        esMappingJsonVo.setType("price_info");

        //设置属性
        Map<String, EsMappingPropertiesVo> proMap = new HashMap<String, EsMappingPropertiesVo>();
        //小区id
        proMap.put("id",new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_INTEGER));
        //houseName
        proMap.put("houseName",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_NOT_ANALYZED, EsMappingConst.PRO_TYPE_STRING));
        proMap.put("areaName",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_NOT_ANALYZED, EsMappingConst.PRO_TYPE_STRING));
        proMap.put("address",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("startTime",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("endTime",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("floor",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("floorNum",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("price",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("sumMianji",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("useMianji",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("gongtanMianji",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("status",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("forUsed",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));
        proMap.put("kaifashang",new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK));

        Map<String, Object> mapping = new HashMap<String, Object>();
        mapping.put("properties", proMap);
        esMappingJsonVo.setMapping(mapping);

        return  esMappingJsonVo;
    }

}
