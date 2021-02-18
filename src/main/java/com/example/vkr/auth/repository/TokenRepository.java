package com.example.vkr.auth.repository;

import com.example.vkr.auth.model.AuthToken;

public interface TokenRepository {
    /**
     * Save {@link AuthToken}
     * @param authToken must not be {@literal null}.
     */
    void saveToken(final AuthToken authToken);
    /**
     *
     */
    AuthToken getAuthToken(String key);
    /**
     * Delete {@link AuthToken} for given {@code key}
     * @param key must not be {@literal null}
     *
     */
    Boolean deleteByKey(String key);
    void deleteAllByUserName(String userName);
}
