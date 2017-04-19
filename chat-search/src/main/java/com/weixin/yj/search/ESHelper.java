package com.weixin.yj.search;

import com.weixin.yj.search.index.SyncAddSearchData;
import com.weixin.yj.search.setting.ESClient;
import com.weixin.yj.search.setting.ESConst;
import com.weixin.yj.search.setting.HighLightConf;
import com.weixin.yj.search.setting.IndexData;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.InternalCardinality;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.transport.NodeNotConnectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 *
 * 对于搜索可以使用jdbc方式连接数据库，进行数据的同步。可以设计一个版本或者时间的字段，作为索引更新的一句。
 * 也可以编写shell脚本，加入linux crontab 做定时任务处理，达到实时索引的目的。
 * ElasticSearch基础工具类
 *
 * @author Jianpin.Li
 *         2014年7月4日 下午3:29:04
 */
public class ESHelper {
    private static final Logger logger = LoggerFactory.getLogger(ESHelper.class);

    private ESHelper() {

    }
    private String searchName;
    private Client client;
    private IndicesOperations io;


    private static Map<String, ESHelper> instanceMap = Collections.synchronizedMap(new HashMap<String, ESHelper>());

    private static ESHelper getInstance(String custorPrefix) {
        ESHelper esHelper = instanceMap.get(custorPrefix);
        if (esHelper == null) {
            esHelper = new ESHelper();
            esHelper.client = ESClient.createTransportClient(custorPrefix);
            esHelper.searchName=custorPrefix;
            esHelper.io = new IndicesOperations(esHelper.client);
            esHelper.searchName=custorPrefix;
            instanceMap.put(custorPrefix, esHelper);
        }

        return esHelper;
    }
    /**
     * 获取知识汇聚的搜索实例
     *
     * @return
     */
    public static ESHelper getInstance() {
        return getInstance(ESConst.CONF_ES_PREFIX_LOCAL);
    }
    /**
     * 删除索引
     * @param name
     */
    public void deleteIndex(String name){
        this.io.deleteIndex(name);
    }

    /**
     * 创建索引
     *
     * @param index 索引名称
     * @author Jianpin.Li
     */
    public void createIndex(String index) {
        this.io.createIndex(index);
    }







    private static long lastReInitTime=0;
    public void reInitInstance(String prefinxName) {
        if( (System.currentTimeMillis()-lastReInitTime)>30*60*1000L){
            logger.error("索引长连接出问题，尝试重新初始化连接");
            lastReInitTime=System.currentTimeMillis();
            ESHelper esHelper = getInstance(prefinxName);
            esHelper.client = ESClient.reConnectTransportClient(prefinxName, (TransportClient) esHelper.client);
            if(esHelper.io!=null){
                esHelper.io.reSetClient(esHelper.client);
            }else{
                esHelper.io = new IndicesOperations(esHelper.client);
            }
            esHelper.searchName=prefinxName;
        }
    }


    private void dealNoAvailableException(Exception e){
        if (e instanceof NoNodeAvailableException || e instanceof NodeNotConnectedException) {
            if (this.searchName!=null && this.searchName.equals(ESConst.CONF_ES_PREFIX_LOCAL)) {
                reInitInstance(ESConst.CONF_ES_PREFIX_LOCAL);
            }
            if (this.searchName!=null && this.searchName.equals(ESConst.CONF_ES_PREFIX_LOCAL)) {
                reInitInstance(ESConst.CONF_ES_PREFIX_LOCAL);
            }
        }
    }
    /**
     * 判断是否存在指定索引
     *
     * @param index 索引名称
     * @return 存在则返回true，如果连接异常也返回false
     * @author Jianpin.Li
     */
    public boolean existIndex(String index) {
        boolean flag = true;
        try {
            if (!this.io.checkIndexExists(index)) {
                flag = false;
                logger.error("index为【" + index + "】对应索引不存在！");
            }
        } catch (Exception e) {
            flag = false;
            logger.error("es节点连接异常！检查网络连接！", e);
            dealNoAvailableException(e);
        }

        return flag;
    }


    /**
     * 判断是否存在指定索引的type
     *
     * @param index 索引名称
     * @param type  类型名称
     * @return 存在则返回true，如果连接异常也返回false
     * @author Jianpin.Li
     */
    public boolean existType(String index, String type) {
        boolean flag = true;
        try {
            if (!this.io.checkTypeExists(index, type)) {
                flag = false;
                logger.error("index为【" + index + "】的索引对应的type【" + type + "】不存在！");
            }
        } catch (Exception e) {
            flag = false;
            logger.error("es节点连接异常！检查网络连接！", e);
            dealNoAvailableException(e);
        }

        return flag;
    }

    /**
     * 添加索引的document，不立即刷新
     *
     * @param index  索引名称
     * @param type   类型名称
     * @param fields 需要索引数据
     * @return 是否创建成功，成功为true
     * @author Jianpin.Li
     */
    public boolean addDoc(String index, String type, Map<String, Object> fields) {
        return this.addDoc(index, type, fields, false);
    }

