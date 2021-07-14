package com.hit.Dao;

import com.hit.bean.Customer;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public interface CustomerDAO {
    /**
     * @Decription add data to the table
     * @param connection
     * @param sql
     */
    void update(Connection connection,Customer customer);
    void insert(Connection connection,Customer customer);
    void updateById(Connection connection, String sql, Customer customer);
    void deleteById(Connection connection,int id);
    Customer getCustomerById(Connection connection,int id);
    List<Customer> getAll(Connection connection);
    Long getCount(Connection connection);
    Date MaxBirth(Connection connection);
}
