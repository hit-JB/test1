package com.hit.exercise;

import com.hit.bean.Customer;
import com.hit.utils.JDBCUtils;
import org.junit.Test;

import java.io.*;
import java.sql.*;
import java.util.Date;
import java.util.Objects;

public class BlobTest {
    @Test
    public void testInsert() throws Exception{
        Connection connection = JDBCUtils.getConnection();
        String sql="insert into test.customers(name,email,birth,photo)values(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,"jiangbo");
        preparedStatement.setObject(2,"hit@jb.com");
        preparedStatement.setObject(3,"1886-02-28");
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("install.png")).getPath();
        System.out.println(path);
        FileInputStream inputStream = new FileInputStream(new File(path));
        preparedStatement.setObject(4,inputStream);
        preparedStatement.execute();
        JDBCUtils.closeResource(connection,preparedStatement);
    }
    @Test
    public void testQuery() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth,photo from test.customers where id =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,21);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            ResultSetMetaData metaData = preparedStatement.getMetaData();
            if(resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);
                Customer customer = new Customer(id,name,email,birth);
                System.out.println(customer);
                Blob blob = resultSet.getBlob(5);
                InputStream stream = null;
                FileOutputStream fos = null;
                try {
                    stream = blob.getBinaryStream();
                    fos = new FileOutputStream("download.png");
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len =stream.read(buffer))!=-1)
                        fos.write(buffer,0,len);
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                } finally {
                    assert stream != null;
                    stream.close();
                    assert fos != null;
                    fos.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JDBCUtils.closeResource(connection,preparedStatement,resultSet);
    }
}
