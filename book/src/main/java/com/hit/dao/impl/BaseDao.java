package com.hit.dao.impl;

import com.hit.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


import java.sql.Connection;
import java.sql.SQLException;

public class BaseDao {
    private QueryRunner queryRunner  = new QueryRunner();
    public int update(String sql,Object...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.update(connection,sql,args);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return -1;
    }
    public <T> T queryForOne(Class<T> type,String sql,Object...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new BeanHandler<>(type),args);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return null;
    }
    public Object queryForSingleValue(String sql,Object...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new ScalarHandler(),args);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return null;
    }
}
