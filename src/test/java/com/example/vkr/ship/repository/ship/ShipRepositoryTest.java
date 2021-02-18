package com.example.vkr.ship.repository.ship;

import com.example.vkr.ship.model.Ship;
import com.example.vkr.ship.model.ShipCapacity;
import com.example.vkr.ship.model.ShipDimensions;
import com.example.vkr.ship.model.ShipEngine;
import com.example.vkr.ship.repository.RepositoryTest;
import com.example.vkr.ship.repository.ShipRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * <p>Before test save ship2 to db.</p>
 * <p>After test delete all ship in db.</p>
 */
@Transactional
public abstract class ShipRepositoryTest extends RepositoryTest {

    @Autowired
    protected ShipRepository shipRepository;

    protected Ship ship = new Ship(1, "name", 123, 2019);
    protected Ship ship2 = new Ship(2, "name", 123, 2019);

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        ship.setShipEngine(new ShipEngine(ship));
        ship.setShipDimensions(new ShipDimensions(ship));
        ship.setShipCapacity(new ShipCapacity(ship));
        ship2.setShipCapacity(new ShipCapacity(ship2));
        ship2.setShipDimensions(new ShipDimensions(ship2));
        ship2.setShipEngine(new ShipEngine(ship2));
        resetDb();
        try {
            saveShip(ship2);
//            shipRepository.save(ship2);
        } catch (Exception ignored) {}
    }

    @Override
    @AfterEach
    protected void reset() throws Exception {
        resetDb();
    }

    @Transactional
    protected void saveShip(Ship ship) {
        em.persist(ship);
        em.flush();
    }

    @Transactional
    protected void deleteShip(Ship ship) {
        em.remove(em.merge(ship));
    }

    private void resetDb() {
        try {
//            deleteShip(ship);
            shipRepository.delete(ship);
        } catch (Exception ignored) {}
        try {
            shipRepository.delete(ship2);
//            deleteShip(ship2);
        } catch (Exception ignored) {}
    }
}
