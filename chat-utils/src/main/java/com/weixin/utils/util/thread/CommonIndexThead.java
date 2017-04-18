package com.weixin.utils.util.thread;

import org.apache.commons.dbutils.QueryRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 索引多线程创建抽象类
 * Created by ljp on 2015/2/2.
 */
public abstract class CommonIndexThead extends Thread {

    protected CountDownLatch latch;
    protected long startPage;
    protected long endPage;
    protected QueryRunner runner;
    protected String sql;
    protected String contType;
    protected String index ;
    protected String  type;
    protected Map<String,Object> paramMap=new HashMap<>();

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

    public String getClientCode() {
        return clientCode;
    }

    protected String clientCode;

    public CommonIndexThead() {
    }

    public CommonIndexThead(String clientCode, CountDownLatch latch, long startPage, long endPage, QueryRunner runner, String sql, String contType) {
        this.latch = latch;
        this.startPage = startPage;
        this.endPage = endPage;
        this.runner = runner;
        this.sql = sql;
        this.contType = contType;
        this.clientCode=clientCode;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public long getStartPage() {
        return startPage;
    }

    public void setStartPage(long startPage) {
        this.startPage = startPage;
    }

    public long getEndPage() {
        return endPage;
    }

    public void setEndPage(long endPage) {
        this.endPage = endPage;
    }

    public QueryRunner getRunner() {
        return runner;
    }

    public void setRunner(QueryRunner runner) {
        this.runner = runner;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getContType() {
        return contType;
    }

    public void setContType(String contType) {
        this.contType = contType;
    }
}
