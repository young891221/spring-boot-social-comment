package com.social.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by KimYJ on 2017-05-18.
 */
@Controller
public class OauthController {

    @GetMapping(value = "/facebook/complete") //pathvariable로 통일
    public String facebookComplete(HttpServletRequest request, HttpServletResponse response, OAuth2Authentication auth) {
        Map<String, String> map = (HashMap<String, String>) auth.getUserAuthentication().getDetails();
        String userName = map.get("name");
        String userPrincipal = map.get("id");
        String socialValue = auth.getAuthorities().toArray()[0].toString().split("_")[1];
        String userImage = "http://graph.facebook.com/" + userPrincipal + "/picture?type=square";
        String userUrl = "https://facebook.com/"+userPrincipal;

        auth.setAuthenticated(true);
        return "complete";
    }

    @GetMapping(value = "/twitter/complete")
    public String twitterComplete(HttpServletRequest request, HttpServletResponse response, OAuth2Authentication auth) {

        return "complete";
    }

    @GetMapping(value = "/google/complete")
    public String googleComplete(HttpServletRequest request, HttpServletResponse response, OAuth2Authentication auth) {

        return "complete";
    }
}
