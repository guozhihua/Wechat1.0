package com.weixin.yj.search.mappings;

import com.alibaba.fastjson.JSONArray;
import com.weixin.yj.search.ESHelper;
import com.weixin.yj.search.setting.ESMappingCreator;
import com.weixin.yj.search.setting.EsMappingJsonVo;

/**
 * Created by wei on 15-3-2.
 */
public class InitHousePriceMapping {
    public static void main(String[] args) {
        try {
            ESHelper esHelper = ESHelper.getInstance();
            JSONArray courseJson = new JSONArray();
            EsMappingJsonVo mappingJsonVo = HouseObjPriceMapping.getMapping();
            courseJson.add(mappingJsonVo);
            //数据库是不是存在
            if (!esHelper.existIndex(mappingJsonVo.getIndex())) {
                esHelper.createIndex(mappingJsonVo.getIndex());
            }
            //先删除对应的mapping
            System.out.println("--------------准备初始化课程索引--------------");
            ESMappingCreator.getInstance().createMappingByJsonArray(courseJson);

            System.out.println("--------------初始化课程索引完毕--------------");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        System.exit(0);
    }

}
