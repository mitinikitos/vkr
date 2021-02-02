package com.example.vkr.auth.repository;

import com.example.vkr.auth.model.User;
import com.example.vkr.base.repository.BaseRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface AuthRepository extends BaseRepository<User, UUID> {
    Optional<User> findByUserName(String userName);
}
