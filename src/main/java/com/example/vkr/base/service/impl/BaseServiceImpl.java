package com.example.vkr.base.service.impl;

import com.example.vkr.base.service.BaseService;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class BaseServiceImpl<T, ID> implements BaseService<T, ID> {

    @Autowired
    protected BaseRepository<T, ID> baseRepository;

    @Override
    public <S extends T> S save(S entity) throws EntityExistsException {
        try {
            return baseRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new EntityExistsException(e.getMessage());
        }
    }

    @Override
    public <S extends T> List<S> saveAll(List<S> entities) {
        return null;
    }

    @Override
    public T findById(ID id) throws EntityNotFoundException {

        Optional<T> optionalT = baseRepository.findById(id);

        if (optionalT.isEmpty()) {
            throw new EntityNotFoundException("Entity not found");
        }

        return optionalT.get();
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public List<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }
}
