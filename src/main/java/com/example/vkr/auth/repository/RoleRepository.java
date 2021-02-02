package com.example.vkr.auth.repository;

import com.example.vkr.auth.model.Role;
import com.example.vkr.base.repository.BaseRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@NoRepositoryBean
public interface RoleRepository extends BaseRepository<Role, Long> {
    Role findRoleByName(String name);
}
