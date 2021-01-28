package com.example.vkr.auth.repository;

import com.example.vkr.auth.model.LockedToken;

public interface TokenRepository {
    void saveLockedToken(final LockedToken token);
    LockedToken get(String key);
    boolean isBlockedList(String token);
}
