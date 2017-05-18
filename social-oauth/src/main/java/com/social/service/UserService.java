package com.social.service;

import com.social.common.SocialType;
import com.social.dao.UserDao;
import com.social.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jeonghoon on 2016-12-14.
 */
@Service
@Transactional
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    public void saveUser(String userName, String userPrincipal, SocialType socialType, String userProfileUrl, String userUrl) {
        User user = makeUser(userName, userPrincipal, socialType, userProfileUrl, userUrl);
        user.setUserKey(makeHash(userName, userPrincipal, socialType));
        logger.info("user hash code : {}", user.getUserKey());

        userDao.saveUser(user);
    }

    public boolean isUserExist(String userName, String userPrincipal, SocialType socialType) {
        int hashKey = makeHash(userName, userPrincipal, socialType);
        if(userDao.getUserHashKey(hashKey) == null)
            return false;
        return true;
    }

    public User getUser(String userName, String userPrincipal, SocialType socialType) {
        int hashKey = makeHash(userName, userPrincipal, socialType);
        return userDao.getUserHashKey(hashKey);
    }

    private User makeUser(String userName, String userPrincipal, SocialType socialType, String userProfileUrl, String userUrl) {
        return new User(userName, userPrincipal, socialType, userProfileUrl, userUrl);
    }

    private int makeHash(String userName, String userPrincipal, SocialType socialType) {
        return (userName+userPrincipal+socialType).hashCode();
    }

    public User getUserByKey(int hashKey) {
        return userDao.getUserHashKey(hashKey);
    }
}
