package com.example.vkr.ship.service;

import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.ShipCapacity;
import org.apache.poi.ss.usermodel.Row;

public interface ShipCapacityService {
    boolean save(ShipCapacity shipCapacity);
}
