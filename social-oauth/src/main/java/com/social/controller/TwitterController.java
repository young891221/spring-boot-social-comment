package com.social.controller;

import com.social.annotation.SaveSocialUser;
import com.social.domain.SocialType;
import com.social.domain.User;
import com.social.service.UserService;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by KimYJ on 2017-05-30.
 */
@Controller
public class TwitterController {
    private static String clientId, clientSecret, callbackUri;

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @PostConstruct
    private void init() {
        clientId = env.getProperty("twitter.clientId");
        clientSecret = env.getProperty("twitter.clientSecret");
        callbackUri = env.getProperty("twitter.callbackUri");
    }

    @GetMapping(value = "/login/twitter")
    public void twitter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OAuth1Operations operations = new TwitterConnectionFactory(clientId, clientSecret).getOAuthOperations();
        OAuthToken oAuthToken = operations.fetchRequestToken(callbackUri, null);
        String authenticationUrl = operations.buildAuthenticateUrl(oAuthToken.getValue(), new OAuth1Parameters());

        request.getServletContext().setAttribute("token", oAuthToken);
        response.sendRedirect(authenticationUrl);
    }

    @GetMapping(value = "/twitter/complete")
    public String twitterComplete(HttpServletRequest request, @RequestParam(name = "oauth_token") String oauthToken, @RequestParam(name = "oauth_verifier") String oauthVerifier) {
        Connection<Twitter> connection = getAccessTokenToConnection(request, oauthVerifier);
        Map<String, String> map = getUserInfoMap(connection);
        setAuthentication(map);
        saveUserIfNotExist(connection, map);

        return "complete";
    }

    private Connection<Twitter> getAccessTokenToConnection(HttpServletRequest request, @RequestParam(name = "oauth_verifier") String oauthVerifier) {
        TwitterConnectionFactory twitterConnectionFactory = new TwitterConnectionFactory(clientId, clientSecret);
        OAuth1Operations operations = twitterConnectionFactory.getOAuthOperations();
        OAuthToken requestToken = (OAuthToken)request.getServletContext().getAttribute("token");
        request.getServletContext().removeAttribute("token");
        OAuthToken accessToken = operations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauthVerifier), null);
        return twitterConnectionFactory.createConnection(accessToken);
    }

    private Map<String, String> getUserInfoMap(Connection<Twitter> connection) {
        Map<String, String> map = new HashMap<>();
        String userPrincipal = connection.getKey().getProviderUserId();
        String userName = connection.getDisplayName();
        map.put("name", userName);
        map.put("id", userPrincipal);
        return map;
    }

    private void setAuthentication(Map<String, String> map) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(map.get("id"),
                "N/A", Arrays.asList(new GrantedAuthority[]{new SimpleGrantedAuthority(SocialType.TWITTER.getRoleType())}));
        authenticationToken.setDetails(map);
        OAuth2Request oAuth2Request = new OAuth2Request(null, map.get("id"), null, true, null,
                null, null, null, null);
        Authentication authentication = new OAuth2Authentication(oAuth2Request, authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void saveUserIfNotExist(Connection<Twitter> connection, Map<String, String> map) {
        if(userService.isExistUser(map.get("id"))) {
            userService.saveUser(User.builder()
                    .userPrincipal(map.get("id"))
                    .userName(map.get("name"))
                    .userEmail(connection.fetchUserProfile().getEmail())
                    .userImage(connection.getImageUrl())
                    .socialType(SocialType.TWITTER)
                    .build());
        }
    }

}
