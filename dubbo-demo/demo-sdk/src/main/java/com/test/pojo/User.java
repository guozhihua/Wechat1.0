package com.test.pojo;

import java.io.Serializable;

/**
 * Created by :Guozhihua
 * Date： 2017/6/30.
 */
public class User implements Serializable {
    private  int id ;

    private  String userName;

    public User() {
    }

    public User(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
