package com.example.vkr.ship.service;

import com.example.vkr.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ServiceBase<T, ID> {
    boolean save(T entity);
    void saveAll(List<T> entities);
    Optional<T> findById(ID id);
    List<T> findAll();
}
