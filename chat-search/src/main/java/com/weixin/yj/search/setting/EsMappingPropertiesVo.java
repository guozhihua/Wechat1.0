package com.weixin.yj.search.setting;

public class EsMappingPropertiesVo {
    private String index;
    private String type;
    private boolean include_in_all;
    private String analyzer;

    /**
     * int/long类型
     * 
     */
    public EsMappingPropertiesVo(String type) {
        super();
        this.type = type;
        this.include_in_all = false;
    }

    /**
     * 不需要分词器
     * 
     * @param index
     * @param type
     */
    public EsMappingPropertiesVo(String index, String type) {
        super();
        this.index = index;
        this.type = type;
        this.include_in_all = false;
    }

    /**
     * 需要分词器
     * 
     * @param index
     * @param type
     * @param analyzer
     */
    public EsMappingPropertiesVo(String index, String type, String analyzer) {
        super();
        this.index = index;
        this.type = type;
        this.analyzer = analyzer;
        this.include_in_all = false;
    }

    public EsMappingPropertiesVo(String index, String type, String analyzer, boolean include_in_all) {
        super();
        this.index = index;
        this.type = type;
        this.analyzer = analyzer;
        this.include_in_all = include_in_all;
    }

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

    public boolean isInclude_in_all() {
        return this.include_in_all;
    }

    public void setInclude_in_all(boolean include_in_all) {
        this.include_in_all = include_in_all;
    }

    public String getAnalyzer() {
        return this.analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }

}
