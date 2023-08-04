package com.lqf.myssm.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils {
    private static String driver=null;
    private static String url=null;
    private static String username=null;
    private static String password=null;

    private static ThreadLocal<Connection> threadLocal=new ThreadLocal<>();

    static {
        try{
            InputStream inputStream=JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
            Properties properties=new Properties();
            properties.load(inputStream);
            driver=properties.getProperty("driver");
            url=properties.getProperty("url");
            username=properties.getProperty("username");
            password=properties.getProperty("password");
            //加载驱动
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection=threadLocal.get();
        if(connection==null){
            connection=DriverManager.getConnection(url,username,password);
            threadLocal.set(connection);
        }
        return connection;
    }

    public static void release(PreparedStatement st, ResultSet rs) throws SQLException {
        if(rs!=null){
            rs.close();
        }
        if(st!=null){
            st.close();
        }
    }

    public static void closeConnection() throws SQLException {
        Connection connection=threadLocal.get();
        if(connection==null){
            return;
        }
        if(!connection.isClosed()){
            connection.close();
            threadLocal.set(null);
        }
    }

}