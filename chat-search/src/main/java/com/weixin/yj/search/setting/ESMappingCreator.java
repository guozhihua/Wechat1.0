package com.weixin.yj.search.setting;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weixin.yj.search.ESHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * mapping管理处理类
 *
 * @author Jianpin.Li
 * 2014年7月17日 下午2:57:19
 */
public class ESMappingCreator {

    private static final Logger logger = LoggerFactory.getLogger(ESMappingCreator.class);
    private static final String KEY_INDEX = "index";
    private static final String KEY_TYPE = "type";
    private static final String KEY_MAPPING = "mapping";

    private ESHelper esHelper;

    private ESMappingCreator() {
    }

    private static class ESMappingCreatorHolder {
        private static ESMappingCreator creator = new ESMappingCreator();
        private static ESHelper esHelper = ESHelper.getInstance();
    }


    /**
     * 获取知识汇聚的实例
     * @return
     */
    public static ESMappingCreator getInstance() {
        ESMappingCreator creator = ESMappingCreatorHolder.creator;
        creator.esHelper = ESMappingCreatorHolder.esHelper;
        return creator;
    }


    /**
     * 根据jsonArray数据创建mapping
     * @author rl
     * @param mappingJson
     */
    public void createMappingByJsonArray(JSONArray mappingJson) {

        try {
            JSONArray mappingArr = mappingJson;
            if (mappingArr.size() > 0) {
                JSONObject mappingItem = null;
                for (int i = 0; i < mappingArr.size(); i++) {
                    mappingItem = mappingArr.getJSONObject(i);

                    createMappingItem(mappingItem);
                }
            }
        } catch (Exception e) {
            logger.error("根据【" + mappingJson + "】创建mapping发生例外！", e);
        }
    }

    /**
     * 根据mappingItem创建索引库
     * @param mappingItem
     */
    private void createMappingItem(JSONObject mappingItem) {
        logger.debug("createMappingItem{}",mappingItem);
        boolean created = esHelper.createMapping(mappingItem.getString(KEY_INDEX), mappingItem.getString(KEY_TYPE), mappingItem.getJSONObject(KEY_MAPPING).toString());
        if (!created) {
            logger.error("index为【" + mappingItem.getString(KEY_INDEX) + "】，type为【" + mappingItem.getString(KEY_TYPE) + "】的mapping失败！");
        }
    }





}
