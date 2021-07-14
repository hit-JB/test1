package com.hit.test;

import com.hit.pojo.User;
import com.hit.service.UserService;
import com.hit.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @Test
    void registUser() {
        UserService userService = new UserServiceImpl();
        User user = new User("jiangbiao","1993","jiangbiao@email.com");
        userService.registUser(user);
    }

    @Test
    void login() {
        UserService userService = new UserServiceImpl();
        User user = new User("jiangbiao","1993","jiangbiao@email.com");
        userService.login(user);

    }

    @Test
    void exitUsername() {
        UserService userService = new UserServiceImpl();
        System.out.println(userService.exitUsername("jiangbiao"));
    }
}