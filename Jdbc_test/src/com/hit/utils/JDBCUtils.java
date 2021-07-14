package com.hit.utils;

import com.hit.Connection.ConnectionTest;

import javax.swing.text.html.HTMLDocument;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class JDBCUtils {
    public static Connection  getConnection() throws Exception{
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);
        String user = properties.getProperty("user");
        String url = properties.getProperty("url");
        String password = properties.getProperty("password");
        String driverClass = properties.getProperty("driverClass");
        Class.forName(driverClass);//static initialize
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
    public static void closeResource(Connection cnn, PreparedStatement ps) {
        try {
            ps.close();
            cnn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void closeResource(Connection cnn, PreparedStatement ps, ResultSet resultSet){
        try {
            if(cnn!=null)
                cnn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if(ps!=null)
                ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if(resultSet!=null)
                resultSet.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