    /**
     * 添加索引的document
     *
     * @param index  索引名称
     * @param type   类型名称
     * @param fields 需要索引数据
     * @return 是否创建成功，成功为true
     * @author Jianpin.Li
     */
    public boolean addDoc(String index, String type, Map<String, Object> fields, boolean isRefresh) {
        //如果是云搜索，统一改成异步方式处理
        if(ESConst.CONF_ES_PREFIX_LOCAL.equals(this.searchName)){
            IndexData syncData=new IndexData(index,type,fields,isRefresh);
            SyncAddSearchData.getInstance().addQueue(syncData);
            return true;
        }else{
            return addDocDirect(index,type,fields,isRefresh);
        }
    }


    public boolean addDocDirect(String index, String type, Map<String, Object> fields, boolean isRefresh){
        boolean created = true;

        //连接不成功或者索引不存在
        if (!this.existIndex(index)) {
            return false;
        }

        IndexResponse ir = null;
        try {
            ir = this.buildAddRequest(index, type, fields).setRefresh(isRefresh).execute().actionGet();
            return ir.isCreated();
        } catch (Exception e) {
            created = false;
            logger.error("添加index为【" + index + "】，type为【" + type + "】，id为【" + ir.getId() + "】的索引文档例外！", e);
        }

        return created;
    }
    /**
     * 构建添加索引的请求
     *
     * @param index
     * @param type
     * @param fields
     * @return
     * @author Jianpin.Li
     */
    public IndexRequestBuilder buildAddRequest(String index, String type, Map<String, Object> fields) {
        IndexRequestBuilder irb = this.client.prepareIndex(index, type);
        if (fields.get(ESConst.ID_FORK) != null) {
            irb.setId(fields.get(ESConst.ID_FORK).toString());
            fields.remove(ESConst.ID_FORK);
        }
        if (fields.get(ESConst.PARENT_FORK) != null) {
            irb.setParent(fields.get(ESConst.PARENT_FORK).toString());
            fields.remove(ESConst.PARENT_FORK);
        }

        irb.setSource(fields);

        return irb;
    }

    /**
     * 批量添加索引document
     *
     * @param index   索引名称
     * @param type    类型名称
     * @param docList 需要索引的数据列表，必须设置一个key为id，如果需要设置关联关系，可以设置一个key为parent
     * @return 是否批量创建成功，部分创建成功为false，只有全部创建成功才为true
     * @author Jianpin.Li
     */
    public boolean bulkAddDoc(String index, String type, List<Map<String, Object>> docList) {
        return this.bulkAddDoc(index, type, docList, false);
    }

    /**
     * 批量添加索引document
     *
     * @param index   索引名称
     * @param type    类型名称
     * @param docList 需要索引的数据列表，必须设置一个key为id，如果需要设置关联关系，可以设置一个key为parent
     * @return 是否批量创建成功，部分创建成功为false，只有全部创建成功才为true
     * @author Jianpin.Li
     */
    public boolean bulkAddDoc(String index, String type, List<Map<String, Object>> docList, boolean isRefresh) {
        boolean created = true;

        if (docList == null || docList.size() == 0) {
            //            logger.info("没有设置需要索引的数据!");
            return false;
        }

        //连接不成功或者索引不存在
        if (!this.existIndex(index)) {
            return false;
        }

        BulkRequestBuilder bulker = this.client.prepareBulk();
        Map<String, Object> fields;
        for (int i = 0; i < docList.size(); i++) {
            fields = docList.get(i);
            bulker.add(this.buildAddRequest(index, type, fields));
        }

        BulkResponse bulkResponse = bulker.setRefresh(isRefresh).execute().actionGet();
        if (bulkResponse.hasFailures()) {
            created = false;
            // process failures by iterating through each bulk response item
            logger.error("批量创建索引数据时存在索引失败的记录！", new Throwable(bulkResponse.buildFailureMessage()));
        }

        return created;
    }

    /**
     * 根据索引数据的id删除已经索引的数据,不立即刷新
     *
     * @param index 索引名称
     * @param type  类型名称
     * @param id    索引数据的id
     * @return 是否删除成功，成功为true
     * @author Jianpin.Li
     */
    public boolean deleteDoc(String index, String type, String id) {
        return this.deleteDoc(index, type, id, false);
    }

    /**
     * 根据索引数据的id删除已经索引的数据
     *
     * @param index 索引名称
     * @param type  类型名称
     * @param id    索引数据的id
     * @return 是否删除成功，成功为true
     * @author Jianpin.Li
     */
    public boolean deleteDoc(String index, String type, String id, boolean isRefresh) {
        //连接不成功或者索引不存在
        if (!this.existIndex(index)) {
            return false;
        }

        DeleteResponse dr = this.client.prepareDelete(index, type, id).setRefresh(isRefresh).execute().actionGet();
        return dr.isFound();
    }

