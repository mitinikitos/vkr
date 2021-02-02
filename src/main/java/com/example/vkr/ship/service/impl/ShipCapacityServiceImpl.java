package com.example.vkr.ship.service.impl;

import com.example.vkr.base.repository.BaseRepository;
import com.example.vkr.base.service.impl.BaseServiceImpl;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.ShipCapacity;
import com.example.vkr.ship.repository.ShipCapacityRepository;
import com.example.vkr.ship.service.ShipCapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ShipCapacityServiceImpl extends BaseServiceImpl<ShipCapacity, Integer> implements ShipCapacityService {

    /**
     * Creates {@link ShipCapacityServiceImpl} for the given {@link BaseRepository}
     * @param baseRepository must not be {@literal null}.
     */
    public ShipCapacityServiceImpl(BaseRepository<ShipCapacity, Integer> baseRepository) {
        super(baseRepository);
    }

    @Override
    public ShipCapacity update(ShipCapacity shipCapacity) throws EntityNotFoundException {
        try {
            return baseRepository.save(shipCapacity);
        } catch (DataIntegrityViolationException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }
}
