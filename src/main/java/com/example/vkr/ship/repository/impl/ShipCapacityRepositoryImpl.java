package com.example.vkr.ship.repository.impl;

import com.example.vkr.base.repository.BaseRepository;
import com.example.vkr.base.repository.impl.BaseRepositoryImpl;
import com.example.vkr.ship.model.ShipCapacity;
import com.example.vkr.ship.repository.ShipCapacityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;

@Repository("shipCapacityRepository")
public class ShipCapacityRepositoryImpl extends BaseRepositoryImpl<ShipCapacity, Integer> implements ShipCapacityRepository {


    /**
     * Creates {@link BaseRepository} for the given {@link JpaEntityInformation}
     * @param em entityManager from configuration files.
     */
    @Autowired
    public ShipCapacityRepositoryImpl(EntityManager em) {
        super(JpaEntityInformationSupport.getEntityInformation(ShipCapacity.class, em));
    }

    @Override
    @Transactional
    public <S extends ShipCapacity> S save(S entity) {

        Assert.notNull(entity, "Entity must not be null.");

        return em.merge(entity);
    }
}
