package com.example.vkr.auth.repository;

import com.example.vkr.auth.model.User;
import com.example.vkr.base.repository.BaseRepository;
import com.example.vkr.exception.EntityNotFoundException;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface AuthRepository extends BaseRepository<User, UUID> {
    /**
     * Retrievies {@link User} for the given {@literal userName}
     * @param userName must not be {@literal null}
     * @return the {@link User} for the given {@literal userName} or {@link Optional#empty()} if not exists
     */
    Optional<User> findByUserName(String userName);
    /**
     * Retrievies {@link User} for the given {@literal email}
     * @param email must not be {@literal null}
     * @return the {@link User} for the given {@literal email} or {@link Optional#empty()} if not exists
     */
    Optional<User> findByEmail(String email);
    /**
     * Delete {@link User} for the given {@literal userName}
     * @throws EntityNotFoundException if {@link User} with given {@literal userName} not exists
     */
    void deleteByUserName(String userName) throws EntityNotFoundException;
}
