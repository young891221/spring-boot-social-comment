package com.social.controller;

import com.social.domain.SocialType;
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
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by jeonghoon on 2016-12-12.
 */
@Controller
public class TwitterController {
    private final Logger logger = LoggerFactory.getLogger(TwitterController.class);
    private final String twitterId = "qynoCusuv0j4RsAEc4QEKHJBb";
    private final String twitterSecret = "Z5Qc2wWqLd78ZSIGZt49gElElOAcYMY8dIYnWhz96MKnicueDO";
    private final String twitCallbackUrl = "http://localhost:8080/twitter/complete";

    @Autowired
    private UserService userService;

    //@GetMapping(value = "/login/twitter")
    public void twitter(HttpServletRequest request, HttpServletResponse response) {
        OAuth1Operations operations = new TwitterConnectionFactory(twitterId, twitterSecret).getOAuthOperations();
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
    public String twitterComplete(HttpServletRequest request, @RequestParam(name = "oauth_token") String oAuthToken, @RequestParam(name = "oauth_verifier") String oauthVerifier) {
        TwitterConnectionFactory twitterConnectionFactory = new TwitterConnectionFactory(twitterId, twitterSecret);
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

        return "complete";
    }

}
