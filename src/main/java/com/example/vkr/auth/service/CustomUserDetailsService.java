package com.example.vkr.auth.service;

import com.example.vkr.auth.model.CustomUserDetails;
import com.example.vkr.auth.model.User;
import com.example.vkr.exception.EmailNotFoundException;

public interface CustomUserDetailsService {
    /**
     * @param email must not be {@literal null}
     * @throws EmailNotFoundException if {@link User} with given {@code email} not exists
     */
    CustomUserDetails loadUserByEmail(String email);
    /**
     * @param username must not be {@literal null}
     * @throws EmailNotFoundException if {@link User} with given {@code username} not exists
     */
    CustomUserDetails loadUserByUsername(String username);
}
