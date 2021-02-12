package com.example.vkr.auth.controller;


import com.example.vkr.auth.model.AuthToken;
import com.example.vkr.auth.model.LoginUser;
import com.example.vkr.auth.model.User;
import com.example.vkr.auth.model.UserDto;
import com.example.vkr.auth.service.AuthService;
import com.example.vkr.auth.service.TokenService;
import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@JsonView(View.UI.class)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthService authService;

    /**
     *
     */
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody @Validated(LoginUser.class) final LoginUser loginUser, BindingResult bindingResult)
            throws BindingException, AuthenticationException {
        if (bindingResult.hasErrors()) {
            throw new BindingException("Error json");
        }

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUserName(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final AuthToken authToken = authService.generateToken();

        return ResponseEntity.ok(authToken);
    }

    @GetMapping("/logout")
    @PreAuthorize("hasRole('USER')")
    public void logout() {
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto)
            throws EntityExistsException, BindingException {
        User user = authService.save(userDto);
        return ResponseEntity.status(201).body(user);
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> refresh(HttpServletRequest req) {
//        final AuthToken authToken = authService.generateToken();
        final AuthToken authToken = (AuthToken) req.getAttribute("token");
        return ResponseEntity.ok().body(authToken);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getProfile() {

        return null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/adminPing", method = RequestMethod.GET)
    public String adminPing() {
        return "Only Admins Can Read This";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/userPing", method = RequestMethod.GET)
    public String userPing() {
        return "Any User Can Read This";
    }
}