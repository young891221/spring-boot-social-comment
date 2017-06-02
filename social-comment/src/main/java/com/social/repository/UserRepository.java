package com.social.repository;


import com.social.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by KimYJ on 2017-05-30.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserPrincipalIs(String userPrincipal);
}
