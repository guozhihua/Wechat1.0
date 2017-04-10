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
        private static ESHelper esHelper = ESHelper.getYunInstance();
    }

    private static class ESMappingCreatorWebHolder {
        private static ESMappingCreator creator = new ESMappingCreator();
        private static ESHelper esHelper = ESHelper.getLocalInstance();
    }

    /**
     * 获取知识汇聚的实例
     * @return
     */
    public static ESMappingCreator getYunInstance() {
        ESMappingCreator creator = ESMappingCreatorHolder.creator;
        creator.esHelper = ESMappingCreatorHolder.esHelper;
        return creator;
    }

    /**
     * 获取本地搜索服务的实例
     * @return
     */
    public static ESMappingCreator getWebtInstance() {
        ESMappingCreator creator = ESMappingCreatorWebHolder.creator;
        creator.esHelper = ESMappingCreatorWebHolder.esHelper;
        return creator;
    }

    /**
     * 获取mapping.json的配置信息
     * @author Jianpin.Li
     * @param mappingUrl
     * @return
     * @throws Exception
     */
    private static JSONArray getMappingConf(String mappingUrl) throws Exception {
        JSONArray mappingArr = new JSONArray();
        try {
            String mappingStr = FileUtils.readFileToString(new File(FullIndexCreator.class.getResource(mappingUrl).getFile()));
            if (StringUtils.isNotEmpty(mappingStr)) {
                mappingArr = JSONArray.parseArray(mappingStr);
            }
        } catch (IOException e) {
            throw new Exception("读取【" + mappingUrl + "】配置文件错误！", e);
        }

        return mappingArr;
    }

    /**
     * 根据配置的json数据创建mapping
     * @author Jianpin.Li
     * @param mappingUrl
     */
    public void createMappingByJson(String mappingUrl) {

        try {
            JSONArray mappingArr = getMappingConf(mappingUrl);
            if (mappingArr.size() > 0) {
                JSONObject mappingItem = null;
                for (int i = 0; i < mappingArr.size(); i++) {
                    mappingItem = mappingArr.getJSONObject(i);

                    createMappingItem(mappingItem);
                }
            }
        } catch (Exception e) {
            logger.error("根据【" + mappingUrl + "】创建mapping发生例外！", e);
        }
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

    /**
     * 创建指定索引库
     * @param mappingUrl
     * @param index
     * @param type
     */
    public void createMappingByJson(String mappingUrl, String index, String type) {
        try {
            JSONArray mappingArr = getMappingConf(mappingUrl);
            if (mappingArr.size() > 0) {
                JSONObject mappingItem = null;
                for (int i = 0; i < mappingArr.size(); i++) {
                    mappingItem = mappingArr.getJSONObject(i);

                    if(mappingItem.getString(KEY_INDEX).equals(index) && mappingItem.getString(KEY_TYPE).equals(type)) {
                        createMappingItem(mappingItem);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("根据【" + mappingUrl + "】创建index为【" + index + "】,type为【" + type + "】mapping发生例外！", e);
        }
    }

    /**
     * 根据配置的json数据删除mapping
     * @author Jianpin.Li
     * @throws Exception
     */
    public void deleteMappingByJson(String mappingUrl) {
        try {
            JSONArray mappingArr = getMappingConf(mappingUrl);
            if (mappingArr.size() > 0) {
                JSONObject mappingItem = null;
                for (int i = 0; i < mappingArr.size(); i++) {
                    mappingItem = mappingArr.getJSONObject(i);

                    deleteByMappingItem(mappingItem);
                }
            }
        } catch (Exception e) {
            logger.error("根据【" + mappingUrl + "】删除mapping发生例外！", e);
        }
    }


    /**
     * 根据配置的mappingJson数据删除mapping
     * @author rl
     * @throws Exception
     */
    public void deleteMappingByJsonArray(JSONArray mappingJson) {
        try {
            JSONArray mappingArr = mappingJson;
            if (mappingArr.size() > 0) {
                JSONObject mappingItem = null;
                for (int i = 0; i < mappingArr.size(); i++) {
                    mappingItem = mappingArr.getJSONObject(i);

                    deleteByMappingItem(mappingItem);
                }
            }
        } catch (Exception e) {
            logger.error("根据【" + mappingJson + "】删除mapping发生例外！", e);
        }
    }

    /**
     * 根据mappingItem删除索引库
     * @param mappingItem
     */
    private void deleteByMappingItem(JSONObject mappingItem) {
//        //不存在索引则创建一个
//        if (!esHelper.existIndex(mappingItem.getString(KEY_INDEX))) {
//            esHelper.createIndex(mappingItem.getString(KEY_INDEX));
//
//            return;
//        } else {
//            //不存在对应的类型则执行删除
//            if (!esHelper.existType(mappingItem.getString(KEY_INDEX), mappingItem.getString(KEY_TYPE))) {
//                return;
//            }
//        }
//
//        boolean deleted = esHelper.delMapping(mappingItem.getString(KEY_INDEX), mappingItem.getString(KEY_TYPE));
//        if (!deleted) {
//            logger.error("index为【" + mappingItem.getString(KEY_INDEX) + "】，type为【" + mappingItem.getString(KEY_TYPE) + "】的mapping失败！");
//        }
    }

    /**
     * 删除指定索引库
     * @param mappingUrl
     * @param index
     * @param type
     */
    public void deleteMappingByJson(String mappingUrl, String index, String type) {
        try {
            if(StringUtils.isEmpty(index) || StringUtils.isEmpty(type)) {
                logger.warn("删除索引时没有设置index或type！");
                return;
            }

            JSONArray mappingArr = getMappingConf(mappingUrl);
            if (mappingArr.size() > 0) {
                JSONObject mappingItem = null;
                for (int i = 0; i < mappingArr.size(); i++) {
                    mappingItem = mappingArr.getJSONObject(i);

                    if(mappingItem.getString(KEY_INDEX).equals(index) && mappingItem.getString(KEY_TYPE).equals(type)) {
                        deleteByMappingItem(mappingItem);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("根据【" + mappingUrl + "】删除mapping发生例外！", e);
        }
    }

}
