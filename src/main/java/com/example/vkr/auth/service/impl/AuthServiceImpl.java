package com.example.vkr.auth.service.impl;

import com.example.vkr.auth.model.AuthToken;
import com.example.vkr.auth.model.Role;
import com.example.vkr.auth.model.User;
import com.example.vkr.auth.model.UserDto;
import com.example.vkr.auth.repository.AuthRepository;
import com.example.vkr.auth.service.AuthService;
import com.example.vkr.auth.service.RoleService;
import com.example.vkr.auth.service.TokenService;
import com.example.vkr.config.TokenProvider;
import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.EntityNotFoundException;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service(value = "authService")
public class AuthServiceImpl implements AuthService {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;
    @Autowired
    private TokenProvider jwtTokenUtil;

    @Override
    public AuthToken generateToken() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String token = jwtTokenUtil.generateToken(authentication);
        tokenService.saveToken(token);
        return new AuthToken(token);
    }

    public AuthToken refresh() {

        return null;
    }

    @Override
    public User save(UserDto user) throws EntityExistsException, BindingException {
        try {
            Assert.notNull(user);

            User nUser = user.getUserFromDto();
            Optional<User> optionalUser = authRepository.findByUserName(nUser.getUserName());
            if (optionalUser.isPresent()) {
                throw new EntityExistsException("User with the same username already exists");
            }
            nUser.setPassword(bcryptEncoder.encode(user.getPassword()));

            Role role = roleService.findByName("USER");
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);

            nUser.setRoles(roleSet);
            return authRepository.save(nUser);

        } catch (DataIntegrityViolationException e) {
            throw new EntityExistsException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new BindingException(e.getMessage());
        }
    }

    @Override
    public List<User> findAll() {
        return authRepository.findAll();
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        try {
            Assert.notNull(userName, "UserName must not be null");

            return authRepository.findByUserName(userName);
        } catch (IllegalArgumentException e) {
            throw new BindingException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            Assert.notNull(email, "Email must not be null");

            return authRepository.findByEmail(email);

        } catch (IllegalArgumentException e) {
            throw new BindingException(e.getMessage());
        }
    }

    @Override
    public User getProfile() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        Optional<User> optionalUser = authRepository.findByUserName(userName);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("");
        }
        return optionalUser.get();
    }

    @Override
    public void deleteByName(String userName) {
        try {
            Assert.notNull(userName, "Username must not be null");
            authRepository.deleteByUserName(userName);
        } catch (IllegalArgumentException e) {
            throw new BindingException(e.getMessage());
        }
    }
}