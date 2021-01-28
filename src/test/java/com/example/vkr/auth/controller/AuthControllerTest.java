package com.example.vkr.auth.controller;

import com.example.vkr.VkrApplication;
import com.example.vkr.auth.model.LoginUser;
import com.example.vkr.auth.model.Role;
import com.example.vkr.auth.model.UserDto;
import com.example.vkr.auth.repository.AuthRepository;
import com.example.vkr.auth.repository.RoleRepository;
import com.example.vkr.auth.service.AuthService;
import com.example.vkr.config.JwtAuthenticationFilter;
import com.example.vkr.config.WebSecurityConfig;
import com.example.vkr.exception.UnprocessableEntityException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
/*
@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//@SpringBootTest
@WebMvcTest(AuthController.class)
//@ContextConfiguration(classes = {VkrApplication.class, WebSecurityConfig.class })
//@Import(WebSecurityConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    private ObjectWriter objectWriter;

    @Autowired
    private AuthController authController;

    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private AuthRepository authRepository;

    @MockBean
    private AuthService authService;

//    @Autowired
//    private AuthenticationManager authenticationManager;


    private Role role;
    private UserDto userDto;

//    @Autowired
//    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setup() throws UnprocessableEntityException {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .defaultRequest(get("/**"))
//                .defaultRequest(post("/**"))
//                .apply(springSecurity())
//                .build();

        userDto = new UserDto("nikita", "Mityuhan1312", "89876543210", "qwerty@mail.ru");
        role = new Role("USER", "User role");
        role.setId(2);
        roleRepository.save(role);
        authService.save(userDto);

        objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
    }

    @AfterEach
    void reset() {
        authRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void generateToken() throws Exception {

        LoginUser loginUser = new LoginUser(userDto.getUserName(), userDto.getPassword());

        mvc.perform(
                post("/auth/authenticate")
                        .content(objectWriter.writeValueAsString(loginUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                )
                .andDo(print());
    }

    @Test
    void saveUserSuccessTest() throws Exception {
        mvc.perform(
                post("/auth/register")
                        .content(objectWriter.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                )
                .andDo(print());
    }

    @Test
    void adminPing() {
    }
}
*/
