package com.example.vkr.ship.service;

import com.example.vkr.ship.model.OwnOperator;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public interface OwnOperatorService {
    void save(OwnOperator ownOperator) throws Exception;
    List<OwnOperator> saveAll(List<OwnOperator> ownOperators);
}
