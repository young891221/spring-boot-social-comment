package com.social.controller;

import com.social.common.SocialType;
import com.social.domain.User;
import com.social.service.UserService;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jeonghoon on 2016-12-12.
 */
@Controller
public class TwitterController {
    private final Logger logger = LoggerFactory.getLogger(TwitterController.class);
    private final String twitterId = "UkCGdoA6PLJvnjC2jDwQ86tar";
    private final String twitterSecret = "e3YwL5IdjAdMXJ9VrStykDwEsBquT8ecENpfDddm8kBS6eDvxw";
    private final String twitCallbackUrl = "http://social.comment.zum.com/twitter/complete";
    //private final String twitCallbackUrl = "http://localhost:8085/twitter/complete";
    private final String hubUrl = "http://hub.zum.com/";

    @Autowired
    private UserService userService;

    TwitterConnectionFactory twitterConnectionFactory;

    //@GetMapping(value = "/login/twitter")
    public void twitter(HttpServletRequest request, HttpServletResponse response) {
        twitterConnectionFactory = new TwitterConnectionFactory(twitterId, twitterSecret);
        OAuth1Operations operations = twitterConnectionFactory.getOAuthOperations();
        OAuth1Parameters params = new OAuth1Parameters();

        OAuthToken oAuthToken = operations.fetchRequestToken(twitCallbackUrl, null);
        request.getServletContext().setAttribute("token", oAuthToken);
        String authenticationUrl = operations.buildAuthenticateUrl(oAuthToken.getValue(), params);
        try {
            response.sendRedirect(authenticationUrl);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    //@GetMapping(value = "/twitter/complete")
    public String twitterComplete(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "oauth_token") String oAuthToken, @RequestParam(name = "oauth_verifier") String oauthVerifier) {
        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            String before = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString().split("_")[1];
            if(before.equals("FACEBOOK")) {
//                SecurityContextHolder.clearContext();
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        }
        OAuth1Operations operations = twitterConnectionFactory.getOAuthOperations();
        OAuthToken requestToken = (OAuthToken)request.getServletContext().getAttribute("token");
        OAuthToken accessToken = operations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauthVerifier), null);
        Connection<Twitter> connection = twitterConnectionFactory.createConnection(accessToken);

        request.getServletContext().setAttribute("connection", connection);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(SocialType.TWITTER.getType()));
        authorities.add(new SimpleGrantedAuthority(connection.getKey().getProviderUserId()));
        authorities.sort(Comparator.comparing(GrantedAuthority::getAuthority));

        Authentication authentication = new UsernamePasswordAuthenticationToken(connection.getDisplayName(), connection.getKey().getProviderId(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String userName = authentication.getPrincipal().toString();
        String userPrincipal = connection.getKey().getProviderUserId();
        String socialType = authentication.getAuthorities().toArray()[1].toString().split("_")[1];
        String userImage = connection.getImageUrl();
        String userUrl = "https://twitter.com/"+userName;

        if(!userService.isUserExist(userName, userPrincipal, SocialType.valueOf(socialType))) {
            userService.saveUser(userName, userPrincipal, SocialType.valueOf(socialType), userImage, userUrl);
        }
        User user = userService.getUser(userName, userPrincipal, SocialType.valueOf(socialType));

        Cookie cookie = new Cookie("userIdx", user.getUserIdx()+"");
        response.addCookie(cookie);
        return "complete";
    }

}
