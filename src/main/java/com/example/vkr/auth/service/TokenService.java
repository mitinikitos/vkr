package com.example.vkr.auth.service;

import com.example.vkr.auth.model.AuthToken;

public interface TokenService {
    AuthToken saveToken(String token);
    AuthToken getAuthToken(String token);
    Boolean deleteByToken(String token);
    AuthToken refreshToken(String token);
}
