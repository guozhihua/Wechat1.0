package com.weixin.utils.jdbc;

import com.mchange.v1.xml.DomParseUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.weixin.utils.jdbc.cfg.NamedScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * jdbc工具类
 * 因为要多站点配置，读取多个数据源，改成使用dbcp2读取配置
 *
 * @author Jianpin.Li, 刘点兵
 *         2014年7月31日 上午9:47:27
 */
public class JdbcUtils {
    private static Map<String, DataSource> dataSources = new ConcurrentHashMap<String, DataSource>();

    private static Logger logger = LoggerFactory.getLogger(JdbcUtils.class);

    public static DataSource getDataSource(String dataSourceName) {
        String initSource = dataSourceName;
        if (!CommonDataBase.LOCAL_APP_DATABSENAMES.contains(dataSourceName)) {
            return null;
        }
        if (dataSources.containsKey(dataSourceName)) {
            return dataSources.get(dataSourceName);
        } else {
            try {
                InputStream is = JdbcUtils.class.getResourceAsStream("/c3p0-config.xml");
                if (is != null) {
                    Map<String, NamedScope> clientDatasourceConfig = extractXmlConfigFromInputStream(is);
                    try {
                        if (is != null) is.close();
                    } catch (Exception e) {
                        logger.error(e.toString());
                    }

                    NamedScope cliendNameScope = clientDatasourceConfig.get(initSource);
                    if (cliendNameScope != null) {
                        HashMap props = cliendNameScope.getProps();
                        ComboPooledDataSource cpds = new ComboPooledDataSource(dataSourceName);
                        String jdbcUrl = props.get("jdbcUrl")+"&autoReconnect=true&characterEncoding=UTF-8";
                        cpds.setJdbcUrl(jdbcUrl);
                        cpds.setDriverClass((String) props.get("driverClass"));
                        cpds.setUser((String) props.get("user"));
                        cpds.setPassword((String) props.get("password"));
                        cpds.setMinPoolSize(props.get("minPoolSize")==null?5:Integer.parseInt(props.get("minPoolSize").toString()));
                        cpds.setMaxPoolSize(props.get("maxPoolSize")==null?20:Integer.parseInt(props.get("maxPoolSize").toString()));
                        cpds.setAcquireIncrement(props.get("acquireIncrement")==null?2:Integer.parseInt(props.get("acquireIncrement").toString()));
                        cpds.setInitialPoolSize(props.get("initialPoolSize") == null ? 5 : Integer.parseInt(props.get("initialPoolSize").toString()));
                        cpds.setAcquireRetryAttempts(props.get("acquireRetryAttempts")==null?3:Integer.parseInt(props.get("acquireRetryAttempts").toString()));
                        cpds.setAutoCommitOnClose(Boolean.valueOf(props.get("autoCommitOnClose")==null?"false":props.get("autoCommitOnClose").toString()));
                        cpds.setCheckoutTimeout(props.get("checkoutTimeout")==null?100:Integer.valueOf(props.get("checkoutTimeout").toString()));
                        cpds.setIdleConnectionTestPeriod(props.get("idleConnectionTestPeriod")==null?60:Integer.valueOf(props.get("idleConnectionTestPeriod").toString()));
                        cpds.setMaxIdleTime(props.get("maxIdleTime")==null?30:Integer.valueOf(props.get("maxIdleTime").toString()));
                        cpds.setMaxStatements(props.get("maxStatements")==null?60:Integer.valueOf(props.get("maxStatements").toString()));
                        dataSources.put(dataSourceName, cpds);
                        return cpds;
                    }
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        }
        return null;
    }
    public static Map<String, NamedScope> extractXmlConfigFromInputStream(InputStream is) throws Exception {
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = fact.newDocumentBuilder();
        Document doc = db.parse(is);

        return extractConfigFromXmlDoc(doc);
    }

    public static Map<String, NamedScope> extractConfigFromXmlDoc(Document doc) throws Exception {
        Element docElem = doc.getDocumentElement();
        if (docElem.getTagName().equals("c3p0-config")) {
            NamedScope defaults;
            Map<String, NamedScope> configNamesToNamedScopes = new HashMap<String, NamedScope>();

            Element defaultConfigElem = DomParseUtils.uniqueChild(docElem, "default-config");
            if (defaultConfigElem != null)
                defaults = extractNamedScopeFromLevel(defaultConfigElem);
            else
                defaults = new NamedScope();
            NodeList nl = DomParseUtils.immediateChildElementsByTagName(docElem, "named-config");
            for (int i = 0, len = nl.getLength(); i < len; ++i) {
                Element namedConfigElem = (Element) nl.item(i);
                String configName = namedConfigElem.getAttribute("name");
                if (configName != null && configName.length() > 0) {
                    NamedScope namedConfig = extractNamedScopeFromLevel(namedConfigElem);
                    configNamesToNamedScopes.put(configName, namedConfig);
                } else
                    logger.warn("Configuration XML contained named-config element without name attribute: " + namedConfigElem);
            }
            return configNamesToNamedScopes;
        } else
            throw new Exception("Root element of c3p0 config xml should be 'c3p0-config', not '" + docElem.getTagName() + "'.");
    }

    private static NamedScope extractNamedScopeFromLevel(Element elem) {
        HashMap props = extractPropertiesFromLevel(elem);
        HashMap userNamesToOverrides = new HashMap();

        NodeList nl = DomParseUtils.immediateChildElementsByTagName(elem, "user-overrides");
        for (int i = 0, len = nl.getLength(); i < len; ++i) {
            Element perUserConfigElem = (Element) nl.item(i);
            String userName = perUserConfigElem.getAttribute("user");
            if (userName != null && userName.length() > 0) {
                HashMap userProps = extractPropertiesFromLevel(perUserConfigElem);
                userNamesToOverrides.put(userName, userProps);
            } else
                logger.warn("Configuration XML contained user-overrides element without user attribute: " + perUserConfigElem);
        }

        HashMap extensions = extractExtensionsFromLevel(elem);

        return new NamedScope(props, userNamesToOverrides, extensions);
    }

    private static HashMap extractExtensionsFromLevel(Element elem) {
        HashMap out = new HashMap();
        NodeList nl = DomParseUtils.immediateChildElementsByTagName(elem, "extensions");
        for (int i = 0, len = nl.getLength(); i < len; ++i) {
            Element extensionsElem = (Element) nl.item(i);
            out.putAll(extractPropertiesFromLevel(extensionsElem));
        }
        return out;
    }

    private static HashMap extractPropertiesFromLevel(Element elem) {
        // System.err.println( "extractPropertiesFromLevel()" );

        HashMap out = new HashMap();

        try {
            NodeList nl = DomParseUtils.immediateChildElementsByTagName(elem, "property");
            int len = nl.getLength();
            for (int i = 0; i < len; ++i) {
                Element propertyElem = (Element) nl.item(i);
                String propName = propertyElem.getAttribute("name");
                if (propName != null && propName.length() > 0) {
                    String propVal = DomParseUtils.allTextFromElement(propertyElem, true);
                    out.put(propName, propVal);
                    //System.err.println( propName + " -> " + propVal );
                } else
                    logger.warn("Configuration XML contained property element without name attribute: " + propertyElem);
            }
        } catch (Exception e) {
            logger.error(
                    "An exception occurred while reading config XML. " +
                            "Some configuration information has probably been ignored.",
                    e);
        }

        return out;
    }

    /**
     * 返回分页sql
     *
     * @param baseSql
     * @param pageNo
     * @param pageSize
     * @return
     * @author 孙万祥 2014年5月20日 下午4:05:16
     */
    public static String getPageSql(String baseSql, int pageNo, int pageSize) {
        if (pageNo <= 0) {
            pageNo = 1;
        }
        if (pageSize <= 0) {
            pageSize = 200;
        }
        String sql = baseSql + " limit " + ((pageNo - 1) * pageSize) + "," + pageSize;
        System.out.println("sql is :"+sql);
        return sql;
    }

}
