package com.social.dao;


import com.social.domain.User;

/**
 * Created by jeonghoon on 2016-12-14.
 */
public interface UserDao {
    void saveUser(User user);
    User getUserHashKey(int hashKey);
}
