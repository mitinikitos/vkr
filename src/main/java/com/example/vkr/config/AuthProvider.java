package com.example.vkr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Resource(name = "authService")
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);

        if (userDetails != null && (userDetails.getUsername().equals(name))) {
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("Bad password");
            }
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            return new UsernamePasswordAuthenticationToken(name, password, authorities);
        } else
            throw new BadCredentialsException("Username or email not found");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
