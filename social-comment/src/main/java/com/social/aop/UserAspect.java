package com.social.aop;

import com.social.domain.SocialType;
import com.social.domain.User;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by KimYJ on 2017-06-01.
 */
@Component
@Aspect
public class UserAspect {

    @Around("execution(* *(.., @com.social.annotation.SocialUser (*), ..))")
    public Object convertUser(ProceedingJoinPoint  joinPoint) throws Throwable {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        User user = (User) session.getAttribute("user");
        if(user == null) {
            OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
            Map<String, String> map = (HashMap<String, String>) authentication.getUserAuthentication().getDetails();
            user = checkSocialType(String.valueOf(authentication.getAuthorities().toArray()[0]), map);
        }

        User finalUser = user;
        Object[] args = Arrays.stream(joinPoint.getArgs()).map(data -> { if(data instanceof User) { data = finalUser; } return data; }).toArray();

        return joinPoint.proceed(args);
    }

    private User checkSocialType(String authority, Map<String, String> map) {
        if(SocialType.FACEBOOK.isEquals(authority)) return saveFacebook(map);
        else if(SocialType.GOOGLE.isEquals(authority)) return saveGoogle(map);
        else if(SocialType.KAKAO.isEquals(authority)) return saveKakao(map);
        return null;
    }

    private User saveFacebook(Map<String, String> map) {
        return User.builder()
                .userPrincipal(map.get("id"))
                .userName(map.get("name"))
                .userEmail(map.get("email"))
                .userImage("http://graph.facebook.com/" + map.get("id") + "/picture?type=square")
                .socialType(SocialType.FACEBOOK)
                .build();
    }

    private User saveGoogle(Map<String, String> map) {
        return User.builder()
                .userPrincipal(map.get("id"))
                .userName(map.get("name"))
                .userEmail(map.get("email"))
                .userImage(map.get("picture"))
                .socialType(SocialType.GOOGLE)
                .build();
    }

    private User saveKakao(Map<String, String> map) {
        HashMap<String, String> propertyMap = (HashMap<String, String>)(Object) map.get("properties");
        return User.builder()
                .userPrincipal(String.valueOf(map.get("id")))
                .userName(propertyMap.get("nickname"))
                .userEmail(map.get("kaccount_email"))
                .userImage(propertyMap.get("thumbnail_image"))
                .socialType(SocialType.KAKAO)
                .build();
    }
}

