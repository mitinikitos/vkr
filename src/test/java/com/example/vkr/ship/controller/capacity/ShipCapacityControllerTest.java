package com.example.vkr.ship.controller.capacity;

import com.example.vkr.ControllerTest;
import com.example.vkr.ship.model.Ship;
import com.example.vkr.ship.model.ShipCapacity;
import com.example.vkr.ship.service.ShipCapacityService;
import com.example.vkr.ship.service.ShipService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>Before test save Ship(1, "test", 123, 2019), ShipCapacity(1, 2, 3, 4, 5)</p>
 */
public abstract class ShipCapacityControllerTest extends ControllerTest {

    @Autowired
    protected ShipCapacityService shipCapacityService;
    @Autowired
    private ShipService shipService;

    protected Ship ship = new Ship(1, "test", 123, 2019);
    private ShipCapacity shipCapacity = new ShipCapacity(1, 2, 3, 4, 5);
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
        shipCapacity.setShip(ship);
        shipCapacity.setRegNum(ship.getId());
        ship.setShipCapacity(shipCapacity);
        shipService.save(ship);
    }

    @Override
    @AfterEach
    protected void reset() throws Exception {
        shipService.deleteById(ship.getId());
    }
}
