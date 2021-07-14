package com.hit.exercise;

import com.hit.bean.Order;
import com.hit.utils.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Exercise1 {
    @Test
    public void testInsert() throws Exception{
       String sql ="insert into `order`(order_name,order_date) values(?,?)";
       List<Order> ret =comExecute(Order.class,sql,"FF","19990615");
    }
    public <T> List<T> comExecute(Class<T> clazz, String sql, Object...args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            List<T> ret = new ArrayList<>();
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++)
                preparedStatement.setObject(i+1,args[i]);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            ResultSetMetaData metaData = preparedStatement.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()){
                T t = clazz.getDeclaredConstructor().newInstance();
                for(int i=0;i<columnCount;i++){
                    String name = metaData.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(name);
                    field.setAccessible(true);
                    field.set(t,resultSet.getObject(i+1));
                }
                ret.add(t);
            }
            return ret;
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection,preparedStatement,resultSet);
        }
        return null;
    }

}
