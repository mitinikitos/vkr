package com.example.vkr.auth.service;

import com.example.vkr.auth.model.AuthToken;
import com.example.vkr.auth.model.User;
import com.example.vkr.auth.model.UserDto;
import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.EntityNotFoundException;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface AuthService {
    /**
     * Register {@link User} for the given {@link UserDto}
     * @param user must not be {@literal null}
     * @return Saved {@link User}
     * @throws EntityExistsException if user with given {@literal userName} exists
     * @throws BindingException if given {@literal user} is {@literal null}
     */
    User save(UserDto user) throws EntityExistsException, BindingException;
    List<User> findAll();
    /**
     * Retrieves {@link User} for the given {@literal userName}
     * @param userName must not be {@literal null}
     * @return the {@link User} for the given {@literal userName} or {@link Optional#empty()} if not exists
     * @throws BindingException if given {@literal userName} is {@literal null}
     */
    Optional<User> findByUserName(String userName);
    /**
     * Retrieves {@link User} for the given {@literal email}
     * @param email must not be {@literal null}
     * @return the {@link User} for the given {@literal email} or {@link Optional#empty()} if not exists
     * @throws BindingException if given {@literal email} is {@literal null}
     */
    Optional<User> findByEmail(String email);
    /**
     * Delete {@link User} for given userName
     * @throws EntityNotFoundException if {@link User} with given {@literal userName} not exists
     */
    void deleteByName(String userName) throws EntityNotFoundException;
    /**
     * Generate AccessToken for given {@link Authentication}
     * @return {@link AuthToken}
     */
    AuthToken generateToken(Authentication authentication);

    /**
     *
     */
    AuthToken refresh();
}
