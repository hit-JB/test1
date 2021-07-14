package com.hit.Dao;

import com.hit.bean.Customer;
import com.hit.utils.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
base operation for table
 */
public abstract class BaseDao<T> {
    private Class<T> clazz=null;
    {
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType parameter = (ParameterizedType) type;
        Type[] typeArguments = parameter.getActualTypeArguments();
        clazz = (Class<T>) typeArguments[0];
    }
    public static  <E> E getValue(Connection connection,String sql,Object...args){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++)
                preparedStatement.setObject(i+1,args[i]);
            boolean execute = preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return (E) resultSet.getObject(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(null,preparedStatement,resultSet);
        }
        return null;
    }
    public List<T> getListInstance(Connection connection, Class<T> clazz, String sql, Object...args) throws SQLException {
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
