package com.weixin.yj.search.setting;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by LDB on 15-11-27.
 */
public class IndexData implements Serializable {

    private String index;
    private String type;
    private  Map<String, Object> fields;
    private boolean  refresh;

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    public IndexData(String index, String type, Map<String, Object> fields, boolean refresh){
        this.index =index;
        this.type =type;
        this.fields =fields;
        this.refresh =refresh;
    }
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }
}
