package com.example.vkr.auth.service;

import com.example.vkr.auth.model.User;
import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class AuthServiceDeleteTest extends AuthServiceTest {

    @Test
    void deleteSuccess() {
        authService.deleteByName("test2");

        Optional<User> deletedUser = authService.findByUserName("test2");

        assertEquals(Optional.empty(), deletedUser);
    }

    @Test
    void deleteFailedNotFound() {
        assertThrows(EntityNotFoundException.class, () -> authService.deleteByName("test"));
    }

    @Test
    void deleteFailedNullId() {
        assertThrows(BindingException.class, () -> authService.deleteByName(null));
    }
}
