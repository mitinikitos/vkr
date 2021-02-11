package com.example.vkr.base.service.impl;

import com.example.vkr.base.service.BaseService;
import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.base.repository.BaseRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("baseService")
public class BaseServiceImpl<T, ID> implements BaseService<T, ID> {

//    @Resource(name = "baseRepository")
    protected BaseRepository<T, ID> baseRepository;

    public BaseServiceImpl(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public <S extends T> S save(S entity) {
        try {
            Assert.notNull(entity, "The entity must not be null");
            return baseRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new EntityExistsException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new BindingException(e.getMessage());
        }
    }

    @Override
    public <S extends T> List<S> saveAll(List<S> entities) {

        List<S> res = new ArrayList<>();
        for (S s : entities){
            try {
                S addedS = save(s);
                res.add(addedS);
            } catch (Exception ignored) {}
        }
        return res;
    }

    @Override
    public T findById(ID id) throws EntityNotFoundException, BindingException {
        try {
            Assert.notNull(id, "Id must not be null");

            Optional<T> optionalT = baseRepository.findById(id);

            if (optionalT.isEmpty()) {
                throw new EntityNotFoundException("Entity not found");
            }

            return optionalT.get();
        } catch (IllegalArgumentException e) {
            throw new BindingException(e.getMessage());
        }
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public List<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    @Override
    public void delete(T entity) throws EntityNotFoundException, BindingException {
        try {
            Assert.notNull(entity, "Entity must not be null");

            baseRepository.delete(entity);

        } catch (IllegalArgumentException e) {
            throw new BindingException(e.getMessage());
        }
    }

    @Override
    public void deleteById(ID id) throws EntityNotFoundException, BindingException {
        try {
            Assert.notNull(id, "Id must not be null");

            baseRepository.deleteById(id);

        } catch (IllegalArgumentException e) {
            throw new BindingException(e.getMessage());
        }
    }
}
