package com.example.vkr.auth.controller;

import com.example.vkr.auth.model.AuthToken;
import com.example.vkr.auth.model.LoginUser;
import com.example.vkr.auth.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerSignInTest extends AuthControllerTest {

    private final LoginUser loginUser = new LoginUser("mitinikitos", "password");
    private final LoginUser loginUserByEmail = new LoginUser("email@mail.ru", "password");
    private final LoginUser loginUser2 = new LoginUser("test", "password");
    private final LoginUser loginUser2ByEmail = new LoginUser("qwerty@gmail.com", "password");
    @Autowired
    private TokenService tokenService;

    @Test
    void signInByUserNameSuccess() throws Exception {

        MvcResult mvcResult = mvc.perform(post("/auth/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(loginUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        isAuthTokenSaved(content);
    }

    @Test
    void signInByEmailSuccess() throws Exception {

        MvcResult mvcResult = mvc.perform(post("/auth/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(loginUserByEmail)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        isAuthTokenSaved(content);
    }

    void isAuthTokenSaved(String content) throws IOException {
        AuthToken authToken = mapFromJson(content, AuthToken.class);
        AuthToken savedToken = tokenService.getAuthToken(authToken.getAccessToken());
        assertEquals(authToken.getAccessToken(), savedToken.getAccessToken());
    }
    @Test
    void signInByUserNameFailed() throws Exception {

        mvc.perform(post("/auth/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(loginUser2)))
//                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(result -> {
                    assertEquals("Username invalid", result.getResponse().getErrorMessage());
                });
    }

    @Test
    void signInByEmailFailed() throws Exception {

        mvc.perform(post("/auth/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(loginUser2ByEmail)))
//                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(result -> {
                    assertEquals("Email invalid", result.getResponse().getErrorMessage());
                });
    }

    //TODO доделать после исправления AuthController
    @Test
    void signInFailedNull() throws Exception {

        mvc.perform(post("/auth/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(new LoginUser())))
                .andDo(print());
    }
}