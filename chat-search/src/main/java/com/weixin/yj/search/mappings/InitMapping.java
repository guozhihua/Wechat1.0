package com.weixin.yj.search.mappings;

import com.alibaba.fastjson.JSONArray;
import com.weixin.yj.search.setting.ESMappingCreator;

/**
 * Created by wei on 15-3-2.
 */
public class InitMapping {
    public static void main(String[] args) {

        JSONArray courseJson = new JSONArray();
        courseJson.add(CourseMapping.getMapping());
        ESMappingCreator.getYunInstance().deleteMappingByJsonArray(courseJson);
        System.out.println("--------------准备初始化课程索引--------------");
        ESMappingCreator.getYunInstance().createMappingByJsonArray(courseJson);
        System.out.println("--------------初始化课程索引完毕--------------");
        System.exit(0);
    }

}
