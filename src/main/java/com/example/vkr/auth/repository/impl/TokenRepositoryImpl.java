package com.example.vkr.auth.repository.impl;

import com.example.vkr.auth.model.LockedToken;
import com.example.vkr.auth.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository("redisRepository")
public class TokenRepositoryImpl implements TokenRepository {

    @Autowired
    private RedisTemplate<String, LockedToken> redisTemplate;


    @Override
    public void saveLockedToken(final LockedToken token) {
        redisTemplate.opsForValue().set(token.getToken(), token, token.getExpiration(), TimeUnit.MILLISECONDS);
    }

    public LockedToken get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean isBlockedList(String token) {
        LockedToken lockedToken = redisTemplate.opsForValue().get(token);
        return lockedToken == null;
    }
}
