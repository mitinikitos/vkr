package com.example.vkr.auth.service.impl;

import com.example.vkr.auth.model.AuthToken;
import com.example.vkr.auth.repository.TokenRepository;
import com.example.vkr.auth.service.TokenService;
import com.example.vkr.config.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
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
        Date date = jwtTokenUtil.getExpirationDateFromToken(token);
        Long expiration = getExpiration(token, date);
        AuthToken authToken = new AuthToken(token, expiration);
        String id = generateId(token, date);
        authToken.setId(id);
        authToken.setExpiration(expiration);
        tokensRepository.saveToken(authToken);
        authToken.setExp(date.getTime());
        return authToken;
    }

    @Override
    public Boolean deleteByToken(String token) {
        try {
            Date date = jwtTokenUtil.getExpirationDateFromToken(token);
            String key = generateId(token, date);
            return tokensRepository.deleteByKey(key);
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    @Override
    public void deleteAllTokenUser(String userName) {
        tokensRepository.deleteAllByUserName(userName);
    }

    @Override
    public AuthToken refreshToken(String token) {
        deleteByToken(token);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String newToken = jwtTokenUtil.generateToken(authentication);
        return saveToken(newToken);
    }

    @Override
    public AuthToken getAuthToken(String token) {
        try {
            Date date = jwtTokenUtil.getExpirationDateFromToken(token);
            String id = generateId(token, date);
            return tokensRepository.getAuthToken(id);
        } catch (ExpiredJwtException e) {
            return null;
        }
    }

    private String generateId(String token, Date date) {
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        return userName + "-" + date.getTime();
    }

    private Long getExpiration(String token, Date date) {
        return new Date(date.getTime() - System.currentTimeMillis()).getTime();
    }
}