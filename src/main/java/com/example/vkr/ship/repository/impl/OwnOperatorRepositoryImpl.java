package com.example.vkr.ship.repository.impl;


import com.example.vkr.ship.model.OwnOperator;
import com.example.vkr.ship.repository.OwnOperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository("ownOperatorRepository")
//@Transactional(propagation = Propagation.REQUIRES_NEW)
public class OwnOperatorRepositoryImpl extends BaseRepositoryImpl<OwnOperator, String> implements OwnOperatorRepository {

    @Autowired
    public OwnOperatorRepositoryImpl(EntityManager em) {
        super(JpaEntityInformationSupport.getEntityInformation(OwnOperator.class, em));
//        super(OwnOperator.class, em);
    }
}
