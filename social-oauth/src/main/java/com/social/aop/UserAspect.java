package com.social.aop;

import com.social.domain.SocialType;
import com.social.domain.User;
import com.social.service.UserService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KimYJ on 2017-06-01.
 */
@Component
@Aspect
public class UserAspect {

    @Autowired
    private UserService userService;

    @Around("@annotation(com.social.annotation.SaveSocialUser)")
    public Object checkAndSaveUser(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof OAuth2Authentication) {
                OAuth2Authentication authentication = (OAuth2Authentication) args[i];
                Map<String, String> map = (HashMap<String, String>) authentication.getUserAuthentication().getDetails();
                checkSocialType(String.valueOf(authentication.getAuthorities().toArray()[0]), map);
            }
        }
        return joinPoint.proceed();
    }

    private void checkSocialType(String authority, Map<String, String> map) {
        if(SocialType.FACEBOOK.getRoleType().equals(authority)) saveFacebook(map);
        else if(SocialType.GOOGLE.getRoleType().equals(authority)) saveGoogle(map);
        else if(SocialType.KAKAO.getRoleType().equals(authority)) saveKakao(map);
    }

    private void saveFacebook(Map<String, String> map) {
        if(userService.isExistUser(map.get("id"))) {
            userService.saveUser(User.builder()
                    .userPrincipal(map.get("id"))
                    .userName(map.get("name"))
                    .userEmail(map.get("email"))
                    .userImage("http://graph.facebook.com/" + map.get("id") + "/picture?type=square")
                    .socialType(SocialType.FACEBOOK)
                    .build());
        }
    }

    private void saveGoogle(Map<String, String> map) {
        if(userService.isExistUser(map.get("id"))) {
            userService.saveUser(User.builder()
                    .userPrincipal(map.get("id"))
                    .userName(map.get("name"))
                    .userEmail(map.get("email"))
                    .userImage(map.get("picture"))
                    .socialType(SocialType.GOOGLE)
                    .build());
        }
    }

    private void saveKakao(Map<String, String> map) {
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
    }
}

