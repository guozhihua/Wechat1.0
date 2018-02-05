package xchat.sys;

/**
 * 任务类型枚举
 *
 * 班级任务---200以上code
 * Created by :Guozhihua
 * Date： 2017/3/9.
 */
public enum MessageType {

    HUANG_JIN_DAREN("100","黄金答人答题任务"),
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
