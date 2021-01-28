package com.example.vkr.auth.service;

import com.example.vkr.auth.model.Role;

public interface RoleService {
    Role findByName(String name);
}
