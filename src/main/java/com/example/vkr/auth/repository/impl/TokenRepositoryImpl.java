package com.example.vkr.auth.repository.impl;

import com.example.vkr.auth.model.AuthToken;
import com.example.vkr.auth.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository("redisRepository")
public class TokenRepositoryImpl implements TokenRepository {

    @Autowired
    private RedisTemplate<String, AuthToken> redisTokenTemplate;


    @Override
    public void saveToken(final AuthToken authToken) {
        redisTokenTemplate.opsForValue().set(authToken.getId(), authToken, authToken.getExpiration(), TimeUnit.MILLISECONDS);
    }

    @Override
    public AuthToken getAuthToken(String key) {
        return redisTokenTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean deleteByKey(String key) {
        return redisTokenTemplate.delete(key);
    }

    @Override
    public void deleteAllByUserName(String userName) {
        Set<byte[]> byteKeys = redisTokenTemplate.getConnectionFactory().getConnection().keys(("*" + userName + "*").getBytes());
        for (byte[] next : Objects.requireNonNull(byteKeys)) {
            String key = new String(next, 0, next.length);
            deleteByKey(key);
        }
    }
}
