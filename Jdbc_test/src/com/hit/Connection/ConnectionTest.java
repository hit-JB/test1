package com.hit.Connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
    @Test
    public void testConnection1() throws SQLException {
        Driver driver = new com.mysql.jdbc.Driver();
        String url = "jdbc:mysql://localhost:3306/book";
        Properties inf= new Properties();
        inf.setProperty("user","root");
        inf.setProperty("password","199802jb");
        Connection connect = driver.connect(url, inf);
        System.out.println(connect);
    }
    @Test
    public void testConnection2() throws Exception {
        //through reflect get the object
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver)aClass.getDeclaredConstructor().newInstance();
        String url = "jdbc:mysql://localhost:3306/book";
        Properties inf= new Properties();
        inf.setProperty("user","root");
        inf.setProperty("password","199802jb");
        Connection connect = driver.connect(url, inf);
        System.out.println(connect);
    }
    @Test//use the drive manager
    public void testConnection3() throws Exception {
        //register the drover
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver)aClass.getDeclaredConstructor().newInstance();
        DriverManager.registerDriver(driver);
        String url = "jdbc:mysql://localhost:3306/book";
        Properties inf= new Properties();
        inf.setProperty("user","root");
        inf.setProperty("password","199802jb");
        Connection connection = DriverManager.getConnection(url, inf);
        System.out.println(connection);
    }
    @Test
    public void testConnection4() throws Exception{
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
//
//        Driver driver = (Driver)aClass.getDeclaredConstructor().newInstance();
//        DriverManager.registerDriver(driver);
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book",
                "root","199802jb");
        System.out.println(conn);

    }
    @Test
    //connect the service through load the config file
    public void getConnection5() throws Exception{
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);
        String user = properties.getProperty("user");
        String url = properties.getProperty("url");
        String password = properties.getProperty("password");
        String driverClass = properties.getProperty("driverClass");
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }
}
