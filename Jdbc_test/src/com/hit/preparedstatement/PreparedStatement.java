package com.hit.preparedstatement;

import com.hit.Connection.ConnectionTest;
import com.hit.bean.Customer;
import com.hit.bean.Order;
import com.hit.utils.JDBCUtils;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 *
 **/
public class PreparedStatement {
    //add a state to the table
    @Test
    public void testComUpdate(){
//        String sql = "delete from test.customers where id = ?";
//        update(sql,3);
        String sql = "select id,name,email,birth from test.customers where id < ?";
        List<Customer> ret = getListInstance(Customer.class,sql,12);
        ret.forEach(System.out::println);
    }
    @Test
    public void testInsert()  {//ctrl+alt+t1
        java.sql.PreparedStatement preparedStatement = null;
        try {
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
            String sql = "insert into test.customers(name, email, birth) values (?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"nazha");
            preparedStatement.setString(2,"nezha@email.com");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse("1000-01-01");
            preparedStatement.setDate(3,new Date(date.getTime()));
            preparedStatement.execute();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }
    @Test
    public void testUpdate() throws Exception{
        Connection connection = JDBCUtils.getConnection();
        String sql = "update test.customers set name = ? where id = ?";
        java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,"mozhart");
        preparedStatement.setObject(2,18);
        preparedStatement.execute();
        JDBCUtils.closeResource(connection,preparedStatement);
    }
    public void update(String sql,Object... args) {//the length of the args
        Connection connection = null;
        java.sql.PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,preparedStatement);
        }

    }
    @Test
    public void testQuery1() {
        Connection connection = null;
        java.sql.PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql="select id,name,email,birth from test.customers where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,1);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){//judge if there is data next if there exit data the point is move
                //get the data of the current line
                int id =resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                java.util.Date birth = resultSet.getDate(4);
                Customer customer= new Customer(id,name,email,birth);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,preparedStatement,resultSet);
        }

    }
    public Customer customerComQuery(String sql,Object... args) {//core operation use reflect
        Connection connection = null;
        java.sql.PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args
                [i]);
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();//result metadata
            int columnCount = metaData.getColumnCount();
            if(resultSet.next())
            {
                Customer customer = new Customer();
                for(int i=0;i<columnCount;i++) {
                    Object object = resultSet.getObject(i + 1);
                    //through reflect get the name of each column
                    String columnName = metaData.getColumnName(i + 1);
                    Field field = Customer.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(customer,object);//reflect
                }
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection,preparedStatement,resultSet);
        }
        return null;
    }
    public Order OrderForQuery(String sql,Object... args){
        Connection connection = null;
        java.sql.PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++)
                preparedStatement.setObject(i+1,args[i]);
            preparedStatement.execute();//execute the sql sequence;
            resultSet = preparedStatement.getResultSet();
            ResultSetMetaData metaData = preparedStatement.getMetaData();
            int columnCount = metaData.getColumnCount();
            if(resultSet.next()){
                Order order = new Order();
                for(int i=0;i<columnCount;i++){
                    Object object = resultSet.getObject(i + 1);
                    String columnName = metaData.getColumnLabel(i + 1);//get the rename of the column name get the name of the column name
                    //reflect the field to the given data
                    Field field = Order.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(order,object);
                }
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection,preparedStatement,resultSet);
        }
        return null;
    }
    public <T> T getInstance(Class<T> clazz,String sql,Object... args){
        Connection connection = null;
        java.sql.PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++)
                preparedStatement.setObject(i+1,args[i]);
            preparedStatement.execute();//execute the sql sequence;
            resultSet = preparedStatement.getResultSet();
            ResultSetMetaData metaData = preparedStatement.getMetaData();
            int columnCount = metaData.getColumnCount();
            if(resultSet.next()){
                T newInstance = clazz.getDeclaredConstructor().newInstance();
                for(int i=0;i<columnCount;i++){
                    Object object = resultSet.getObject(i + 1);
                    String columnName = metaData.getColumnLabel(i + 1);//get the rename of the column name get the name of the column name
                    //reflect the field to the given data
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(newInstance,object);
                }
                return newInstance;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection,preparedStatement,resultSet);
        }
        return null;
    }
    public <T> List<T> getListInstance(Class<T> clazz,String sql,Object...args){
        Connection connection = null;
        java.sql.PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++)
                preparedStatement.setObject(i+1,args[i]);
            preparedStatement.execute();//execute the sql sequence;
            resultSet = preparedStatement.getResultSet();
            ResultSetMetaData metaData = preparedStatement.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<T> ret = new ArrayList<>();
            while (resultSet.next()){
                T newInstance = clazz.getDeclaredConstructor().newInstance();
                for(int i=0;i<columnCount;i++){
                    Object object = resultSet.getObject(i + 1);
                    String columnName = metaData.getColumnLabel(i + 1);//get the rename of the column name get the name of the column name
                    //reflect the field to the given data
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(newInstance,object);
                }
                ret.add(newInstance);
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection,preparedStatement,resultSet);
        }
        return null;
    }
    //insert 20000date to the goods table
    @Test
    public void insetBatchDate() throws Exception{
        Connection connection = JDBCUtils.getConnection();
        java.sql.PreparedStatement preparedStatement;
        String sql = "insert into test.goods(name) values(?)";
        preparedStatement = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for(int i=0;i<20000;i++){
            preparedStatement.setObject(1,"name_"+(i+20000));
            preparedStatement.addBatch();
            if(i%500==0){
                preparedStatement.executeBatch();
                preparedStatement.clearBatch();
            }
        }
        connection.commit();
    }
}
