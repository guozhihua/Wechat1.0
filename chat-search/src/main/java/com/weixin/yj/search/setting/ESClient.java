package com.weixin.yj.search.setting;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * <h3>elasticsearch客户端获取类</h3>
 * @author guozhihua
 */
public class ESClient {

    private static final Logger logger = LoggerFactory.getLogger(ESClient.class);
	
	private static List<Client> allClients=new ArrayList<>();
    /**
     * tomcat停止时，调用此方法，否则会报
     * created a ThreadLocal with key of type [org.elasticsearch.common.inject.InjectorImpl$1] (value [org.elasticsearch.common.inject.InjectorImpl$1@7677c17]) and a value of type [java.lang.Object[]]
     * (value [[Ljava.lang.Object;@1b03cdcd]) but failed to remove it when the web application was stopped.
     * Threads are going to be renewed over time to try and avoid a probable memory leak.
     * 错误
     */
    public static void closeAllClient(){
        for(Client client:allClients){
            client.close();
        }
    }
    public static Client createTransportClient(String prefix) {
        if (StringUtils.isEmpty(prefix)) {
            prefix = "elasticsearch";
        }
        ResourceBundle bundle = ResourceBundle.getBundle("es");
        String clusterName = bundle.getString(prefix + ".clusterName");

        if (StringUtils.isNotEmpty(clusterName)) {
            String nodeIps = bundle.getString(prefix + ".host");
            String nodePorts = bundle.getString(prefix + ".port");

            if (StringUtils.isNotEmpty(nodeIps) && StringUtils.isNotEmpty(nodePorts)) {

                final Settings settings = Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", true)
                        .build();

                String[] nodeIpArr = nodeIps.split(",");
                String[] nodePortArr = nodePorts.split(",");

                if (nodeIpArr.length != nodePortArr.length) {
                    logger.error(prefix + "的ip或端口号不匹配！", new Throwable());
                }

                TransportClient client = TransportClient.builder().settings(settings).build();
                for (int i = 0; i < nodeIpArr.length; i++) {
                    try{
                        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(nodeIpArr[i]), Integer.parseInt(nodePortArr[i])));
                    }catch (Exception ex){

                    }
                }
                allClients.add(client);
                return client;
            } else {
                logger.error(prefix + "的ip或端口号没有配置！", new Throwable());
            }
        } else {
            logger.error(prefix + "的cluster名称没有配置！", new Throwable());
        }

        return null;
    }

    public static Client reConnectTransportClient(String prefix, TransportClient client) {

        if (StringUtils.isEmpty(prefix)) {
            prefix = "elasticsearch";
        }
        ResourceBundle bundle = ResourceBundle.getBundle("es");
        String clusterName = bundle.getString(prefix + ".clusterName");

        if (StringUtils.isNotEmpty(clusterName)) {
            String nodeIps = bundle.getString(prefix + ".host");
            String nodePorts = bundle.getString(prefix + ".port");

            if (StringUtils.isNotEmpty(nodeIps) && StringUtils.isNotEmpty(nodePorts)) {

                final Settings settings = Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", true)
                        .build();

                String[] nodeIpArr = nodeIps.split(",");
                String[] nodePortArr = nodePorts.split(",");

                if (nodeIpArr.length != nodePortArr.length) {
                    logger.error(prefix + "的ip或端口号不匹配！", new Throwable());
                }

                if(client==null){
                     client = TransportClient.builder().settings(settings).build();
                }
                else{
                    for (int i = 0; i < nodeIpArr.length; i++) {
                        try{
                            client.removeTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(nodeIpArr[i]), Integer.parseInt(nodePortArr[i])));
                        }catch (Exception ex){}
                    }
                }
                for (int i = 0; i < nodeIpArr.length; i++) {
                    try{
                        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(nodeIpArr[i]), Integer.parseInt(nodePortArr[i])));
                    }catch (Exception ex){
                    }
                }
                return client;
            } else {
                logger.error(prefix + "的ip或端口号没有配置！", new Throwable());
            }
        } else {
            logger.error(prefix + "的cluster名称没有配置！", new Throwable());
        }

        return null;
    }

}
