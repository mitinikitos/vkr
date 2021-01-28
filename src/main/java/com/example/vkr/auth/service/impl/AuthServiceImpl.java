package com.example.vkr.auth.service.impl;

import com.example.vkr.auth.model.*;
import com.example.vkr.auth.repository.AuthRepository;
import com.example.vkr.auth.service.AuthService;
import com.example.vkr.auth.service.RoleService;
import com.example.vkr.config.TokenProvider;
import com.example.vkr.exception.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service(value = "authService")
public class AuthServiceImpl implements UserDetailsService, AuthService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Override
    public AuthToken refresh() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String token = jwtTokenUtil.generateToken(authentication);
        return new AuthToken(token);
    }

    @Override
    public AuthToken generateToken(Authentication authentication) {
//        final Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginUser.getUserName(),
//                        loginUser.getPassword()
//                )
//        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return new AuthToken(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = authRepository.findByUserName(username);
        if (optionalUser.isEmpty()){
            throw new UsernameNotFoundException("Invalid username or password");
        }
        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    @Override
    public User save(UserDto user) throws UnprocessableEntityException {
        User nUser = user.getUserFromDto();
        Optional<User> optionalUser = authRepository.findByUserName(nUser.getUserName());
        if (optionalUser.isPresent()) {
            throw new UnprocessableEntityException("User with the same username already exists");
        }
        nUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        nUser.setRoles(roleSet);
        return authRepository.save(nUser);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) authRepository.findAll();
    }

    @Override
    public Optional<User> findOne(String userName) {
        return authRepository.findByUserName(userName);
    }
}
