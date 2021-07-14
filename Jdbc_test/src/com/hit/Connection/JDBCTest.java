package com.hit.Connection;

import com.mchange.v2.c3p0.DataSources;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class JDBCTest {
    @Test
    public void testGetConnection() throws Exception{
        //create the connection poll
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql:///test");
        source.setUsername("root");
        source.setPassword("199802jb");
        source.setInitialSize(10);
        source.setMaxActive(10);
        Connection connection = source.getConnection();
        System.out.println(connection);
    }
    @Test
    public void testGetConnection2() throws Exception{
        Properties pros = new Properties();
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        pros.load(stream);
        DataSource dataSource = BasicDataSourceFactory.createDataSource(pros);
        System.out.println(dataSource);
    }
}
