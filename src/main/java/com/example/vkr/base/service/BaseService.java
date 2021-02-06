package com.example.vkr.base.service;

import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.EntityNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<T, ID> {
    /**
     * Save entity
     * @param entity must not be {@literal null}
     * @exception EntityExistsException if entity already exists
     * @exception BindingException if {@literal entity} or entity id is {@literal null}
     * @return saved entity
     */
    <S extends T> S save(S entity) throws EntityExistsException, BindingException;
    /**
     *
     */
    <S extends T> List<S> saveAll(List<S> entities);
    /**
     * Return {@link T} for the given {@link ID}
     * @param id must not be {@literal null}.
     * @exception EntityNotFoundException if {@link T} with given {@link ID} not exists
     * @exception BindingException if {@literal id} is {@literal null}.
     * @return {@link T}
     */
    T findById(ID id) throws EntityNotFoundException, BindingException;
    List<T> findAll();
    List<T> findAll(Pageable pageable);
    /**
     * Delete given {@literal entity}
     * @param entity must not be {@literal null}
     * @exception EntityNotFoundException if given {@literal entity} not exists
     * @exception BindingException if given {@literal entity} is {@literal null}
     */
    void delete(T entity) throws EntityNotFoundException, BindingException;
    /**
     * Delete entity with given {@literal id}
     * @param id must not be {@literal null}.
     * @exception EntityNotFoundException if {@literal entity} with given {@literal id} not exists
     * @exception BindingException if {@literal id} is {@literal null}
     */
    void deleteById(ID id) throws EntityNotFoundException, BindingException;
}