    /**
     * 批量根据索引数据的id删除已经索引的数据, 不立即刷新
     *
     * @param index  索引名称
     * @param type   类型名称
     * @param idList 索引数据的id列表
     * @return 是否删除成功，部分删除成功为false，只有全部删除成功才为true
     * @author Jianpin.Li
     */
    public boolean bulkDeleteDoc(String index, String type, List<String> idList) {
        return this.bulkDeleteDoc(index, type, idList, false);
    }

    /**
     * 批量根据索引数据的id删除已经索引的数据
     *
     * @param index  索引名称
     * @param type   类型名称
     * @param idList 索引数据的id列表
     * @return 是否删除成功，部分删除成功为false，只有全部删除成功才为true
     * @author Jianpin.Li
     */
    public boolean bulkDeleteDoc(String index, String type, List<String> idList, boolean isRefresh) {
        boolean deleted = true;
        //连接不成功或者索引不存在
        if (!this.existIndex(index)) {
            return false;
        }

        BulkRequestBuilder bulker = this.client.prepareBulk();
        String id;
        for (int i = 0; i < idList.size(); i++) {
            id = idList.get(i);
            bulker.add(this.client.prepareDelete(index, type, id));
        }

        BulkResponse bulkResponse = bulker.setRefresh(isRefresh).execute().actionGet();
        if (bulkResponse.hasFailures()) {
            deleted = false;
            // process failures by iterating through each bulk response item
            logger.error("批量删除索引数据时存在索引失败的记录！");
        }

        return deleted;
    }

    /**
     * 批量根据索引数据的id删除已经索引的数据, 不立即刷新(int类型)
     *
     * @param index  索引名称
     * @param type   类型名称
     * @param idList 索引数据的id列表
     * @return 是否删除成功，部分删除成功为false，只有全部删除成功才为true
     * @author Jianpin.Li
     */
    public boolean bulkDeleteDoc4Int(String index, String type, List<Integer> idList) {
        return this.bulkDeleteDoc4Int(index, type, idList, false);
    }

    /**
     * 批量根据索引数据的id删除已经索引的数据
     *
     * @param index  索引名称
     * @param type   类型名称
     * @param idList 索引数据的id列表
     * @return 是否删除成功，部分删除成功为false，只有全部删除成功才为true
     * @author Jianpin.Li
     */
    public boolean bulkDeleteDoc4Int(String index, String type, List<Integer> idList, boolean isRefresh) {
        boolean deleted = true;
        //连接不成功或者索引不存在
        if (!this.existIndex(index)) {
            return false;
        }

        BulkRequestBuilder bulker = this.client.prepareBulk();
//        for (int i = 0; i < idList.size(); i++) {
//            if(index.equals(HoneybeeEsConst.INDEX_HONEYBEE) || index.equals(CamelEsConst.INDEX_CAMEL_MASTER)){
//                bulker.add(this.client.prepareDelete(index, type,  IndexUtil.getLocalIndexId(idList.get(i), null, null, CommonConfig.getSystemClientCode())));
//            }else{
//                bulker.add(this.client.prepareDelete(index, type, idList.get(i) + ""));
//            }
//        }

        BulkResponse bulkResponse = bulker.setRefresh(isRefresh).execute().actionGet();
        if (bulkResponse.hasFailures()) {
            deleted = false;
            // process failures by iterating through each bulk response item
            logger.error("批量删除索引数据时存在索引失败的记录！");
        }

        return deleted;
    }

    /**
     * 批量操作方法，支持添加、删除等操作, 不立即刷新
     *
     * @param index    索引名称
     * @param callback 回调，在此方法中编写添加、删除等操作
     * @return 是否操作成功，部分操作成功为false，只有全部操作成功才为true
     * @throws Exception
     * @author Jianpin.Li
     */
    public boolean bulkOperate(String index, ESCallback callback) throws Exception {
        return this.bulkOperate(index, callback, false);
    }

    /**
     * 批量操作方法，支持添加、删除等操作
     *
     * @param index     索引名称
     * @param callback  回调，在此方法中编写添加、删除等操作
     * @param isRefresh 是否立即刷新
     * @return 是否操作成功，部分操作成功为false，只有全部操作成功才为true
     * @throws Exception
     * @author Jianpin.Li
     */
    public boolean bulkOperate(String index, ESCallback callback, boolean isRefresh) throws Exception {
        boolean flag = true;

        //连接不成功或者索引不存在
        if (!this.existIndex(index)) {
            return false;
        }

        BulkRequestBuilder bulker = null;
        try {
            bulker = callback.doInES(this.client);
        } catch (Exception e) {
            flag = false;
            throw new Exception("批量操作索引数据时出错！", e);
        }

        //存在异常则直接返回false
        if (!flag) {
            return flag;
        }

        if (bulker == null) {
            return flag;
        }

        BulkResponse bulkResponse = bulker.setRefresh(isRefresh).execute().actionGet();
        if (bulkResponse.hasFailures()) {
            flag = false;
            // process failures by iterating through each bulk response item
            throw new Exception("批量操作索引数据时存在操作失败的记录！");
        }

        return flag;
    }

