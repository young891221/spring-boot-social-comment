package com.social.config;

import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import redis.embedded.RedisServer;

/**
 * Created by KimYJ on 2017-06-05.
 */
@Component
public class EmbeddedRedis {
    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() throws IOException {
        redisServer = new RedisServer();
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        redisServer.stop();
    }
}
