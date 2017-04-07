package com.weixin.yj.search.setting;

import java.util.List;

/**
 * 高亮配置类
 *
 * @author Jianpin.Li
 * 2014年8月14日 下午3:59:57
 */
public class HighLightConf {

    /** 高亮的左标签 */
    private String preTag;
    /** 高亮的右标签 */
    private String postTag;
    /** 高亮字段 */
    private List<String> fieldList;

    public String getPostTag() {
        return this.postTag;
    }

    public void setPostTag(String postTag) {
        this.postTag = postTag;
    }

    public String getPreTag() {
        return this.preTag;
    }

    public void setPreTag(String preTag) {
        this.preTag = preTag;
    }

    public List<String> getFieldList() {
        return this.fieldList;
    }

    public void setFieldList(List<String> fieldList) {
        this.fieldList = fieldList;
    }

}
