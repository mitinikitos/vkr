package com.example.vkr.auth.controller;

import com.example.vkr.auth.model.AuthToken;
import com.example.vkr.auth.model.LoginUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class AuthControllerDeleteTest extends AuthControllerTest {

    private AuthToken authToken;

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
        MvcResult mvcResult = mvc.perform(post("/auth/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(new LoginUser("mitinikitos", "password"))))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        authToken = mapFromJson(content, AuthToken.class);
    }

    @Test
    void deleteUserSuccess() throws Exception {

        mvc.perform(delete("/auth/profile")
                .header("Authorization", "Bearer " + authToken.getAccessToken()))
                .andDo(print());
    }
}
