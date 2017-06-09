package com.social.controller;

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
            if(map.get("name") == null) {
                HashMap<String, String> propertyMap = (HashMap<String, String>)(Object) map.get("properties");
                model.addAttribute("name", propertyMap.get("nickname"));
            } else {
                model.addAttribute("name", map.get("name"));
            }
        }
        return "login";
    }

    @GetMapping(value = "/error")
    public String error(@RequestParam String message, Model model) {
        model.addAttribute("message", message);
        return "error";
    }
}
