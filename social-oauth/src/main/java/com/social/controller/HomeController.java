package com.social.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by KimYJ on 2017-05-18.
 */
@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String root() {
        return "login";
    }
}
