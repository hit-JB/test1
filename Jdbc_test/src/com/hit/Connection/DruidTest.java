package com.hit.Connection;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.DataSources;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidTest {
    @Test
    public void getConnection() throws Exception{
        Properties pros = new Properties();
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        pros.load(stream);
        DataSource source = DruidDataSourceFactory.createDataSource(pros);
        Connection connection = source.getConnection();
        System.out.println(connection);
    }
}
