package com.hit.dao;

import com.hit.pojo.User;

public interface UserDao {
    /**
     * return the user message according to user name
     * @param username
     * @return return null if not exit the user otherwise the user
     */
    public User queryUserByName(String username);

    /**
     *
     * @param password
     * @param username
     * @return return the user selected
     */
    public User queryUserByNameAndPassword(String username,String password);

    /**
     *
     * @param user
     *
     * @return if
     */
    public int saveUser(User user);
}
