package com.hit.test;

import com.hit.dao.UserDao;
import com.hit.dao.impl.UseDaoImpl;
import com.hit.pojo.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UseDaoImplTest {

    @Test
    void queryUserByName() {
        UserDao userDao = new UseDaoImpl();
        System.out.println(userDao.queryUserByName("jiangbiao"));
    }

    @Test
    void queryUserByNameAndPassword() {
        UserDao userDao = new UseDaoImpl();
        System.out.println(userDao.queryUserByNameAndPassword("admin","admin"));
    }

    @Test
    void saveUser() {
        UserDao userDao = new UseDaoImpl();
        User newUser = new User("jiangBo","123456","jb703435169@gmail.com");
        userDao.saveUser(newUser);
    }
}