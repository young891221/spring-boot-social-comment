package com.social.service;

import com.social.dao.UserDao;
import com.social.domain.SocialType;
import com.social.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by KimYJ on 2017-05-30.
 */
@Service
@Transactional
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    //@Autowired private UserDao userDao;

    public void saveUser() {

    }
}
