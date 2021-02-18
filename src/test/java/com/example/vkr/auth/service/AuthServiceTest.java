package com.example.vkr.auth.service;

import com.example.vkr.AbstractTest;
import com.example.vkr.auth.model.Role;
import com.example.vkr.auth.model.User;
import com.example.vkr.auth.model.UserDto;
import com.example.vkr.auth.repository.AuthRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * <p>Before test save User("test2", "password", "89876543210", "qwerty@gmail.com")</p>
 * <p>After test delete all user</p>
 */
public abstract class AuthServiceTest extends AbstractTest {

    @Autowired
    protected AuthService authService;
    @Autowired
    protected AuthRepository authRepository;

    protected UserDto userDto = new UserDto("test1", "password", "89876543210", "qwerty@mail.ru");
    protected UserDto user2Dto = new UserDto("test2", "password", "89876543210", "qwerty@mail.ru");
    protected UserDto user3Dto = new UserDto("test3", "password", "89876543210", "qwerty@gmail.com");
    protected UserDto savedDto = new UserDto("test2", "password", "89876543210", "qwerty@gmail.com");
    protected Role userRole = new Role(1L,"USER", "user role");

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        User user = savedDto.getUserFromDto();
        user.setRoles(Set.of(userRole));
        resetDb();
        authRepository.save(user);
    }

    @Override
    @AfterEach
    protected void reset() throws Exception {
        resetDb();
    }

    private void resetDb() {
        try {
            authRepository.deleteByUserName(userDto.getUserName());
        } catch (Exception ignored) {}
        try {
            authRepository.deleteByUserName(savedDto.getUserName());
        } catch (Exception ignored) {}
    }
}
