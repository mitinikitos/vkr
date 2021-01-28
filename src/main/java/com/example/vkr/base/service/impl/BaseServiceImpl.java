package com.example.vkr.base.service.impl;

import com.example.vkr.base.service.BaseService;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.base.repository.BaseRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public void delete(T entity) throws EntityNotFoundException {
        baseRepository.delete(entity);
    }

    @Override
    public void deleteById(ID id) throws EntityNotFoundException {
        baseRepository.deleteById(id);
    }
}
