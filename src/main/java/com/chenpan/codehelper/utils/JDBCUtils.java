package com.chenpan.codehelper.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.chenpan.codehelper.bean.DBPropertiesBean;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;

/**
 * Druid连接池工具类,将来dao层调用
 */
public class JDBCUtils {
    private static DataSource dataSource; // 定义成员变量DataSource

    /**
     * 获取连接
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void init(DBPropertiesBean dbPropertiesBean) throws SQLException {
        closeDataSource();
        dataSource = CreateDruidDataSource(dbPropertiesBean);
    }

    public static DruidDataSource CreateDruidDataSource(DBPropertiesBean dbPropertiesBean) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dbPropertiesBean.getDateSourceUrl());
        dataSource.setUsername(dbPropertiesBean.getUserName());
        dataSource.setPassword(dbPropertiesBean.getPassword());
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxWait(60000);
        dataSource.setMaxActive(20);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setTestWhileIdle(true);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(10);
        return dataSource;
    }

    /**
     * 释放资源
     */
    public static void close(Statement statement, Connection connection) {
        close(null, statement, connection);
    }

    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();// 归还连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取连接池方法
     */
    public static DataSource getDataSource() {
        return dataSource;
    }
    public static void closeDataSource() {
        if (dataSource != null) {
            try {
                Class<? extends DataSource> clazz = dataSource.getClass();
                Method closeMethod = clazz.getDeclaredMethod("close");
                closeMethod.invoke(dataSource);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}