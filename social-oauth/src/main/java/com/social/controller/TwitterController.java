package com.social.controller;

import com.social.domain.SocialType;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by KimYJ on 2017-05-30.
 */
@Controller
public class TwitterController {
    private final Logger logger = LoggerFactory.getLogger(TwitterController.class);
    private final String twitterId = "qynoCusuv0j4RsAEc4QEKHJBb";
    private final String twitterSecret = "Z5Qc2wWqLd78ZSIGZt49gElElOAcYMY8dIYnWhz96MKnicueDO";
    private final String twitCallbackUrl = "http://localhost:8080/twitter/complete";

    @GetMapping(value = "/login/twitter")
    public void twitter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OAuth1Operations operations = new TwitterConnectionFactory(twitterId, twitterSecret).getOAuthOperations();
        OAuthToken oAuthToken = operations.fetchRequestToken(twitCallbackUrl, null);
        String authenticationUrl = operations.buildAuthenticateUrl(oAuthToken.getValue(), new OAuth1Parameters());

        request.getServletContext().setAttribute("token", oAuthToken);
        response.sendRedirect(authenticationUrl);
    }

    @GetMapping(value = "/twitter/complete")
    public String twitterComplete(HttpServletRequest request, @RequestParam(name = "oauth_token") String oauthToken, @RequestParam(name = "oauth_verifier") String oauthVerifier) {
        TwitterConnectionFactory twitterConnectionFactory = new TwitterConnectionFactory(twitterId, twitterSecret);
        OAuth1Operations operations = twitterConnectionFactory.getOAuthOperations();
        OAuthToken requestToken = (OAuthToken)request.getServletContext().getAttribute("token");
        request.getServletContext().removeAttribute("token");
        OAuthToken accessToken = operations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauthVerifier), null);
        Connection<Twitter> connection = twitterConnectionFactory.createConnection(accessToken);

        request.getServletContext().setAttribute("connection", connection);

        Map<String, String> map = new HashMap<>();
        String userPrincipal = connection.getKey().getProviderUserId();
        String userName = connection.getDisplayName();
        String userEmail = connection.fetchUserProfile().getEmail();
        String userImage = connection.getImageUrl();
        map.put("name", userName);
        map.put("principal", userPrincipal);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(SocialType.TWITTER.getType()));

        OAuth2Request oAuth2Request = new OAuth2Request(null, userPrincipal, null, true, null,
                null, null, null, null);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, "N/A", authorities);
        authenticationToken.setDetails(map);
        Authentication authentication = new OAuth2Authentication(oAuth2Request, authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "complete";
    }

}
