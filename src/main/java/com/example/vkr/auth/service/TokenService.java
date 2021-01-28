package com.example.vkr.auth.service;

import com.example.vkr.auth.model.AuthToken;
import com.example.vkr.auth.model.LockedToken;

public interface TokenService {
    void saveLockedToken(String token);
    LockedToken findByToken(AuthToken token);
    boolean isBlackList(String token);
}
