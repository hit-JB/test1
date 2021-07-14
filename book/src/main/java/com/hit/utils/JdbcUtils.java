package com.hit.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class JdbcUtils {
    private static DruidDataSource dataSource;
    static {
        Properties properties = new Properties();
        //read the config file as stream
        InputStream inputStream = null;
        try {
            String path = Objects.requireNonNull(JdbcUtils.class.getClassLoader().getResource("jdbc.properties")).getPath();
            inputStream = new FileInputStream(new File(path));
            System.out.println(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        for(int i=0;i<100;i++){
            Connection cnn = getConnection();
            System.out.println(cnn);
        }
    }
    public static Connection getConnection(){

        try {
            Connection connection = dataSource.getConnection();
            return connection;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static void close(Connection conn){
        try {
            if(conn!=null)
            conn.close();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
