package com.example.vkr.auth.service;

import com.example.vkr.auth.model.AuthToken;
import com.example.vkr.auth.model.LoginUser;
import com.example.vkr.auth.model.User;
import com.example.vkr.auth.model.UserDto;
import com.example.vkr.exception.UnprocessableEntityException;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface AuthService {
    User save(UserDto user) throws UnprocessableEntityException;
    List<User> findAll();
    Optional<User> findOne(String userName);
    AuthToken generateToken(Authentication authentication);
    AuthToken refresh();
}
