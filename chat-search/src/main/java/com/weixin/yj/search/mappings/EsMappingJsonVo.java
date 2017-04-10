package com.weixin.yj.search.mappings;

import java.util.Map;

/**
 * ES MAPPING
 * 
 * @author liangzh
 * 
 * 2014年12月12日 上午9:30:08
 *
 */
public class EsMappingJsonVo {

    private String index;
    private String type;
    private Map<String, Object> mapping;

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, Object> mapping) {
        this.mapping = mapping;
    }
}
