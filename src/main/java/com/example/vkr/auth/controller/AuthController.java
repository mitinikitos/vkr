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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@JsonView(View.UI.class)
public class AuthController {

    @Value("${jwt.header.string}")
    private String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    private String TOKEN_PREFIX;

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
    public ResponseEntity<?> generateToken(@RequestBody @Valid LoginUser loginUser, BindingResult bindingResult)
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
        final AuthToken authToken = authService.generateToken(authentication);

        return ResponseEntity.ok(authToken);
    }

    @GetMapping("/logout")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res) {
        String authToken = getTokenFromHeader(req);

        tokenService.saveLockedToken(authToken);
        return ResponseEntity.ok().body("by");
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto)
            throws EntityExistsException, BindingException {
        User user = authService.save(userDto);
        return ResponseEntity.status(201).body(user);
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> refresh() {
        final AuthToken authToken = authService.refresh();
        return ResponseEntity.ok().body(authToken);
    }

    private String getTokenFromHeader(HttpServletRequest req) {
        String header = req.getHeader(HEADER_STRING);

        return header.replace(TOKEN_PREFIX, "").trim();
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
