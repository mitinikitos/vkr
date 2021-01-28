package com.example.vkr.ship.service;

import com.example.vkr.ship.model.ShipDimensions;
import org.apache.poi.ss.usermodel.Row;

public interface ShipDimensionsService {
    void save(ShipDimensions shipDimensions);
}
