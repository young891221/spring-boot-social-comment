package com.social.controller;

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

    @GetMapping(value = "/facebook/complete")
    public String facebookComplete(OAuth2Authentication auth) {
        Map<String, String> map = (HashMap<String, String>) auth.getUserAuthentication().getDetails();
        String userPrincipal = map.get("id");
        String userName = map.get("name");
        String userEmail = map.get("email");
        String userImage = "http://graph.facebook.com/" + userPrincipal + "/picture?type=square";
        return "complete";
    }

    @GetMapping(value = "/google/complete")
    public String googleComplete(OAuth2Authentication auth) {
        Map<String, String> map = (HashMap<String, String>) auth.getUserAuthentication().getDetails();
        String userPrincipal = map.get("id");
        String userName = map.get("name");
        String userEmail = map.get("email");
        String userImage = map.get("picture");
        return "complete";
    }

    @GetMapping(value = "/kakao/complete")
    public String kakaoComplete(OAuth2Authentication auth) {
        Map<String, String> map = (HashMap<String, String>) auth.getUserAuthentication().getDetails();
        HashMap<String, String> propertyMap = (HashMap<String, String>)(Object) map.get("properties");
        String userPrincipal = String.valueOf(map.get("id"));
        String userName = propertyMap.get("nickname");
        String userEmail = map.get("kaccount_email");
        String userImage = propertyMap.get("thumbnail_image");
        return "complete";
    }
}
