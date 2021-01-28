package com.example.vkr.ship.service;

import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.Ship;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

//TODO вынести в базовый интерфейс
public interface ShipService {
    Ship save(Ship ship) throws EntityExistsException;
    void saveAll(List<Ship> ships);
    Optional<Ship> findById(Integer id);
    List<Ship> findAll();
    void deleteById(int regNum) throws EntityNotFoundException;
    List<Ship> findAllWithSort(int startPage, int amount, String sort);
    Ship addShip(Ship ship);
    //Ship excelParse(Row row) throws NullPointerException;
}
