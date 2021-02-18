package com.example.vkr;

import com.example.vkr.AbstractTest;
import com.example.vkr.VkrApplication;
import com.example.vkr.config.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@WebAppConfiguration
public abstract class ControllerTest extends AbstractTest {

    protected MockMvc mvc;

    @Autowired
    WebApplicationContext context;
    @Autowired
    JwtAuthenticationFilter filter;

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .addFilter(filter)
                .build();
    }

}
