package com.example.vkr.ship.controller.ship;

import com.example.vkr.ControllerTest;
import com.example.vkr.ship.model.Ship;
import com.example.vkr.ship.service.ShipService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>Before test save Ship(1, "name", 123, 2020)</p>
 */
public abstract class ShipControllerTest extends ControllerTest {

    @Autowired
    ShipService shipService;

    protected final Ship ship = new Ship(1, "name1", 123, 2020);
    protected final Ship ship2 = new Ship(2, "name", 123, 2018);

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
        deleteShip(ship);
        deleteShip(ship2);
        shipService.save(ship2);
    }

    @Override
    @AfterEach
    protected void reset() throws Exception {
        deleteShip(ship);
        deleteShip(ship2);
    }

    protected void deleteShip(Ship ship) {
        try {
            shipService.deleteById(ship.getId());
        } catch (Exception ignored) {}
    }
}