    /**
     * 进行query方式查询，此查询进行的评分等操作，效率相对较低
     *
     * @param indexs        索引名称，多个使用“,”分割
     * @param types         类型名称，多个使用“,”分割
     * @param boolQuery     query查询方式构造
     * @param sortBuilders  排序构造
     * @param highLightConf 高亮设置
     * @param pageNo        当前页号
     * @param pageSize      每页显示多少条
     * @return
     * @author Jianpin.Li
     */
    public SearchHits searchQuery(String indexs, String types, QueryBuilder boolQuery, List<SortBuilder> sortBuilders, HighLightConf highLightConf, int pageNo, int pageSize) {
        return this.search(indexs, types, boolQuery, null, sortBuilders, highLightConf, pageNo, pageSize);
    }

    /**
     * 进行query方式查询，此查询进行的评分等操作，效率相对较低
     *
     * @param indexs        索引名称，多个使用“,”分割
     * @param types         类型名称，多个使用“,”分割
     * @param boolQuery     query查询方式构造
     * @param sortBuilders  排序构造
     * @param highLightConf 高亮设置
     * @param pageNo        当前页号
     * @param pageSize      每页显示多少条
     * @return
     * @author Jianpin.Li
     */
    public SearchResponse searchQueryResp(String indexs, String types, QueryBuilder boolQuery, List<SortBuilder> sortBuilders, HighLightConf highLightConf, int pageNo, int pageSize) {
        return this.searchResp(indexs, types, boolQuery, null, sortBuilders, highLightConf, pageNo, pageSize);
    }

    /**
     * 进行filter方式查询，此查询不进行评分等操作，且进行了缓存，效率较高
     *
     * @param indexs       索引名称，多个使用“,”分割
     * @param types        类型名称，多个使用“,”分割
     * @param boolFilter   filter查询方式构造
     * @param sortBuilders 排序构造
     * @param pageNo       当前页号
     * @param pageSize     每页显示多少条
     * @return
     * @author Jianpin.Li
     */
    public SearchHits searchFilter(String indexs, String types, QueryBuilder boolFilter, List<SortBuilder> sortBuilders, int pageNo, int pageSize) {
        return this.search(indexs, types, null, boolFilter, sortBuilders, null, pageNo, pageSize);
    }

    /**
     * 进行filter方式查询，此查询不进行评分等操作，且进行了缓存，效率较高
     *
     * @param indexs       索引名称，多个使用“,”分割
     * @param types        类型名称，多个使用“,”分割
     * @param boolFilter   filter查询方式构造
     * @param sortBuilders 排序构造
     * @param pageNo       当前页号
     * @param pageSize     每页显示多少条
     * @return
     * @author Jianpin.Li
     */
    public SearchResponse searchFilterResp(String indexs, String types, QueryBuilder boolFilter, List<SortBuilder> sortBuilders, int pageNo, int pageSize) {
        return this.searchResp(indexs, types, null, boolFilter, sortBuilders, null, pageNo, pageSize);
    }

    /**
     * 进行查询的通用方法，query和filter只支持一种,返回查询的结果
     *
     * @param indexs        索引名称，多个使用“,”分割
     * @param types         类型名称，多个使用“,”分割
     * @param boolQuery     query查询方式构造
     * @param boolFilter    filter查询方式构造
     * @param sortBuilders  排序构造
     * @param highLightConf 高亮设置
     * @param pageNo        当前页号
     * @param pageSize      每页显示多少条
     * @return
     * @author Jianpin.Li
     */
    private SearchHits search(String indexs, String types, QueryBuilder boolQuery, QueryBuilder boolFilter, List<SortBuilder> sortBuilders, HighLightConf highLightConf, int pageNo, int pageSize) {
        return this.searchResp(indexs, types, boolQuery, boolFilter, sortBuilders, highLightConf, pageNo, pageSize).getHits();
    }

