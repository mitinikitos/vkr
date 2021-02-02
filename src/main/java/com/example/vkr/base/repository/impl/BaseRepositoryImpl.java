package com.example.vkr.base.repository.impl;

import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.base.repository.BaseRepository;
import lombok.NoArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.util.ProxyUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

@Repository("baseRepository")
@NoArgsConstructor
public class BaseRepositoryImpl<T, ID> implements BaseRepository<T, ID> {

    @PersistenceContext(unitName = "entityManagerFactory")
    protected EntityManager em;

    protected JpaEntityInformation<T, ?> information;

    /**
     * Creates a new {@link BaseRepositoryImpl} for the given {@link JpaEntityInformation}
     * @param information must not be {@literal null}
     */
    public BaseRepositoryImpl(JpaEntityInformation<T, ?> information) {
        this.information = information;
    }

    /**
     * Return {@link Class<T>} for the given {@link JpaEntityInformation}
     * @return {@link Class<T>}
     */
    protected Class<T> getDomainClass() {
        return information.getJavaType();
    }

    /**
     * Creates {@link TypedQuery} for the given {@link Specification} and {@link Sort}
     * @param spec can be {@literal null}.
     * @param sort must not be {@literal null}.
     * @return {@link TypedQuery}
     */
    protected TypedQuery<T> getQuery(@Nullable Specification<T> spec, Sort sort) {
        return getQuery(spec, getDomainClass(), sort);
    }

    /**
     * Creates {@link TypedQuery} for the given {@link Specification} and {@link Sort}
     * @param spec can be {@literal null}.
     * @param pageable must not be {@literal null}.
     * @return {@link TypedQuery}
     */
    protected TypedQuery<T> getQuery(@Nullable Specification<T> spec, Pageable pageable) {

        Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
        return getQuery(spec, sort);
    }

    /**
     * Creates {@link TypedQuery} for the given {@link Specification} and {@link Sort}
     * @param <S> extends {@link T}
     * @param spec can be {@literal null}.
     * @param domainClass must not be {@literal null}.
     * @param sort must not be {@literal null}.
     * @return {@link TypedQuery}
     */
    protected <S extends T> TypedQuery<S> getQuery(@Nullable Specification<S> spec, Class<S> domainClass, Sort sort) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<S> query = builder.createQuery(domainClass);

        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);

        query.select(root);

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return em.createQuery(query);
    }

    private <S, U extends T> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec, Class<U> domainClass,
                                                                  CriteriaQuery<S> query) {

        Assert.notNull(domainClass, "Domain class must not be null!");
        Assert.notNull(query, "CriteriaQuery must not be null!");

        Root<U> root = query.from(domainClass);

        if (spec == null) {
            return root;
        }

        CriteriaBuilder builder = em.getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }
    /**
     * Returns {@link Pageable#unpaged()} for the given {@link Pageable}
     * @param pageable must not be {@literal null}.
     * @return {@link Pageable#unpaged()}
     */
    protected static boolean isUnpaged(Pageable pageable) {
        return pageable.isUnpaged();
    }

    /**
     * Return {@link List}
     */
    @Override
    public List<T> findAll() {
        return getQuery(null, Sort.unsorted()).getResultList();
    }

    /**
     * Return {@link List} for the given {@link Pageable}
     * @param pageable must not be {@literal null}.
     * @return {@link List}
     */
    @Override
    public List<T> findAll(Pageable pageable) {

        if (isUnpaged(pageable)) {
            return findAll();
        }

        return findAll((Specification<T>) null, pageable);
    }

    /**
     * Return {@link List} for the given {@link Specification} and {@link Pageable}
     * @param spec can be {@literal null}.
     * @param pageable must not be {@literal null}.
     * @return {@link List}
     */
    @Override
    public List<T> findAll(@Nullable Specification<T> spec, Pageable pageable) {

        TypedQuery<T> query = getQuery(spec, pageable);

        return isUnpaged(pageable) ? findAll() : findAll(query, pageable);
    }

    protected <S extends T> List<S> findAll(TypedQuery<S> query, Pageable pageable) {

        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        return query.getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = { DataIntegrityViolationException.class, ConstraintViolationException.class })
    public <S extends T> S save(S entity) {

        Assert.notNull(entity, "Entity must not be null.");

        em.persist(entity);
        em.flush();

        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.NESTED,
            noRollbackFor = { DataIntegrityViolationException.class, ConstraintViolationException.class })
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
    public Optional<T> findById(ID id) {

        Class<T> domain = getDomainClass();

        return Optional.ofNullable(em.getReference(domain, id));
    }


    /**
     * Delete entity
     * @param entity must not be {@literal null}.
     * @exception EntityNotFoundException if entity not exists
     */
    @Override
    @Transactional
    public void delete(T entity) throws EntityNotFoundException {

        Assert.notNull(entity, "Entity must not be null!");

        Class<?> type = ProxyUtils.getUserClass(entity);

        T existing = (T) em.find(type, information.getId(entity));

        if (existing == null) {
            throw new EntityNotFoundException(String.format("No %s entity exists!", getDomainClass()));
        }

        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    /**
     * Delete entity for the given {@link ID}
     * @param id must not be {@literal null}.
     * @exception EntityNotFoundException if entity for the given {@link ID} not exists.
     */
    @Override
    @Transactional
    public void deleteById(ID id) throws EntityNotFoundException {

        Assert.notNull(id, "Id must not be null");

        delete(findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("No %s entity with id %s!", getDomainClass(), id))));
    }
}
