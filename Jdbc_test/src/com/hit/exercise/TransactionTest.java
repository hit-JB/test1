package com.hit.exercise;

import com.hit.bean.User;
import com.hit.utils.JDBCUtils;
import org.junit.Test;

import javax.swing.*;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionTest {
    @Test
    public void testUpdate() throws Exception{
        String sql="update user_table set balance = balance-? where user=?";
        String sql1 = "update user_table set balance =? where user=?";
        //update(sql,100,"AA");
        Connection connection = JDBCUtils.getConnection();
        connection.setAutoCommit(false);
        //updateII(connection,sql,100,"AA");
        updateII(connection,sql1,200,"BB");
        Thread.sleep(15000);
        System.out.println("successful transition");
        connection.commit();
    }
    @Test
    public void testInsertTransaction() throws Exception{
        Connection connection = JDBCUtils.getConnection();
        System.out.println(connection.getTransactionIsolation());
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        String sql = "select user,password,balance from user_table where user=?";
        connection.setAutoCommit(false);
        List<User> users = getListInstance(connection,User.class,sql,"BB");

        users.forEach(System.out::println);
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

    }//未考虑数据库事物转帐时的操作
    public void updateII(Connection connection ,String sql,Object...args) throws SQLException {
        java.sql.PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(preparedStatement!=null)
                preparedStatement.close();
        }
    }
    public <T> List<T> getListInstance(Connection connection,Class<T> clazz, String sql, Object...args) throws SQLException {
        java.sql.PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
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
        }
        finally {
          if(resultSet!=null)
              resultSet.close();
          if(preparedStatement!=null)
              preparedStatement.close();
        }
        return null;
    }
}
