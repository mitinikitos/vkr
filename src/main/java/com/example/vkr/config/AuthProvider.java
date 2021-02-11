package com.example.vkr.config;

import com.example.vkr.auth.model.CustomUserDetails;
import com.example.vkr.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AuthProvider implements AuthenticationProvider {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*\\.[A-Za-z]{2,}$";
    @Resource(name = "userDetailsService")
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = (String) authentication.getCredentials();
        CustomUserDetails userDetails;
        if (isEmail(name)) {
            userDetails = userDetailsService.loadUserByEmail(name);
        } else {
            userDetails = userDetailsService.loadUserByUsername(name);
        }

        if (userDetails != null && (userDetails.getUsername().equals(name) || userDetails.getEmail().equals(name))) {
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("Bad password");
            }
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), password, authorities);
        } else
            throw new BadCredentialsException("Username or email not found");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    private boolean isEmail(String input) {
        if (input != null)
        {
            Pattern p = Pattern.compile(EMAIL_PATTERN);
            Matcher m = p.matcher(input);
            return m.find();
        }
        return false;
    }
}