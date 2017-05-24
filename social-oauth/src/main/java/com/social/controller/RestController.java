package com.social.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by youngjae on 2017. 5. 21..
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping(value = "/authentication")
    public Map<String, Object> authentication(OAuth2Authentication auth) {
        Map<String, Object> map = new HashMap<>();

        if (auth.isAuthenticated()) {
            Map<String, String> authMap = (HashMap<String, String>) auth.getUserAuthentication().getDetails();
            map.put("result", true);
            map.put("name", authMap.get("name"));
        }
        else {
            map.put("result", false);
        }
        return map;
    }
}
