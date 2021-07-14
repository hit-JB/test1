package com.hit.dao.impl;

import com.hit.dao.UserDao;
import com.hit.pojo.User;


public class UseDaoImpl extends BaseDao implements UserDao {


    @Override
    public User queryUserByName(String username) {
       String sql = "select id,username,password,email from t_user where username=?";
       return queryForOne(User.class,sql,username);
    }

    @Override
    public User queryUserByNameAndPassword(String password, String username) {
        String sql = "select id,username,password,email from t_user where username=? and password=?";
        User user = queryForOne(User.class,sql,username,password);
        return user;
    }

    @Override
    public int saveUser(User user) {
        String sql = "insert into t_user(username,password,email)values(?,?,?)";
        return  update(sql,user.getUsername(),user.getPassword(),user.getEmail());
    }
}
