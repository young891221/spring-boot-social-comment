package com.social.service;

import com.social.repository.UserRepository;
import com.social.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by KimYJ on 2017-05-30.
 */
@Service
@Transactional
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean isExistUser(String userPrincipal) {
        return userRepository.findByUserPrincipalIs(userPrincipal) == null ? true : false;
    }
}
