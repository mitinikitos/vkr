package com.example.vkr.auth.controller;

import com.example.vkr.ControllerTest;
import com.example.vkr.auth.model.Role;
import com.example.vkr.auth.model.UserDto;
import com.example.vkr.auth.repository.AuthRepository;
import com.example.vkr.auth.repository.impl.AuthRepositoryImpl;
import com.example.vkr.auth.service.AuthService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.mock;

/**
 * <p>Before test save UserDto("mitinikitos", "password", "89876543210","email@mail.ru")</p>
 * <p>After test delete all user</p>
 */
public abstract class AuthControllerTest extends ControllerTest {

    @Autowired
    protected AuthService authService;
    @Autowired
    protected BCryptPasswordEncoder bcryptEncoder;

    protected UserDto user = new UserDto("test", "password", "89876543210","email@gmail.com");
    protected UserDto user2 = new UserDto("mitinikitos", "password", "89876543210","email@mail.ru");
    protected UserDto user3 = new UserDto("nikita", "password", "89876543210", "email@mail.ru");

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
        resetDb();
        authService.save(user2);
    }

    @Override
    @AfterEach
    protected void reset() throws Exception {
        resetDb();
    }

    private void resetDb() {
        try {
            authService.deleteByName(user.getUserName());
        } catch (Exception ignored) {}
        try {
            authService.deleteByName(user2.getUserName());
        } catch (Exception ignored) {}
    }
}