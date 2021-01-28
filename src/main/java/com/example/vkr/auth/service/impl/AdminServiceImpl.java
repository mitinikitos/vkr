package com.example.vkr.auth.service.impl;

import com.example.vkr.auth.model.Role;
import com.example.vkr.auth.model.User;
import com.example.vkr.auth.repository.AuthRepository;
import com.example.vkr.auth.service.AdminService;
import com.example.vkr.auth.service.RoleService;
import com.example.vkr.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public List<User> findAll() {
        return (List<User>) authRepository.findAll();
    }

    @Override
    public User findByUserName(String userName) throws EntityNotFoundException {
        Optional<User> optionalUser = authRepository.findByUserName(userName);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User by username " + userName + " not found");
        }
        return optionalUser.get();
    }

    @Override
    public User setAdminRole(String userName) throws EntityNotFoundException {
        Optional<User> optionalUser = authRepository.findByUserName(userName);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User by username " + userName + " not found");
        }
        Role role = roleService.findByName("ADMIN");
        User user = optionalUser.get();
        if (user.isAdmin(role)) {
            return user;
        } else {
            user.addRole(role);
            return authRepository.saveAndFlush(user).get();
        }
    }
}
