package xchat.pojo;

import xchat.sys.SuperVO;

import java.util.Date;

/**
 * 数据字典
 * Created by 志华 on 2018/3/8.
 */
public class DataDic extends SuperVO {

    private Integer id;//

    private java.util.Date createTime;//

    private String code ;

    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
