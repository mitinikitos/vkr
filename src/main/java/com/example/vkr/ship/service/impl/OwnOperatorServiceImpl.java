package com.example.vkr.ship.service.impl;

import com.example.vkr.ship.model.OwnOperator;
import com.example.vkr.ship.service.OwnOperatorService;
import com.example.vkr.ship.repository.OwnOperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OwnOperatorServiceImpl implements OwnOperatorService {

    @Autowired
    OwnOperatorRepository ownOperatorRepository;

    @Override
    public void save(OwnOperator ownOperator) throws Exception {
        ownOperatorRepository.save(ownOperator);
    }

    @Override
    public List<OwnOperator> saveAll(List<OwnOperator> ownOperators) {

        for (OwnOperator ownOperator : ownOperators) {
            try {
                ownOperatorRepository.save(ownOperator);
            } catch (Exception ignored) {}
        }
        return null;
    }
}