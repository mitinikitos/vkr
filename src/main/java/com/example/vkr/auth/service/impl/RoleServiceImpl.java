package com.example.vkr.auth.service.impl;

import com.example.vkr.auth.model.Role;
import com.example.vkr.auth.repository.RoleRepository;
import com.example.vkr.auth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