    /**
     * 进行查询的通用方法，query和filter只支持一种，返回整个查询的响应结果
     *
     * @param indexs        索引名称，多个使用“,”分割
     * @param types         类型名称，多个使用“,”分割
     * @param boolQuery     query查询方式构造
     * @param boolFilter    filter查询方式构造
     * @param sortBuilders  排序构造
     * @param highLightConf 高亮设置
     * @param pageNo        当前页号
     * @param pageSize      每页显示多少条
     * @return
     * @author Jianpin.Li
     */
    private SearchResponse searchResp(String indexs, String types, QueryBuilder boolQuery, QueryBuilder boolFilter, List<SortBuilder> sortBuilders, HighLightConf highLightConf, int pageNo, int pageSize) {
        try {
            SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(indexs);
            if (StringUtils.isNotEmpty(types)) {
                searchRequestBuilder.setTypes(types);
            }

            if (boolQuery != null) {
                searchRequestBuilder.setQuery(boolQuery);
            } else if (boolFilter != null) {
                searchRequestBuilder.setPostFilter(boolFilter);
            }

            //设置高亮
            if (highLightConf != null) {
                searchRequestBuilder.setHighlighterPreTags(highLightConf.getPreTag());
                searchRequestBuilder.setHighlighterPostTags(highLightConf.getPostTag());

                if (highLightConf.getFieldList() != null && highLightConf.getFieldList().size() > 0) {
                    for (String field : highLightConf.getFieldList()) {
                        searchRequestBuilder.addHighlightedField(field);
                        searchRequestBuilder.setHighlighterEncoder("html");
                    }
                }
            }

            searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

            if (pageNo != 0 && pageSize != 0) {
                searchRequestBuilder.setFrom((pageNo - 1) * pageSize).setSize(pageSize);//.setExplain(true)
            }

            if (sortBuilders != null && sortBuilders.size() > 0) {
                for (SortBuilder sortBuilder : sortBuilders) {
                    searchRequestBuilder.addSort(sortBuilder);
                }
            }

            if (logger.isDebugEnabled()) {
                String body = searchRequestBuilder.toString();
                logger.debug("searchRequestBuilder:\n{}/{}/_search\n{}", indexs, types, body);
            }
            return searchRequestBuilder.execute().actionGet();
        } catch (Exception e) {
            logger.error("搜索服务searchResp发生异常!", e);
            //发生了节点不存在的错误，需要重新初始化
            dealNoAvailableException(e);
        }

        return null;
    }

    /**
     * 分组查询
     *
     * @param indexs           索引名称，多个使用“,”分割
     * @param types            类型名称，多个使用“,”分割
     * @param boolQuery        query查询方式构造
     * @param boolFilter       filter查询方式构造
     * @param aggregationBuilders 分组查询构造数组
     * @return
     * @author Jianpin.Li
     */
    @SuppressWarnings("deprecation")
    public SearchResponse searchFacetResp(String indexs, String types, QueryBuilder boolQuery, QueryBuilder boolFilter,AbstractAggregationBuilder aggregationBuilders) {
        try {
            SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(indexs);
            if (StringUtils.isNotEmpty(types)) {
                searchRequestBuilder.setTypes(types);
            }

            if (boolQuery != null) {
                searchRequestBuilder.setQuery(boolQuery);
            } else if (boolFilter != null) {
                searchRequestBuilder.setPostFilter(boolFilter);
            }

            searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
            searchRequestBuilder.setFrom(0).setSize(0);

            if (aggregationBuilders != null ) {
               searchRequestBuilder.addAggregation(aggregationBuilders);
            }
            return searchRequestBuilder.execute().actionGet();

        } catch (Exception e) {
            logger.error("搜索服务searchFacetResp发生异常!", e);
            dealNoAvailableException(e);
        }
        return new SearchResponse();
    }

    /**
     *  分组查询 (可以指定排序)
     * @param indexs
     * @param types
     * @param boolQuery
     * @param boolFilter
     * @param sortBuilders
     * @param facetBuilderList
     * @return
     * @author drs
     */
    @SuppressWarnings("deprecation")
    public SearchResponse searchFacetResp(String indexs, String types, QueryBuilder boolQuery, QueryBuilder boolFilter, List<SortBuilder> sortBuilders, AbstractAggregationBuilder facetBuilderList) {

        try {

            SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(indexs);
            if (StringUtils.isNotEmpty(types)) {
                searchRequestBuilder.setTypes(types);
            }

            if (boolQuery != null) {
                searchRequestBuilder.setQuery(boolQuery);
            } else if (boolFilter != null) {
                searchRequestBuilder.setPostFilter(boolFilter);
            }

            searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
            searchRequestBuilder.setFrom(0).setSize(0);

            if (facetBuilderList != null) {
                    searchRequestBuilder.addAggregation(facetBuilderList);
            }
            if (sortBuilders != null && sortBuilders.size() > 0) {
                for (SortBuilder sortBuilder : sortBuilders) {
                    searchRequestBuilder.addSort(sortBuilder);
                }
            }
            return searchRequestBuilder.execute().actionGet();

        } catch (Exception e) {
            logger.error("搜索服务searchFacetResp发生异常!", e);
            dealNoAvailableException(e);
        }
        return new SearchResponse();
    }

