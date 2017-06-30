package com.test.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by :Guozhihua
 * Date： 2016/11/4.
 */
public class Menu implements Serializable {
    private int menuId;

    //父级菜单ID
    private int parentId;
    //是否叶子几点
    private boolean isLeaf;
    // 排序号
    private float sortNum;
    //菜单名称
    private String menuName;

    private String menuUrl;
    private Date createTime;
    private boolean isSystem;


    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public float getSortNum() {
        return sortNum;
    }

    public void setSortNum(float sortNum) {
        this.sortNum = sortNum;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }


}
