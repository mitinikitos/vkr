package com.example.vkr.ship.service.ship;

import com.example.vkr.AbstractTest;
import com.example.vkr.ship.model.Ship;
import com.example.vkr.ship.repository.ShipRepository;
import com.example.vkr.ship.service.ShipService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>Before test saved ship2</p>
 * <p>After test delete all ship</p>
 */
public abstract class ShipServiceTest extends AbstractTest {

    protected Ship ship = new Ship(1, "name", 123, 2018);
    protected Ship ship2 = new Ship(2, "name", 123, 2018);

    @Autowired
    protected ShipRepository shipRepository;

    @Autowired
    protected ShipService shipService;

    @Override
    @BeforeEach
    protected void setUp() {
        resetDb();
//        shipService.save(ship2);
        shipRepository.save(ship2);
    }

    @Override
    @AfterEach
    protected void reset() {
        resetDb();
    }

    private void resetDb() {
        delete(ship);
        delete(ship2);
    }

    protected void delete(Ship ship) {
        try {
            shipRepository.deleteById(ship.getId());
        } catch (Exception ignored) {}
    }
}
