package com.example.vkr.auth.service.impl;

import com.example.vkr.auth.model.AuthToken;
import com.example.vkr.auth.model.LockedToken;
import com.example.vkr.auth.repository.TokenRepository;
import com.example.vkr.auth.service.TokenService;
import com.example.vkr.config.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service(value = "tokenService")
public class TokenServiceImpl implements TokenService {

//    @Autowired
//    private TokenRepository tokenRepository;

    @Autowired
    private TokenRepository tokensRepository;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Override
    public void saveLockedToken(String token) {
        Date date = jwtTokenUtil.getExpirationDateFromToken(token);
        System.out.println(date);
        System.out.println(new Date());
        Long expiration = new Date(date.getTime() - System.currentTimeMillis()).getTime();
//        Long expiration = (date.getTime() - (new Date().getTime()));
        LockedToken lockedToken = new LockedToken(token, expiration);
        System.out.println(expiration);
        tokensRepository.saveLockedToken(lockedToken);
//        tokenRepository.save(lockedToken);
    }


    @Override
    public LockedToken findByToken(AuthToken token) {
        return tokensRepository.get(token.getToken());
    }

    @Override
    public boolean isBlackList(String token) {
        return tokensRepository.get(token) != null;
    }
}
