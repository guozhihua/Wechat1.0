package com.weixin.yj.search;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.client.Client;

/**
 * 索引目录操作类
 *
 * 2014年7月4日 下午3:42:11
 */
public class IndicesOperations {
    private Client client;
    public void reSetClient(Client newClient){
        this.client =newClient;
    }

    public IndicesOperations(Client client) {
        this.client = client;
    }

    /**
     * 判断索引是否存在
     * @author Jianpin.Li
     * @param name 索引名称
     * @return
     */
    public boolean checkIndexExists(String name) {
        IndicesExistsResponse response = this.client.admin().indices().prepareExists(name).execute().actionGet();
        return response.isExists();
    }

    /**
     * 判断index下的type是否存在
     * @author Jianpin.Li
     * @param index 索引名称
     * @param type 类型名称
     * @return
     */
    public boolean checkTypeExists(String index, String type) {
        TypesExistsResponse response = this.client.admin().indices().prepareTypesExists(index).setTypes(type).execute().actionGet();
        return response.isExists();
    }

    /**
     * 创建索引
     * @author Jianpin.Li
     * @param name 索引名称
     */
    public void createIndex(String name) {
        this.client.admin().indices().prepareCreate(name).execute().actionGet();
    }

    /**
     * 删除索引
     * @author Jianpin.Li
     * @param name 索引名称
     */
    public void deleteIndex(String name) {
        this.client.admin().indices().prepareDelete(name).execute().actionGet();
    }

    /**
     * 关闭索引
     * @author Jianpin.Li
     * @param name 索引名称
     */
    public void closeIndex(String name) {
        this.client.admin().indices().prepareClose(name).execute().actionGet();
    }

    /**
     * 打开索引
     * @author Jianpin.Li
     * @param name 索引名称
     */
    public void openIndex(String name) {
        this.client.admin().indices().prepareOpen(name).execute().actionGet();
    }
}