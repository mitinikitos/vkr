package com.example.vkr.auth.repository;

import com.example.vkr.auth.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthRepository extends CrudRepository<User, UUID> {
    Optional<User> findByUserName(String userName);
    Optional<User> saveAndFlush(User user);
}
