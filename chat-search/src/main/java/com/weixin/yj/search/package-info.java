/**
 * 当创建索引的服务也变为异步任务，则对于MQ异步任务消息来说，必须保存不能丢失的。消息服务必须足够强大。
 * elaticsearch 于bubbo zk 中依赖的jar 冲突的问题
 * Created by :Guozhihua
 * Date： 2017/4/7.
 */
package com.weixin.yj.search;
/**
 * initMapping中，判断是否初始化了索引，否则，先创建索引，在建立mapping.创建索引指定分片和副本防止yellow.
 *
 * 建立好文本之后可以对于数据进行存储
 *
 *
 *
 */