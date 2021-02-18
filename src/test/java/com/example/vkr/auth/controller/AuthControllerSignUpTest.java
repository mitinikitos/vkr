package com.example.vkr.auth.controller;

import com.example.vkr.auth.model.Role;
import com.example.vkr.auth.model.User;
import com.example.vkr.exception.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerSignUpTest extends AuthControllerTest {

    /**
     * Success signUp.
     * Checking for adding a user to the test database and adding a USER role
     */
    @Test
    void signUpSeccess() throws Exception {

        mvc.perform(post("/auth/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(user)))
                .andDo(print())
                .andExpect(status().isCreated());

        Optional<User> savedUserOptional = authService.findByUserName(user.getUserName());

        assertNotEquals(Optional.empty(), savedUserOptional);
        User savedUser = savedUserOptional.get();

        assertTrue(bcryptEncoder.matches(user.getPassword(), savedUser.getPassword()));
        Set<Role> roleSet = savedUser.getRoles();
//        assertTrue(roleSet.contains(userRole));
    }

    /**
     * Failed signUp.
     * User with given userName already exists
     */
    @Test
    void signUpFailureUserNameExists() throws Exception {

        mvc.perform(post("/auth/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(user2)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityExistsException));
    }

    /**
     * Failed signUp.
     * User with given email already exists.
     */
    @Test
    void signUpFailureEmailExists() throws Exception {

        mvc.perform(post("/auth/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(user3)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityExistsException));
    }
}
