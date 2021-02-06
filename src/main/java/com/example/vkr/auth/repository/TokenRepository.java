package com.example.vkr.auth.repository;

import com.example.vkr.auth.model.LockedToken;

public interface TokenRepository {
    /**
     * Save {@link LockedToken} in black list
     * @param token must not be {@literal null}.
     */
    void saveLockedToken(final LockedToken token);
    /**
     * Returns {@link LockedToken} for the given key.
     * @param key is the key to the value.
     * @return {@link LockedToken}
     */
    LockedToken get(String key);
    /**
     *
     */
    boolean isBlockedList(String token);
}