    /**
     * 分组分页查询
     * @param indexs
     * @param types
     * @param boolQuery
     * @param boolFilter
     * @param facetBuilderList
     * @param pageNo
     * @param pageSize
     * @return
     * @author swx
     */
    @SuppressWarnings("deprecation")
    public SearchResponse searchFacetResp(String indexs, String types, QueryBuilder boolQuery, QueryBuilder boolFilter, List<SortBuilder> sortBuilders, AbstractAggregationBuilder facetBuilderList, int pageNo, int pageSize) {
        try {
            SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(indexs);
            if (StringUtils.isNotEmpty(types)) {
                searchRequestBuilder.setTypes(types);
            }

            if (boolQuery != null) {
                searchRequestBuilder.setQuery(boolQuery);
            } else if (boolFilter != null) {
                searchRequestBuilder.setPostFilter(boolFilter);
            }

            searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

            if (pageNo != 0 && pageSize != 0) {
                searchRequestBuilder.setFrom((pageNo - 1) * pageSize).setSize(pageSize);
            }

            if (facetBuilderList != null ) {
                    searchRequestBuilder.addAggregation(facetBuilderList);
            }

            if (sortBuilders != null && sortBuilders.size() > 0) {
                for (SortBuilder sortBuilder : sortBuilders) {
                    searchRequestBuilder.addSort(sortBuilder);
                }
            }
            return searchRequestBuilder.execute().actionGet();

        } catch (Exception e) {
            logger.error("搜索服务searchFacetResp发生异常!", e);
            dealNoAvailableException(e);
        }
        return new SearchResponse();
    }

    /**
     * 分组查询
     *
     * @param indexs           索引名称，多个使用“,”分割
     * @param types            类型名称，多个使用“,”分割
     * @param boolQuery        query查询方式构造
     * @param boolFilter       filter查询方式构造
     * @param facetBuilderList 分组查询构造数组callback
     * @return
     * @author Jianpin.Li
     */
    public Aggregations searchFacet(String indexs, String types, QueryBuilder boolQuery, QueryBuilder boolFilter, AbstractAggregationBuilder facetBuilderList) {
        return this.searchFacetResp(indexs, types, boolQuery, boolFilter, facetBuilderList).getAggregations();
    }

    /**
     * 聚合查询
     *这里的聚合查询会返回所有的记录，相当于select distinct(xxx) from table.性能是非常糟糕的。请谨慎使用。
     * 如果仅仅只是想实现：SQL的select count( * ) from (select distinct * from table)语句。请务必使用searchDistinctCount方法
     * @param indexs          索引名称，多个使用“,”分割
     * @param types           类型名称，多个使用“,”分割
     * @param boolQuery       query查询方式构造
     * @param boolFilter      filter查询方式构造
     * @param aggsBuilderList 聚合查询构造数组
     * @return
     * @author Jianpin.Li
     */
    @SuppressWarnings("rawtypes")
    public SearchResponse searchAggsResp(String indexs, String types, QueryBuilder boolQuery, QueryBuilder boolFilter, List<AggregationBuilder> aggsBuilderList) {
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(indexs);
        if (StringUtils.isNotEmpty(types)) {
            searchRequestBuilder.setTypes(types);
        }

        if (boolQuery != null) {
            searchRequestBuilder.setQuery(boolQuery);
        } else if (boolFilter != null) {
            searchRequestBuilder.setPostFilter(boolFilter);
        }

        searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        //searchRequestBuilder.setSearchType(SearchType.COUNT);//不需要返回具体的doc
        searchRequestBuilder.setFrom(0).setSize(0);

        if (aggsBuilderList != null && aggsBuilderList.size() > 0) {
            for (AggregationBuilder aggregationBuilder : aggsBuilderList) {
                searchRequestBuilder.addAggregation(aggregationBuilder);
            }
        }
        if(logger.isDebugEnabled()){
            logger.debug("searchRequestBuilder:\n{}/{}/_search\n{}", indexs, types, searchRequestBuilder.toString());
        }
        SearchResponse sr = searchRequestBuilder.execute().actionGet();
        if(logger.isDebugEnabled()){
            logger.debug(sr.toString());
        }
        return sr;
    }

    /**
     * 类似语 SQL的select count( * ) from (select distinct * from table)语句。请务必使用searchDistinctCount方法
     *
     * @param indexs
     * @param types
     * @param boolQuery 其他过滤条件，类似sql里面的where
     * @param boolFilter
     * @param distinctField
     * @return
     */

