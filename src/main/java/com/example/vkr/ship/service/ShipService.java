package com.example.vkr.ship.service;

import com.example.vkr.base.service.BaseService;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.Ship;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

//TODO вынести в базовый интерфейс
public interface ShipService extends BaseService<Ship, Integer> {
    List<Ship> findAllWithSort(int startPage, int amount, String sort);
}
