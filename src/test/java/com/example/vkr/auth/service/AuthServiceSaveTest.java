package com.example.vkr.auth.service;

import com.example.vkr.auth.model.Role;
import com.example.vkr.auth.model.User;
import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityExistsException;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

public class AuthServiceSaveTest extends AuthServiceTest {


    @Test
    void saveUserSuccess() {

        User savedUser = authService.save(userDto);
        Set<Role> roleSet = savedUser.getRoles();
        Iterator<Role> roleIterator = roleSet.iterator();

        assertEquals(1, roleSet.size());
        Role userRole = roleIterator.next();
        assertEquals(userRole.getName(), this.userRole.getName());
    }

    @Test
    void saveUserFailedUserNameExists() {

        assertThrows(EntityExistsException.class, () -> authService.save(user2Dto));

        Optional<User> optionalUser = authRepository.findByUserName(user2Dto.getUserName());

        assertNotEquals(Optional.empty(), optionalUser);
        assertEquals(user2Dto.getUserName(), optionalUser.get().getUserName());
    }
    @Test
    void saveUserFailedEmailExists() {

        assertThrows(EntityExistsException.class, () -> authService.save(user3Dto));

        Optional<User> optionalUser = authRepository.findByEmail(user3Dto.getEmail());

        assertNotEquals(Optional.empty(), optionalUser);
        assertEquals(user3Dto.getEmail(), optionalUser.get().getEmail());
    }

    @Test
    void saveUserFailedNull() {

        assertThrows(BindingException.class, () -> authService.save(null));

    }
}
