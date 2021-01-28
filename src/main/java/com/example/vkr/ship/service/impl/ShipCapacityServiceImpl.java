package com.example.vkr.ship.service.impl;

import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.ShipCapacity;
import com.example.vkr.ship.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipCapacityServiceImpl implements com.example.vkr.ship.service.ShipCapacityService {

    @Override
    public boolean save(ShipCapacity shipCapacity){
        return true;
    }
}
