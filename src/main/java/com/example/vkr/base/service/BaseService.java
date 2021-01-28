package com.example.vkr.base.service;

import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.EntityNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<T, ID> {
    <S extends T> S save(S entity) throws EntityExistsException;
    <S extends T> List<S> saveAll(List<S> entities);
    T findById(ID id) throws EntityNotFoundException;
    List<T> findAll();
    List<T> findAll(Pageable pageable);
}
