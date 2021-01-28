package com.example.vkr.ship.service.impl;

import com.example.vkr.base.repository.BaseRepository;
import com.example.vkr.base.service.impl.BaseServiceImpl;
import com.example.vkr.ship.model.*;
import com.example.vkr.ship.repository.ShipRepository;
import com.example.vkr.ship.service.ShipService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("shipService")
public class ShipServiceImpl extends BaseServiceImpl<Ship, Integer> implements ShipService {

    @Resource(name = "shipRepository")
    ShipRepository shipRepository;

    public ShipServiceImpl(BaseRepository<Ship, Integer> baseRepository) {
        super(baseRepository);
    }

    @Override
    public List<Ship> findAllWithSort(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sort));
        return findAll(pageable);
    }
}