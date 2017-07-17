# test_wechat
消息队列模块：message-queue 本身配置与模块config下
redis 缓存模块：也是在本身配置下
项目在启动的时候选择是否使用某些模块，加载对应的配置。

dubbox rest  + Zkper +mvc 的整合使用。dubbox rest 采用 servlet 方式依赖于外部tomcat。

search 采用elaticsearch 作为搜索服务，数据的同步采用了可配置的数据源 jdbc方式作为调用，暂时么有使用其他外部的插件。

一般而言，外部tomcat 较内置服务性能更为强大。

后期搭建mycat 读写分离，增加项目高并发情况下的支持。

common-jdbc 模块是JDBC方式操作的数据库。目前无模块依赖，仅作为测试
web-service 测试使用




对于索引数据的建立，采用异步任务的方式。

目前数据使用均为单数据源方式。

