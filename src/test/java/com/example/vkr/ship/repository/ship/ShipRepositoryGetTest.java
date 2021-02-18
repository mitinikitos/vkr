package com.example.vkr.ship.repository.ship;

import com.example.vkr.ship.model.Ship;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ShipRepositoryGetTest extends ShipRepositoryTest {

    private final Ship ship3 = new Ship(3, "name", 123, 2018);

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
        saveShip(ship);
    }

    @Test
    void getByIdSuccess() {

        Optional<Ship> optionalShip = shipRepository.findById(ship2.getId());

        assertNotEquals(Optional.empty(), optionalShip);
        Ship getShip = optionalShip.get();
        assertEquals(ship2.getName(), getShip.getName());

    }

    @Test
    void getByIdFailureNotExists() {

        Optional<Ship> optionalShip = shipRepository.findById(ship3.getId());

        assertEquals(Optional.empty(), optionalShip);
    }
    /**
     *
     */
    @Test
    void getByIdFailureNullId() {

        assertThrows(InvalidDataAccessApiUsageException.class, () -> shipRepository.findById(null));
    }

    @Test
    void getAllSuccess() {

        List<Ship> getShips = shipRepository.findAll();

        assertNotNull(getShips);
        assertEquals(2, getShips.size());

    }
}
