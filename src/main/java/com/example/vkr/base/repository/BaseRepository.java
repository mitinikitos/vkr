package com.example.vkr.base.repository;

import com.example.vkr.exception.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> {

    /**
     * Save entity and flush
     * @param <S> extends {@link T}
     * @param entity must not be {@literal null}
     * @return {@link S} if entity save
     * @throws DataIntegrityViolationException if entity exists
     */
    <S extends T> S save(S entity);

    <S extends T> List<S> saveAll(List<S> entities);

    List<T> findAll();

    List<T> findAll(Pageable pageable);

    List<T> findAll(@Nullable Specification<T> spec, Pageable pageable);

    /**
     * @param id must not be {@literal null}
     * @return entity with the given id or {@link Optional#empty()} if entity not exists
     * @throws IllegalArgumentException if {@link ID} is {@literal null}
     */
    Optional<T> findById(ID id);

    /**
     * Delete entity
     * @param entity must not be {@literal null}.
     * @exception EntityNotFoundException if entity not exists
     */
    void delete(T entity) throws EntityNotFoundException;

    /**
     * Delete entity for the given {@link ID}
     * @param id must not be {@literal null}.
     * @exception EntityNotFoundException if entity for the given {@link ID} not exists.
     */
    void deleteById(ID id) throws EntityNotFoundException;
}