    private String DistinctCountColumnName="DistinctCountColumn";
    public long searchDistinctCount(String indexs, String types, QueryBuilder boolQuery, QueryBuilder boolFilter, String distinctField){

        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(indexs);
        if(types!=null){
            searchRequestBuilder.setTypes(types);
        }
        searchRequestBuilder.setSearchType(SearchType.COUNT);
        CardinalityBuilder cardinalityBuilder =  AggregationBuilders.cardinality(DistinctCountColumnName).field(distinctField);
        searchRequestBuilder.addAggregation(cardinalityBuilder);
        if (boolQuery != null) {
            searchRequestBuilder.setQuery(boolQuery);
        }else if (boolFilter != null) {
            searchRequestBuilder.setPostFilter(boolFilter);
        }
        logger.debug("searchRequestBuilder:\n/{}/{}/_search?search_type=count\n{}", indexs, types, searchRequestBuilder.toString());
        SearchResponse sr = searchRequestBuilder.execute().actionGet();
        InternalCardinality agg = (InternalCardinality)sr.getAggregations().get(DistinctCountColumnName);
        return agg.getValue();
    }
    /**
     * 聚合查询
     *
     * @param indexs          索引名称，多个使用“,”分割
     * @param types           类型名称，多个使用“,”分割
     * @param boolQuery       query查询方式构造
     * @param boolFilter      filter查询方式构造
     * @param aggsBuilderList 聚合查询构造数组
     * @return
     * @author Jianpin.Li
     */
    @SuppressWarnings("rawtypes")
    public Aggregations searchAggs(String indexs, String types, QueryBuilder boolQuery, QueryBuilder boolFilter, List<AggregationBuilder> aggsBuilderList) {
        return this.searchAggsResp(indexs, types, boolQuery, boolFilter, aggsBuilderList).getAggregations();
    }
    /**
     * 创建mapping
     *
     * @param index       索引名称
     * @param type        类型名称
     * @param mappingJson
     * @return boolean 创建成功为true
     * @author Jianpin.Li
     */
    public boolean createMapping(String index, String type, String mappingJson) {
        PutMappingRequest mappingRequest = Requests.putMappingRequest(index);
        if (StringUtils.isNotEmpty(type)) {
            mappingRequest.type(type);
        }
        mappingRequest.source(mappingJson);
        PutMappingResponse response = this.client.admin().indices().putMapping(mappingRequest).actionGet();
        return response.isAcknowledged();
    }



    /**
     * 通过设置更新的字段更新文档，不立即刷新
     *
     * @param index  索引名称
     * @param type   类型名称
     * @param id     索引文档id
     * @param fields 需要更新的字段
     * @return
     * @author Jianpin.Li
     */
    public boolean updateByFields(String index, String type, String id, Map<String, Object> fields) {
        return this.updateByFields(index, type, id, fields, false);
    }

    /**
     * 通过设置更新的字段更新文档
     *
     * @param index     索引名称
     * @param type      类型名称
     * @param id        索引文档id
     * @param fields    需要更新的字段
     * @param isRefresh 是否立即刷新
     * @return
     * @author Jianpin.Li
     */
    public boolean updateByFields(String index, String type, String id, Map<String, Object> fields, boolean isRefresh) {
        boolean created = false;
        try {
            UpdateResponse resp = this.client.prepareUpdate(index, type, id).setDoc(fields).setRefresh(isRefresh).execute().actionGet();
            created = resp.isCreated();
        } catch (Exception e) {
            logger.error("更新index为【" + index + ",type为【" + type + "】,id为【" + id + "】的索引发生错误！", e);
            dealNoAvailableException(e);
        }

        return created;
    }

    /**
     * 批量通过设置更新的字段更新文档，不立即刷新
     *
     * @param index  索引名称
     * @param type   类型名称
     * @param idList 索引文档id列表（String类型的主键）
     * @param fields 需要更新的字段
     * @return
     * @author Jianpin.Li
     */
    public boolean bulkUpdateByFields(final String index, final String type, final List<String> idList, final Map<String, Object> fields) {
        return this.bulkUpdateByFields(index, type, idList, fields, true);
    }

    /**
     * 批量通过设置更新的字段更新文档
     *
     * @param index     索引名称
     * @param type      类型名称
     * @param idList    索引文档id列表（String类型的主键）
     * @param fields    需要更新的字段
     * @param isRefresh 是否立即刷新
     * @return
     * @author Jianpin.Li
     */
    public boolean bulkUpdateByFields(final String index, final String type, final List<String> idList, final Map<String, Object> fields, boolean isRefresh) {
        boolean created = false;
        try {
            created = this.bulkOperate(index, new ESCallback() {
                @Override
                public BulkRequestBuilder doInES(Client client) {
                    BulkRequestBuilder bulker = client.prepareBulk();
                    for (String id : idList) {
                        bulker.add(client.prepareUpdate(index, type, id).setDoc(fields));
                    }
                    return bulker;
                }
            }, isRefresh);

        } catch (Exception e) {
            logger.error("更新index为【" + index + ",type为【" + type + "】,id列表为【" + idList + "】的索引发生错误！", e);
            dealNoAvailableException(e);
        }

        return created;
    }





    /**
     * 根据索引id获取索引数据
     *
     * @param index 索引名称
     * @param type  类型名称
     * @param id    索引id
     * @return
     * @author Jianpin.Li
     */
    public Map<String, Object> getDoc(String index, String type, String id) {
        try {
            GetResponse response = this.client.prepareGet(index, type, id).execute().actionGet();
            return response.getSource();
        } catch (Exception e) {
            logger.error("获取index为【" + index + "】,type为【" + type + "】, id为【" + id + "】的索引数据发生错误！", e);
            dealNoAvailableException(e);

        }
        return null;
    }

