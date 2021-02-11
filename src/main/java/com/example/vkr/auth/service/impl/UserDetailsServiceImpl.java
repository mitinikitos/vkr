package com.example.vkr.auth.service.impl;

import com.example.vkr.auth.model.CustomUserDetails;
import com.example.vkr.auth.model.SecurityUser;
import com.example.vkr.auth.model.User;
import com.example.vkr.auth.service.AuthService;
import com.example.vkr.auth.service.CustomUserDetailsService;
import com.example.vkr.exception.EmailNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private AuthService authService;

    @Override
    public CustomUserDetails loadUserByEmail(String email) {
        Optional<User> optionalUser = authService.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new EmailNotFoundException("Email invalid");
        }
        User user = optionalUser.get();
        return new SecurityUser(user.getUserName(), user.getEmail(), user.getPassword(), getAuthority(user));
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        Optional<User> optionalUser = authService.findByUserName(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Username invalid");
        }
        User user = optionalUser.get();
        return new SecurityUser(user.getUserName(), user.getEmail(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorities;
    }
}
