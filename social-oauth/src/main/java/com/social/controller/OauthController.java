package com.social.controller;

import com.social.domain.SocialType;
import com.social.domain.User;
import com.social.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KimYJ on 2017-05-18.
 */
@Controller
public class OauthController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/facebook/complete")
    public String facebookComplete(OAuth2Authentication auth) {
        Map<String, String> map = (HashMap<String, String>) auth.getUserAuthentication().getDetails();
        if(userService.isExistUser(map.get("id"))) {
            userService.saveUser(User.builder()
                    .userPrincipal(map.get("id"))
                    .userName(map.get("name"))
                    .userEmail(map.get("email"))
                    .userImage("http://graph.facebook.com/" + map.get("id") + "/picture?type=square")
                    .socialType(SocialType.FACEBOOK)
                    .build());
        }
        return "complete";
    }

    @GetMapping(value = "/google/complete")
    public String googleComplete(OAuth2Authentication auth) {
        Map<String, String> map = (HashMap<String, String>) auth.getUserAuthentication().getDetails();
        if(userService.isExistUser(map.get("id"))) {
            userService.saveUser(User.builder()
                    .userPrincipal(map.get("id"))
                    .userName(map.get("name"))
                    .userEmail(map.get("email"))
                    .userImage(map.get("picture"))
                    .socialType(SocialType.GOOGLE)
                    .build());
        }
        return "complete";
    }

    @GetMapping(value = "/kakao/complete")
    public String kakaoComplete(OAuth2Authentication auth) {
        Map<String, String> map = (HashMap<String, String>) auth.getUserAuthentication().getDetails();
        HashMap<String, String> propertyMap = (HashMap<String, String>)(Object) map.get("properties");
        if(userService.isExistUser(String.valueOf(map.get("id")))) {
            userService.saveUser(User.builder()
                    .userPrincipal(String.valueOf(map.get("id")))
                    .userName(propertyMap.get("nickname"))
                    .userEmail(map.get("kaccount_email"))
                    .userImage(propertyMap.get("thumbnail_image"))
                    .socialType(SocialType.KAKAO)
                    .build());
        }
        return "complete";
    }
}
