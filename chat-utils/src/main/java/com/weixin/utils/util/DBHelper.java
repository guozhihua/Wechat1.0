package com.weixin.utils.util;


import java.sql.*;

public class DBHelper {

    //mycat 连接v地址
    public static final String url = "jdbc:mysql://47.94.101.185:8066/app_test";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "root";
    public static final String password = "root";

    public Connection conn = null;

    public DBHelper() {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void execute(String sql) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(sql);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (st != null)
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    public ResultSet executeQuery(String sql) {
        PreparedStatement st = null;
        ResultSet resultSet = null;
        try {
            st = conn.prepareStatement(sql);
            resultSet = st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (st != null)
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return resultSet;
    }
}
