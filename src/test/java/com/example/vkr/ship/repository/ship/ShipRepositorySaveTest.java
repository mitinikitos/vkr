package com.example.vkr.ship.repository.ship;

import com.example.vkr.ship.model.Ship;
import com.example.vkr.ship.model.ShipCapacity;
import com.example.vkr.ship.model.ShipDimensions;
import com.example.vkr.ship.model.ShipEngine;
import com.example.vkr.ship.repository.ship.ShipRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

public class ShipRepositorySaveTest extends ShipRepositoryTest {

    /**
     *
     */
    @Test
    @Transactional
    void saveSuccess() {

        saveShip(ship);

        Optional<Ship> savedShip = shipRepository.findById(ship.getId());

        assertNotEquals(Optional.empty(), savedShip);
        assertNotNull(savedShip);
        assertEquals(ship.getName(), savedShip.get().getName());
        System.out.println(savedShip.get().toString());

    }

    /**
     *
     */
//    @Test
//    void saveFailure() {
//
//        assertThrows(DataIntegrityViolationException.class, () -> shipRepository.save(ship2));
//
//    }
}