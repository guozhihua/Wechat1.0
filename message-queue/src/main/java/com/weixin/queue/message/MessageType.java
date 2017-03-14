package com.weixin.queue.message;

/**
 * 任务类型枚举
 *
 * 班级任务---200以上code
 * Created by :Guozhihua
 * Date： 2017/3/9.
 */
public enum MessageType {
    CLASS_REPORT("201","班级报告统计"),
    CLASS_MODULE("202","班级模块错误掌握情况统计"),
    ClASS_WRONG("203","班级错题统计"),
    ClASS_WRONG_KNOWLEDGE("204","班级错误知识点统计")
    ;
    private  String typeCode;
    private String typeDescription;

    MessageType(String typeCode, String typeDescription) {
        this.typeCode = typeCode;
        this.typeDescription = typeDescription;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }
}
