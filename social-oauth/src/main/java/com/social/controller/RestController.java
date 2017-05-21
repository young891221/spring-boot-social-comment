package com.social.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by youngjae on 2017. 5. 21..
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping(value = "/authentication")
    public boolean authentication(Model model) {
        boolean ret = !(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser"));
        String userName = "";

        if (ret) {

            int size = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray().length;

            if (size < 2) {
                Map<String, String> map = (HashMap<String, String>) ((OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication()).getUserAuthentication().getDetails();
                userName = map.get("name");
            }
            else if (size > 1) {
                userName = SecurityContextHolder.getContext().getAuthentication().getName();
            }
        }
        model.addAttribute("userName", userName);
        return ret;
    }
}
