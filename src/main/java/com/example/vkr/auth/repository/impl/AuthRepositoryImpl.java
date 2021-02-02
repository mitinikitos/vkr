package com.example.vkr.auth.repository.impl;

import com.example.vkr.auth.model.User;
import com.example.vkr.auth.repository.AuthRepository;
import com.example.vkr.base.repository.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;
import java.util.UUID;

@Repository("authRepository")
public class AuthRepositoryImpl extends BaseRepositoryImpl<User, UUID> implements AuthRepository {

    private static final String SELECT_USER_BY_USERNAME = "select u from User u where u.userName=:userName";

    @Override
    public <S extends User> S save(S entity) {
        return super.save(entity);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        TypedQuery<User> typedQuery = em.createQuery(SELECT_USER_BY_USERNAME, User.class);
        typedQuery.setParameter("userName", userName);
        typedQuery.setMaxResults(1);

        try {
            return Optional.ofNullable(typedQuery.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
