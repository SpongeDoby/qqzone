package com.lqf.myssm.transaction;

import com.lqf.myssm.utils.JdbcUtils;

import java.sql.SQLException;

public class TransactionManager {
    //开启事务
    public static void beginTrans() throws SQLException {
        JdbcUtils.getConnection().setAutoCommit(false);
    }

    //提交事务
    public static void commit() throws SQLException {
        JdbcUtils.getConnection().commit();
        JdbcUtils.closeConnection();
    }

    //回滚事务
    public static void rollback(){
        try {
            JdbcUtils.getConnection().rollback();
            JdbcUtils.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
