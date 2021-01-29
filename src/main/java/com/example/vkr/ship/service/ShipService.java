package com.example.vkr.ship.service;

import com.example.vkr.base.service.BaseService;
import com.example.vkr.ship.model.Ship;

import java.util.List;

public interface ShipService extends BaseService<Ship, Integer> {
    List<Ship> findAllWithSort(int page, int size, String sort);
}
