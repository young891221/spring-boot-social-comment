package com.social.service;

import com.social.domain.User;
import com.social.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by KimYJ on 2017-05-30.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean isNotExistUser(String userPrincipal) {
        return userRepository.findByUserPrincipalIs(userPrincipal) == null ? true : false;
    }
}
