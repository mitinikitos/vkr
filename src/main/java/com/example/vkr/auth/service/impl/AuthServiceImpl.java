package com.example.vkr.auth.service.impl;

import com.example.vkr.auth.model.AuthToken;
import com.example.vkr.auth.model.Role;
import com.example.vkr.auth.model.User;
import com.example.vkr.auth.model.UserDto;
import com.example.vkr.auth.repository.AuthRepository;
import com.example.vkr.auth.service.AuthService;
import com.example.vkr.auth.service.RoleService;
import com.example.vkr.config.TokenProvider;
import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.EntityNotFoundException;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public AuthToken refresh() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String token = jwtTokenUtil.generateToken(authentication);
        return new AuthToken(token);
    }

    @Override
    public AuthToken generateToken(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return new AuthToken(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser;
        String userName;
        User user;
        if (isEmail(username)) {
            optionalUser = authRepository.findByEmail(username);
            if (optionalUser.isEmpty())
                throw new UsernameNotFoundException("Invalid email");
            user = optionalUser.get();
            userName = user.getEmail();
        } else {
            optionalUser = authRepository.findByUserName(username);
            if (optionalUser.isEmpty()){
                throw new UsernameNotFoundException("Invalid username");
            }
            user = optionalUser.get();
            userName = user.getUserName();
        }
        return new org.springframework.security.core.userdetails.User(userName, user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorities;
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
    public void deleteByName(String userName) throws EntityNotFoundException {
        authRepository.deleteByUserName(userName);
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
