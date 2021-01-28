package com.example.vkr.base.repository;

import com.example.vkr.exception.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> {
    <S extends T> S save(S entity);
    <S extends T> List<S> saveAll(List<S> entities);
    List<T> findAll();
    List<T> findAll(Pageable pageable);
    List<T> findAll(@Nullable Specification<T> spec, Pageable pageable);
    Optional<T> findById(ID id);
    void delete(T entity) throws EntityNotFoundException;
    void deleteById(ID id) throws EntityNotFoundException;
}