    /**
     * 根据索引id获取索引数据
     *
     * @param index 索引名称
     * @param type  类型名称
     * @param id    索引id
     * @return
     * @author Jianpin.Li
     */
    public Map<String, Object> getDocContainId(String index, String type, String id) {
        try {
            GetResponse response = this.client.prepareGet(index, type, id).execute().actionGet();
            Map<String, Object> docMap = response.getSource();
            if (docMap == null) {
                return null;
            }

            docMap.put(ESConst.ID_FORK, response.getId());
            return docMap;
        } catch (Exception e) {
            logger.error("获取index为【" + index + "】,type为【" + type + "】, id为【" + id + "】的索引数据发生错误！", e);
            dealNoAvailableException(e);
        }
        return null;
    }

    public String getSettings(String setting) {
        return this.client.settings().get(setting);
    }

    public ActionFuture<GetSettingsResponse> getClusterSettings(String[] indexes, String[] setting) {
        GetSettingsRequest request = new GetSettingsRequest();
        if (indexes != null && indexes.length > 0) {
            request.indices(indexes);
        }
        if (setting != null && setting.length > 0) {
            request.names(setting);
        }
        return this.client.admin().indices().getSettings(request);
    }

    public void setSettings(String[] indexes, String[] settings, String[] values) {
        if (settings == null || values == null || settings.length != values.length) {
            return;
        }
        Map<String, String> settingMap = new HashMap<String, String>();
        for (int i = 0; i < settings.length; i++) {
            settingMap.put(settings[i], values[i]);
        }

        Settings setting = Settings.settingsBuilder().put(settingMap).build();
        UpdateSettingsRequest request = new UpdateSettingsRequest();
        request.indices(indexes);
        request.settings(setting);
        this.client.admin().indices().updateSettings(request);
    }

    /**
     * 进行query方式查询，此查询进行的评分等操作，效率相对较低
     *
     * @param indexs        索引名称，多个使用“,”分割
     * @param types         类型名称，多个使用“,”分割
     * @param boolQuery     query查询方式构造
     * @param sortBuilders  排序构造
     * @param highLightConf 高亮设置
     * @return
     * @author Jianpin.Li
     */
    public SearchHits searchQuery(String indexs, String types, QueryBuilder boolQuery, List<SortBuilder> sortBuilders, HighLightConf highLightConf) {
        return this.searchResp(indexs, types, boolQuery, null, sortBuilders, highLightConf).getHits();
    }

    /**
     * 进行查询的通用方法，query和filter只支持一种，返回整个查询的响应结果
     *
     * @param indexs        索引名称，多个使用“,”分割
     * @param types         类型名称，多个使用“,”分割
     * @param boolQuery     query查询方式构造
     * @param boolFilter    filter查询方式构造
     * @param sortBuilders  排序构造
     * @param highLightConf 高亮设置
     * @return
     * @author Jianpin.Li
     */
    private SearchResponse searchResp(String indexs, String types, QueryBuilder boolQuery, QueryBuilder boolFilter, List<SortBuilder> sortBuilders, HighLightConf highLightConf) {
        try {
            SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(indexs);
            if (StringUtils.isNotEmpty(types)) {
                searchRequestBuilder.setTypes(types);
            }

            if (boolQuery != null) {
                searchRequestBuilder.setQuery(boolQuery);
            } else if (boolFilter != null) {
                searchRequestBuilder.setPostFilter(boolFilter);
            }

            //设置高亮
            if (highLightConf != null) {
                searchRequestBuilder.setHighlighterPreTags(highLightConf.getPreTag());
                searchRequestBuilder.setHighlighterPostTags(highLightConf.getPostTag());

                if (highLightConf.getFieldList() != null && highLightConf.getFieldList().size() > 0) {
                    for (String field : highLightConf.getFieldList()) {
                        searchRequestBuilder.addHighlightedField(field);
                    }
                }
            }

            searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

            searchRequestBuilder.setSize(60000).setFrom(0);

            if (sortBuilders != null && sortBuilders.size() > 0) {
                for (SortBuilder sortBuilder : sortBuilders) {
                    searchRequestBuilder.addSort(sortBuilder);
                }
            }

            logger.debug("searchRequestBuilder:\n{}/{}/_search\n{}", indexs, types, searchRequestBuilder.toString());
            return searchRequestBuilder.execute().actionGet();
        } catch (Exception e) {
            logger.error("搜索服务searchResp发生异常!", e);
            dealNoAvailableException(e);
        }

        return new SearchResponse();
    }

    public Client getClient() {
        return client;
    }


    public boolean deleteBatch(){


        return false;
    }
}
