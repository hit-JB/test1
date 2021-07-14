package com.hit.service.impl;

import com.hit.dao.UserDao;
import com.hit.dao.impl.UseDaoImpl;
import com.hit.pojo.User;
import com.hit.service.UserService;

public class UserServiceImpl implements UserService {//create test ctrl+shift+t
    UserDao userDao = new UseDaoImpl();
    @Override
    public void registUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        return userDao.queryUserByNameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean exitUsername(String username) {
        if(userDao.queryUserByName(username)==null)
            return false;
        return true;
    }
}
