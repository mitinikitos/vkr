package com.example.vkr.auth.service;

import com.example.vkr.auth.model.User;
import com.example.vkr.exception.BindingException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class AuthServiceGetTest extends AuthServiceTest {

    @Test
    void findByUserNameSuccess() {
        Optional<User> optionalUser = authService.findByUserName("test2");
        Assert.assertNotEquals(Optional.empty(), optionalUser);
        User user = optionalUser.get();
        Assert.assertEquals(savedDto.getEmail(), user.getEmail());
    }

    @Test
    void findByUserNameFailed() {
        Optional<User> optionalUser = authService.findByUserName("test");
        Assert.assertEquals(Optional.empty(), optionalUser);
    }

    @Test
    void findByUserNameFailedNull() {
        Assert.assertThrows(BindingException.class, () -> authService.findByUserName(null));
    }

    @Test
    void findByEmailSuccess() {
        Optional<User> optionalUser = authService.findByEmail("qwerty@gmail.com");
        Assert.assertNotEquals(Optional.empty(), optionalUser);
        User user = optionalUser.get();
        Assert.assertEquals(savedDto.getUserName(), user.getUserName());
    }

    @Test
    void findByEmailFailed() {
        Optional<User> optionalUser = authService.findByEmail("qwerty@mail.com");
        Assert.assertEquals(Optional.empty(), optionalUser);
    }

    @Test
    void findByEmailFailedNull() {
        Assert.assertThrows(BindingException.class, () -> authService.findByEmail(null));
    }
}
