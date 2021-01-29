package com.example.vkr.ship.service.impl;

import com.example.vkr.base.repository.BaseRepository;
import com.example.vkr.base.service.impl.BaseServiceImpl;
import com.example.vkr.exception.EntityExistsException;
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

    /**
     * Creates {@link ShipServiceImpl} for the given {@link BaseRepository}
     * @param baseRepository must not be {@literal null}.
     */
    public ShipServiceImpl(BaseRepository<Ship, Integer> baseRepository) {
        super(baseRepository);
    }

    @Override
    public <S extends Ship> S save(S ship) throws EntityExistsException {

        if (ship.getShipEngine() == null) {
            ShipEngine shipEngine = new ShipEngine(ship);
            ship.setShipEngine(shipEngine);
        }
        if (ship.getShipDimensions() == null) {
            ShipDimensions dimensions = new ShipDimensions(ship);
            ship.setShipDimensions(dimensions);
        }
        if (ship.getShipCapacity() == null) {
            ShipCapacity capacity = new ShipCapacity(ship);
            ship.setShipCapacity(capacity);
        }
        return super.save(ship);
    }

    //TODO добавить отдельный метод в repository
    @Override
    public List<Ship> findAllWithSort(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sort));
        return findAll(pageable);
    }
}