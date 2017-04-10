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
 * 全量索引创建处理类
 *
 * @author Jianpin.Li
 * 2014年7月17日 下午2:50:47
 */
public class FullIndexCreator {

    private static final Logger logger = LoggerFactory.getLogger(FullIndexCreator.class);

    private static final String KEY_RIVER_NAME = "riverName";
    private static final String KEY_RIVER_INFO = "riverInfo";

    private FullIndexCreator() {}

    private static class BatchIndexCreatorHolder {
        private static FullIndexCreator creator = new FullIndexCreator();
    }

    public static FullIndexCreator getInstance() {
        return BatchIndexCreatorHolder.creator;
    }

    /**
     * 获取river配置信息
     * @author Jianpin.Li
     * @return
     */
    private JSONArray getRiverConf() throws Exception {
        JSONArray riverArr = new JSONArray();
        try {
            String riverStr = FileUtils.readFileToString(new File(FullIndexCreator.class.getResource("/es/river.json").getFile()));
            if (StringUtils.isNotEmpty(riverStr)) {
                riverArr = JSONArray.parseArray(riverStr);
            }
        } catch (IOException e) {
            throw new Exception("读取river.json配置文件错误！", e);
        }

        return riverArr;
    }

    /**
     * 创建river
     * @author Jianpin.Li
     * @throws Exception
     */
    public void createRiverByJson() throws Exception {
        try {
            JSONArray riverArr = this.getRiverConf();
            if (riverArr.size() > 0) {
                JSONObject riverItem;
                for (int i = 0; i < riverArr.size(); i++) {
                    riverItem = riverArr.getJSONObject(i);
                    boolean isCreated = ESHelper.getYunInstance().createRiver(riverItem.getString(KEY_RIVER_NAME), riverItem.getJSONObject(KEY_RIVER_INFO).toString());
                    if (!isCreated) {
                        logger.error("river【" + riverItem.getString(KEY_RIVER_NAME) + "】创建失败！");
                    }
                }
            }

        } catch (Exception e) {
            throw new Exception("创建搜索的River失败！", e);
        }

    }

    /**
     * 删除river
     * @author Jianpin.Li
     * @throws Exception
     */
    public void deleteRiverByJson() throws Exception {
        try {
            JSONArray riverArr = this.getRiverConf();
            if (riverArr.size() > 0) {
                JSONObject riverItem;
                for (int i = 0; i < riverArr.size(); i++) {
                    riverItem = riverArr.getJSONObject(i);
                    boolean deleted = ESHelper.getYunInstance().delRiver(riverItem.getString(KEY_RIVER_NAME));
                    if (!deleted) {
                        logger.error("river【" + riverItem.getString(KEY_RIVER_NAME) + "】删除失败！");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("删除搜索的River失败！", e);
        }
    }

    public static void main(String[] args) {
        try {
            FullIndexCreator.getInstance().deleteRiverByJson();
            FullIndexCreator.getInstance().createRiverByJson();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
