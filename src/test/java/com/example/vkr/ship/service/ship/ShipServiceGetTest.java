package com.example.vkr.ship.service.ship;

import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.Ship;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class ShipServiceGetTest extends ShipServiceTest {

    @Test
    void getShipSuccess() throws BindingException, EntityNotFoundException {

        Ship getShip = shipService.findById(ship2.getId());

        assertEquals(ship2.getName(), getShip.getName());
        assertNotNull(getShip.getShipCapacity());
        assertNotNull(getShip.getShipDimensions());
        assertNotNull(getShip.getShipEngine());
        assertEquals(ship2.getId(), getShip.getShipCapacity().getRegNum());
    }

    @Test
    void getShipFailureNotExists() {
        assertThrows(EntityNotFoundException.class, () -> shipService.findById(ship.getId()));
    }

    @Test
    void getShipFailureNullId() {
        assertThrows(BindingException.class, () -> shipService.findById(null));
    }
}
