package com.hit.service;

import com.alibaba.druid.support.spring.stat.SpringStatUtils;
import com.hit.pojo.User;

public interface UserService {
    public void registUser(User user);
    public User login(User user);
    public boolean exitUsername(String username);
}
