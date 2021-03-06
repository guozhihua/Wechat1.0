package com.weixin.yj.search.setting;


import com.alibaba.fastjson.JSON;
import com.weixin.yj.search.User;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.net.InetAddress;
import java.util.*;

/**
 *
 * 简单测试类测试 ES集群基础功能
 * Created by :Guozhihua
 * Date： 2017/4/7.
 */
public class EsConnectionProvider {
    static final String  host = "192.168.1.65";
    static final  Integer port = 9301 ;

    public static void main(String[] args) {
        try{
            new EsConnectionProvider().testAddIndex();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    /**
            * 新增索引
    *
            * @throws Exception
    */
    public void testAddIndex() throws Exception {
//        Client client = getClient();
        Client client =ESClient.createTransportClient(null);
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setAreaName("天猫2222");
        user.setUserId(UUID.randomUUID() + "");
        user.setSchool("北京大学222222");
        Random random = new Random();
        user.setMobile("1770102" + random.nextInt(10000));
        try {
            IndexResponse response = client.prepareIndex("index", "user", user.getUserId())//参数说明： 索引，类型 ，_id
                    .setSource(jsonStr(user))//setSource可以传以上map string  byte[] 几种方式
                    .get();
            boolean created = response.isCreated();
            System.out.println("\r\n创建一条记录:" + created);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            client.close();
        }
    }

    /**
            * 查询单行
    */
    public void getIndexDataSingle() {
        //获取_id为1的类型
        Client client = getClient();
        try {
            GetResponse response1 = client.prepareGet("index", "user", "北京1").get();
            System.out.println();
            System.out.println("查询一条数据:" + JSON.toJSON(response1.getSourceAsMap()));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            client.close();
        }
    }

    /**
            * 查询多行
    */
    public void getIndexDataMuli() {
        Client client = getClient();
        try {
            //搜索
            SearchResponse response = client.prepareSearch("index")//可以同时搜索多个索引prepareSearch("index","index2")
                    .setTypes("user")//可以同时搜索多个类型
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.fuzzyQuery("areaName", "天猫"))                 // Query
                    .setPostFilter(QueryBuilders.rangeQuery("grade").from(0).to(18))     // Filter
                    .setFrom(0).setSize(20).setExplain(true)
                    .execute()
                    .actionGet();
            forSearchResponse(response);
            System.out.println();
            System.out.println("总共查询到有：" + response.getHits().getTotalHits());
            //多查询结果
            SearchRequestBuilder srb1 = client
                    .prepareSearch().setQuery(QueryBuilders.queryStringQuery("张三")).setSize(1);
            SearchRequestBuilder srb2 = client
                    .prepareSearch().setQuery(QueryBuilders.matchQuery("name", "张三")).setSize(1);

            MultiSearchResponse sr = client.prepareMultiSearch()
                    .add(srb1)
                    .add(srb2)
                    .execute().actionGet();

            long nbHits = 0;
            for (MultiSearchResponse.Item item : sr.getResponses()) {
                SearchResponse response1 = item.getResponse();
                forSearchResponse(response1);
                nbHits += response1.getHits().getTotalHits();
            }
            System.out.println();
            System.out.println("多查询总共查询到有：" + nbHits);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            client.close();
        }
    }


    /**
            * 单个删除索引数据
    */

    public void testDelIndex() {
        Client client = getClient();
        try {
            DeleteResponse response = client.prepareDelete("index", "user", "北京1").execute().actionGet();
            boolean isfound = response.isFound();
            System.out.println("\r\n查找并且删除的结果是："+isfound);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            client.close();
        }

    }


    /**
            * 单个更新索引数据
    */
    public void testUpdateIndex(){
        Client client = getClient();
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setAreaName("天猫");
        user.setUserId(UUID.randomUUID() + "");
        user.setSchool("北京大学");
        Random random = new Random();
        user.setMobile("1770102" + random.nextInt(10000));
        try {
            Map map = new HashMap();
            map.put("areaName","北京市海淀区上地9街87号212");
            map.put("school","北航空航天大学");
            UpdateResponse response = client.prepareUpdate("index", "user", "北京1").setDoc(map).get();
            boolean isfound = response.isCreated();
            System.out.println("\r\n 更新数据："+isfound);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            client.close();
        }



    }





    public void forSearchResponse(SearchResponse response) {
        for (SearchHit hit1 : response.getHits()) {
            Map<String, Object> source1 = hit1.getSource();
            if (!source1.isEmpty()) {
                for (Iterator<Map.Entry<String, Object>> it = source1.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry<String, Object> entry = it.next();
                    System.out.println(entry.getKey() + "=======" + entry.getValue());
                }
            }
        }
    }


    /**
            * 将对象通过JSONtoString转换成JSON字符串
    * 使用fastjson 格式化注解  在属性上加入 @JSONField(format="yyyy-MM-dd HH:mm:ss")
    *
            * @return
            */
    public String jsonStr(Object obj) {
        return JSON.toJSONString(obj);
    }

    private TransportClient getClient() {
        TransportClient client = null;
        Settings settings = Settings.builder()
                //指定集群名称
                .put("cluster.name", "es-custor")
                //探测集群中机器状态
                .put("client.transport.sniff", true).build();
        try {
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
            return client;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return client;
    }

}
