package com.example.vkr.auth.controller;

import com.example.vkr.auth.model.AuthToken;
import com.example.vkr.auth.service.TokenService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthControllerRefreshAndLogoutTest extends AuthControllerTest {

    @Autowired
    private TokenService tokenService;
    private AuthToken authToken;

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
        MvcResult mvcResult = mvc.perform(post("/auth/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(user2)))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        authToken = mapFromJson(content, AuthToken.class);
    }

    @Test
    void refreshTokenSuccess() throws Exception {
        //Для корректного обновления токена
        Thread.sleep(1000);
        MvcResult mvcResult = mvc.perform(get("/auth/refresh")
                .header("Authorization", "Bearer " + authToken.getAccessToken()))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        AuthToken newToken = mapFromJson(content, AuthToken.class);

        AuthToken savedToken = tokenService.getAuthToken(newToken.getAccessToken());

        Assert.assertNotNull(savedToken);
        AuthToken oldToken = tokenService.getAuthToken(authToken.getAccessToken());
        Assert.assertNull(oldToken);
    }

    @Test
    void refreshFailed() throws Exception {
        Thread.sleep(4000);

        mvc.perform(get("/auth/refresh")
                .header("Authorization", "Bearer " + authToken.getAccessToken()))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(result -> {
                    Assert.assertEquals("The token has expired", result.getResponse().getErrorMessage());
                });
    }

    @Test
    void logout() throws Exception {

        mvc.perform(get("/auth/logout")
                .header("Authorization", "Bearer " + authToken.getAccessToken()))
                .andExpect(status().isOk());
        AuthToken logoutToken = tokenService.getAuthToken(authToken.getAccessToken());
        Assert.assertNull(logoutToken);
    }
}
