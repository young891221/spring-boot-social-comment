package com.social.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KimYJ on 2017-05-18.
 */
@Controller
public class OauthController {

    @GetMapping(value = "/facebook/complete")
    public String facebookComplete(HttpServletRequest request, HttpServletResponse response, OAuth2Authentication auth, Authentication authentication) {

        if(authentication != null) {
            authentication.setAuthenticated(false);
            //SecurityContextHolder.getContext().setAuthentication(null);
            request.getServletContext().removeAttribute("token");
            request.getServletContext().removeAttribute("connection");
        }

        Map<String, String> map = (HashMap<String, String>) auth.getUserAuthentication().getDetails();
        String userName = map.get("name");
        String userPrincipal = map.get("id");
        String socialValue = authentication.getAuthorities().toArray()[0].toString().split("_")[1];
        String userImage = "http://graph.facebook.com/" + userPrincipal + "/picture?type=square";
        String userUrl = "https://facebook.com/"+userPrincipal;

        /*if (!userService.isUserExist(userName, userPrincipal, SocialType.valueOf(socialValue))) {
            userService.saveUser(userName, userPrincipal, SocialType.valueOf(socialValue), userImage, userUrl);
        }

        User user = userService.getUser(userName, userPrincipal, SocialType.valueOf(socialValue));*/

        Cookie cookie = new Cookie("userIdx", "test");
        response.addCookie(cookie);

        return "complete";
    }

    @GetMapping(value = "/twitter/complete")
    public String twitterComplete(HttpServletRequest request, HttpServletResponse response, OAuth2Authentication auth, Authentication authentication) {

        return "complete";
    }

    @GetMapping(value = "/google/complete")
    public String googleComplete(HttpServletRequest request, HttpServletResponse response, OAuth2Authentication auth, Authentication authentication) {

        return "complete";
    }
}
