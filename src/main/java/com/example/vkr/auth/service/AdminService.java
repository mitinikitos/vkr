package com.example.vkr.auth.service;

import com.example.vkr.auth.model.User;
import com.example.vkr.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<User> findAll();
    User findByUserName(String userName) throws EntityNotFoundException;
    User setAdminRole(String userName) throws EntityNotFoundException;
}
