package com.example.vkr.ship.repository.impl;

import com.example.vkr.base.repository.impl.BaseRepositoryImpl;
import com.example.vkr.ship.model.Ship;
import com.example.vkr.ship.repository.ShipRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository("shipRepository")
//@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ShipRepositoryImpl extends BaseRepositoryImpl<Ship, Integer> implements ShipRepository {

    public ShipRepositoryImpl(EntityManager em) {
        super(JpaEntityInformationSupport.getEntityInformation(Ship.class, em));
//        super(Ship.class, em);
    }
}
