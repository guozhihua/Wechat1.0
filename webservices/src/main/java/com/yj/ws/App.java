package com.yj.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by :Guozhihua
 * Date： 2017/4/20.
 */
@XmlRootElement(name="app")
@XmlAccessorType(XmlAccessType.FIELD)
public class App implements Serializable {
    private  int id ;
    private  String name;

    public App() {
    }

    public App(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
