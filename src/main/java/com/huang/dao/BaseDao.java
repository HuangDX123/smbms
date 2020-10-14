package com.huang.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    //静态代码块，类加载的时候就初始化了
    static {
        Properties properties = new Properties();
        //反射，通过类加载器读取对应的资源
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");//把资源变成流

        try {
            properties.load(is); //使用properties加载流
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取数据库配置文件里对应的值
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    //获取数据库的链接
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver); //将mysql驱动注册到DriverManager中去
            connection = DriverManager.getConnection(url, username, password); //传入参数，获取数据库连接
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;//返回连接
    }

    //查询公共方法

    /**
     * @param connection        sql连接
     * @param sql               sql语句
     * @param params            sql参数
     * @param resultSet         查询结果集
     * @param preparedStatement PreparedStatement对象可以防止sql注入
     * @return
     * @throws SQLException
     */
    public static ResultSet execute(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet, String sql, Object[] params) throws SQLException {
        preparedStatement = connection.prepareStatement(sql); //预编译SQL语句

        //向SQL语句中传入参数，即替换占位符?
        for (int i = 0; i < params.length; i++) {
            //setObject,数组从0开始，但是占位符从1开始
            preparedStatement.setObject(i + 1, params[i]);
        }

        resultSet = preparedStatement.executeQuery(); //执行查询语句
        return resultSet;
    }

    //增删改公共方法
    public static int execute(Connection connection, String sql, Object[] params, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            //setObject,数组从0开始，但是占位符从1开始
            preparedStatement.setObject(i + 1, params[i]);
        }

        int updataRows = preparedStatement.executeUpdate();
        return updataRows;//返回更新的行数
    }

    //关闭连接，释放资源
    public static boolean closeResource(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        boolean flag = true;

        if (resultSet != null) {
            try {
                resultSet.close();
                //关闭成功后设置为null，让垃圾回收器回收
                resultSet = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false; //没有释放成功
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                //关闭成功后设置为null，让垃圾回收器回收
                preparedStatement = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false; //没有释放成功
            }
        }

        if (connection != null) {
            try {
                connection.close();
                //关闭成功后设置为null，让垃圾回收器回收
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false; //没有释放成功
            }
        }

        return flag; //只要有一个资源没有释放成功就会返回false
    }
}
