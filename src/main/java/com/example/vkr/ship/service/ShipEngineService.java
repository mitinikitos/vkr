package com.example.vkr.ship.service;

import com.example.vkr.ship.model.Engine;
import com.example.vkr.ship.model.Ship;

import java.util.List;

public interface ShipEngineService {
    void save(Ship ship, List<Engine> engines);
}
