package com.social.dao.Impl;


import com.social.dao.UserDao;
import com.social.domain.User;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by jeonghoon on 2016-12-14.
 */

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    private final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public void saveUser(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    @Override
    public User getUserHashKey(int hashKey) {
        return (User)sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("userKey", hashKey)).uniqueResult();
    }
}
