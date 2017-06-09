package com.social.controller;

import com.social.annotation.SocialUser;
import com.social.domain.User;
import com.social.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by KimYJ on 2017-06-09.
 */
@Controller
public class Oauth2Controller {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{facebook|google|kakao}/complete")
    public String facebookComplete(@SocialUser User user, HttpSession session) {
        if(userService.isNotExistUser(user.getUserPrincipal())) {
            user = userService.saveUser(user);
        }
        session.setAttribute("user", user);
        return "complete";
    }
}
