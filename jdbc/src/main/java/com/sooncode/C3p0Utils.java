package com.sooncode;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sooncode.db.DB;
import com.sooncode.db.DBs;
import org.apache.log4j.Logger;

import java.beans.PropertyVetoException;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by :Guozhihua
 * Date： 2016/11/21.
 */
public class C3p0Utils {
    public final static Logger logger = Logger.getLogger("C3p0Utils.class");

    private static final C3p0Utils instance=new C3p0Utils();

    /** 数据源缓存 */
    private static Map<String, DB> dBcache = new HashMap<>();

    private static ComboPooledDataSource cpds=new ComboPooledDataSource(true);

    /** 源码所在路径 */
    private static String classesPath;
    /** c3p0 配置文件 */
    public static Properties c3p0properties;
    static{
        dBcache.clear();
        Properties c3p0 = new Properties();
        classesPath = instance.getClassesPath();
        List<String> dbConfig = getDbConfig();
        for (String str : dbConfig) {
            PropertiesUtil pu = new PropertiesUtil(classesPath + str);
            DB db = new DB();
            db.setKey(pu.getString("KEY"));
            db.setDriver(pu.getString("DRIVER"));
            db.setIp(pu.getString("IP"));
            db.setPort(pu.getString("PORT"));
            db.setDataName(pu.getString("DATA_NAME"));
            db.setEncodeing(pu.getString("ENCODEING"));
            db.setUserName(pu.getString("USERNAME"));
            db.setPassword(pu.getString("PASSWORD"));
            dBcache.put(db.getKey(), db);
        }
        InputStreamReader in;

        try {
            in = new InputStreamReader(new FileInputStream(classesPath + "c3p0.properties"), "utf-8");
            c3p0.load(in);
            c3p0properties = c3p0;
            System.out.println("c3p0配置加载：maxPoolSize:"+c3p0.getProperty("maxPoolSize"));
        } catch (Exception e) {
            c3p0properties = null;
            e.printStackTrace();
            logger.debug("【加载c3p0  配置文件失败】");
        }
        cpds.setDataSourceName("c3p0PollDataSource");
        // 加载驱动类
        DB db=null;
        try {
            db = dBcache.get(c3p0properties.getProperty("DEFAULT_DB_PREFIX").toString());
            String jdbcUrl = "jdbc:mysql://" + db.getIp() + ":" + db.getPort() + "/" + db.getDataName() + "?useUnicode=true&autoReconnect=true&characterEncoding=" + db.getEncodeing();
            cpds.setJdbcUrl(jdbcUrl);
            cpds.setDriverClass(db.getDriver());
        } catch (PropertyVetoException e) {
            logger.error("init c3p0 error ",e);
            e.printStackTrace();
        }
        cpds.setUser(db.getUserName());
        cpds.setPassword(db.getPassword());

        cpds.setMaxPoolSize(Integer.valueOf(c3p0properties.getProperty("maxPoolSize").toString()));
        cpds.setMinPoolSize(Integer.valueOf(c3p0properties.getProperty("minPoolSize").toString()));

        cpds.setAcquireIncrement(Integer.valueOf(c3p0properties.getProperty("acquireIncrement").toString()));
        cpds.setInitialPoolSize(Integer.valueOf(c3p0properties.getProperty("initialPoolSize").toString()));
        cpds.setAcquireRetryAttempts(Integer.valueOf(c3p0properties.getProperty("acquireRetryAttempts").toString()));

        cpds.setMaxIdleTime(Integer.valueOf(c3p0properties.getProperty("maxIdleTime").toString()));
        cpds.setAutoCommitOnClose(Boolean.valueOf(c3p0properties.getProperty("autoCommitOnClose")));

        cpds.setCheckoutTimeout(Integer.valueOf(c3p0properties.getProperty("checkoutTimeout")));
        cpds.setIdleConnectionTestPeriod(Integer.valueOf(c3p0properties.getProperty("idleConnectionTestPeriod")));
        cpds.setMaxIdleTime(Integer.valueOf(c3p0properties.getProperty("maxIdleTime")));
        cpds.setMaxStatements(Integer.valueOf(c3p0properties.getProperty("maxStatements")));
        cpds.setTestConnectionOnCheckin(Boolean.valueOf(c3p0properties.getProperty("testConnectionOnCheckin")));
    }
    /**
     * 获取 源码所在路径
     * @return
     */
    private String getClassesPath() {
        String path = this.getClass().getResource("/").getPath();
        File file = new File(path);
        String classesPath = file.toString() + File.separatorChar;
        return classesPath;
    }

    public static  synchronized   C3p0Utils getInstance(){
        return instance;
    }

    public  final  synchronized   Connection getConnection(){
        try {
            Connection connection =cpds.getConnection();
           logger.info("从c3p0 连接池获取connection.."+connection.toString());
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
           logger.error("c3p0utils 获取数据库连接池失败",e);
        }
        return null;
    }
    /**扫描数据库配置文件*/
    private static List<String> getDbConfig() {
        File file = new File(classesPath);
        String test[];
        test = file.list();
        List<String> dbCongig = new ArrayList<>();
        for (int i = 0; i < test.length; i++) {
            String fileName = test[i];
            if (fileName.contains("_db.properties")) {
                dbCongig.add(fileName);
            }
        }

        return dbCongig;

    }
    public  static  String  getDesc(){
        String s = "";
        try{
             s ="当前连接数目：【"+ cpds.getNumBusyConnections()+"】，总连接数目：【"+cpds.getNumConnections()+"】，空闲连接数：【"+cpds.getNumIdleConnections()+"】";
             logger.info("c3p0 连接池信息："+s);
        }catch (Exception ex){
            logger.error("c3p0 desc is error",ex);
        }
        return  s;
    }




}

/**
 * 配置文件 读取工具类
 *
 * @author pc
 *
 */
class PropertiesUtil {
    /**
     * 配置文件所在路径
     */
    private String filePath;

    public PropertiesUtil(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 根据Key读取Value
     *
     * @param filePath 配置文件 (默认在src下)
     * @param key      关键字
     * @return 值
     */
    public String getString(String key) {
        Properties p = new Properties();
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(this.filePath), "utf-8");
            p.load(in);
            String value = p.getProperty(key);
            return value.trim();

        } catch (IOException e) {
            e.printStackTrace();
            DBs.logger.error("error " + e);
            return null;
        }
    }

    /**
     * 根据Key读取Value
     *
     * @param filePath 配置文件 (默认在src下)
     * @param key      关键字
     * @return int
     */
    public Integer getInt(String key) {
        Properties properties = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(this.filePath));
            properties.load(in);
            String value = properties.getProperty(key);
            Integer val = Integer.parseInt(value.trim());
            return val;

        } catch (Exception e) {
            DBs.logger.error("[关闭数据库资源失败]", e);
            return null;
        }
    }





}
