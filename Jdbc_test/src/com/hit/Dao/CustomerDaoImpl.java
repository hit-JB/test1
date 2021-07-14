package com.hit.Dao;

import com.hit.bean.Customer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class CustomerDaoImpl extends BaseDao<Customer> implements CustomerDAO {

    @Override
    public void update(Connection connection, Customer customer) {

    }

    @Override
    public void insert(Connection connection, Customer customer) {
    }

    @Override
    public void updateById(Connection connection, String sql, Customer customer) {

    }

    @Override
    public void deleteById(Connection connection, int id) {

    }

    @Override
    public Customer getCustomerById(Connection connection, int id) {
        return null;
    }

    @Override
    public List<Customer> getAll(Connection connection) {
        return null;
    }

    @Override
    public Long getCount(Connection connection) {
        return null;
    }

    @Override
    public Date MaxBirth(Connection connection) {
        return null;
    }
}
