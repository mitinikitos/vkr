package com.example.vkr.auth.repository.impl;

import com.example.vkr.auth.model.Role;
import com.example.vkr.auth.repository.RoleRepository;
import com.example.vkr.base.repository.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository("roleRepository")
public class RoleRepositoryImpl extends BaseRepositoryImpl<Role, Long> implements RoleRepository {

    private static final String SELECT_ROLE_BY_NAME = "select r from Role r where r.name=:name";

    @Override
    public Role findRoleByName(String name) {
        TypedQuery<Role> typedQuery = em.createQuery(SELECT_ROLE_BY_NAME, Role.class);
        typedQuery.setParameter("name", name);
        typedQuery.setMaxResults(1);
        return typedQuery.getSingleResult();
    }
}
