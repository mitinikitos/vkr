package com.example.vkr.auth.repository.impl;

import com.example.vkr.auth.model.User;
import com.example.vkr.auth.repository.AuthRepository;
import com.example.vkr.base.repository.impl.BaseRepositoryImpl;
import com.example.vkr.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;
import java.util.UUID;

@Repository("authRepository")
public class AuthRepositoryImpl extends BaseRepositoryImpl<User, UUID> implements AuthRepository {

    private static final String SELECT_USER_BY_USERNAME = "select u from User u where u.userName=:userName";
    private static final String SELECT_USER_BY_EMAIL = "select u from User u where u.email=:email";
    private static final String DELETE_USER_ROLE = "delete from user_role where user_id=:userId";
    private static final String DELETE_USER = "delete from users where id=:userId";

    public AuthRepositoryImpl(EntityManager em) {
        super(JpaEntityInformationSupport.getEntityInformation(User.class, em));
    }

    @Override
    public Optional<User> findByUserName(String userName) {

        try {
            TypedQuery<User> typedQuery = em.createQuery(SELECT_USER_BY_USERNAME, User.class);
            typedQuery.setParameter("userName", userName);
            typedQuery.setMaxResults(1);

            return Optional.ofNullable(typedQuery.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            TypedQuery<User> typedQuery = em.createQuery(SELECT_USER_BY_EMAIL, User.class);
            typedQuery.setParameter("email", email);
            typedQuery.setMaxResults(1);

            return Optional.ofNullable(typedQuery.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void delete(User user) throws EntityNotFoundException {
        em.createNativeQuery(DELETE_USER_ROLE).setParameter("userId", user.getId()).executeUpdate();
        em.createNativeQuery(DELETE_USER).setParameter("userId", user.getId()).executeUpdate();
    }

    @Override
    @Transactional
    public void deleteByUserName(String userName) throws EntityNotFoundException {

        delete(findByUserName(userName).orElseThrow(() -> new EntityNotFoundException(
                String.format("User with userName '%s' not exists", userName)
        )));

    }


}
