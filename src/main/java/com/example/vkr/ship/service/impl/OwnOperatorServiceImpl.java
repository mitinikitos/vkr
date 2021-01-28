package com.example.vkr.ship.service.impl;

import com.example.vkr.base.repository.BaseRepository;
import com.example.vkr.base.service.impl.BaseServiceImpl;
import com.example.vkr.ship.model.OwnOperator;
import com.example.vkr.ship.service.OwnOperatorService;
import com.example.vkr.ship.repository.OwnOperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("ownOperatorService")
public class OwnOperatorServiceImpl extends BaseServiceImpl<OwnOperator, String> implements OwnOperatorService {

    @Resource(name = "ownOperatorRepository")
    OwnOperatorRepository ownOperatorRepository;


    public OwnOperatorServiceImpl(BaseRepository<OwnOperator, String> baseRepository) {
        super(baseRepository);
    }
}