package com.example.vkr.ship.service.impl;

import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.*;
import com.example.vkr.ship.repository.ShipRepository;
import com.example.vkr.ship.service.ShipService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
//import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//TODO edit save, saveAll
@Service("shipService")
public class ShipServiceImpl implements ShipService {

    @Resource(name = "shipRepository")
    ShipRepository shipRepository;


    @Override
    public Ship save(Ship ship) throws EntityExistsException {
        try {
            return shipRepository.save(ship);
        } catch (DataIntegrityViolationException e) {
            throw new EntityExistsException(e.getMessage());
        }
    }

    @Override
    public Ship addShip(Ship ship) {
        try {
            shipRepository.save(ship);
            return ship;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void saveAll(List<Ship> ships) {
    }

    @Override
    public Optional<Ship> findById(Integer id) throws NullPointerException {
        return shipRepository.findById(id);
    }

    @Override
    public List<Ship> findAll() {
        return null;
    }

    @Override
    public List<Ship> findAllWithSort(int startPage, int amount, String sort) {
        return null;
    }

    @Override
    public void deleteById(int regNum) throws EntityNotFoundException {
    }
}
