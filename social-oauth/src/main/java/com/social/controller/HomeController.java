package com.social.controller;

import com.social.domain.User;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KimYJ on 2017-05-18.
 */
@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String root(OAuth2Authentication auth, Model model) {
        if(auth != null && auth.isAuthenticated()) {
            Map<String, String> map = (HashMap<String, String>) auth.getUserAuthentication().getDetails();
            model.addAttribute("name", map.get("name"));
        }
        return "login";
    }

    @GetMapping(value = "/error")
    public String error(@RequestParam String message, Model model) {
        model.addAttribute("message", message);
        return "error";
    }
}
