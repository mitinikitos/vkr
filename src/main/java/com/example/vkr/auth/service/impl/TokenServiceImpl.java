package com.example.vkr.auth.service.impl;

import com.example.vkr.auth.model.AuthToken;
import com.example.vkr.auth.repository.TokenRepository;
import com.example.vkr.auth.service.TokenService;
import com.example.vkr.config.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service(value = "tokenService")
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokensRepository;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Override
    public AuthToken saveToken(String token) {
        Long expiration = getExpiration(token);
        AuthToken authToken = new AuthToken(token, expiration);
        String id = generateId(token);
        authToken.setId(id);
        tokensRepository.saveToken(authToken);
        return authToken;
    }

    @Override
    public Boolean deleteByToken(String token) {
        String key = generateId(token);
        return tokensRepository.deleteByKey(key);
    }

    @Override
    public AuthToken refreshToken(String token) {
        Boolean isDeleteToken = deleteByToken(token);
        if (!isDeleteToken) {
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String newToken = jwtTokenUtil.generateToken(authentication);
        return saveToken(newToken);
    }

    @Override
    public AuthToken getAuthToken(String token) {
        String id = generateId(token);
        return tokensRepository.getAuthToken(id);
    }

    private String generateId(String token) {
        Date date = jwtTokenUtil.getExpirationDateFromToken(token);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        return userName + "-" + date.getTime();
    }

    private Long getExpiration(String token) {
        Date date = jwtTokenUtil.getExpirationDateFromToken(token);
        return new Date(date.getTime() - System.currentTimeMillis()).getTime();
    }
}