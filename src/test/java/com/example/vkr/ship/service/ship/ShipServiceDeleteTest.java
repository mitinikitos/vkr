package com.example.vkr.ship.service.ship;

import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.Ship;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class ShipServiceDeleteTest extends ShipServiceTest {

    @Test
    void deleteSuccess() throws BindingException, EntityNotFoundException {

        Ship deletedShip = shipService.findById(ship2.getId());

        assertNotNull(deletedShip);

        shipService.deleteById(ship2.getId());

        assertThrows(EntityNotFoundException.class, () -> shipService.findById(ship2.getId()));
    }

    @Test
    void deleteFailed() {
        
        assertThrows(EntityNotFoundException.class, () -> shipService.deleteById(ship.getId()));

    }
}
