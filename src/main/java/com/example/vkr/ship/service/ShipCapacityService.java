package com.example.vkr.ship.service;

import com.example.vkr.base.service.BaseService;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.Ship;
import com.example.vkr.ship.model.ShipCapacity;


public interface ShipCapacityService extends BaseService<ShipCapacity, Integer> {
    /**
     * Update entity {@link ShipCapacity}
     * @param shipCapacity must not be {@literal null}.
     * @exception EntityNotFoundException if {@link Ship} with id {@link ShipCapacity#getRegNum()} not exists.
     * @return updated entity
     */
    ShipCapacity update(ShipCapacity shipCapacity) throws EntityNotFoundException;
}
