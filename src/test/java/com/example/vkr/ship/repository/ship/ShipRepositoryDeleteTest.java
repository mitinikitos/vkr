package com.example.vkr.ship.repository.ship;

import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.Ship;
import com.example.vkr.ship.repository.ship.ShipRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

public class ShipRepositoryDeleteTest extends ShipRepositoryTest {

    @Test
    @Transactional
    void deleteSuccess() {

        Optional<Ship> deleteShip = shipRepository.findById(ship2.getId());
        assertNotEquals(Optional.empty(), deleteShip);

        try {
            shipRepository.deleteById(ship2.getId());
        } catch (EntityNotFoundException ignored) {}

        Optional<Ship> optionalShip = shipRepository.findById(ship2.getId());
        assertEquals(Optional.empty(), optionalShip);
    }

    /**
     *
     */
    @Test
    void deleteFailureNotExists() {
        assertThrows(EntityNotFoundException.class, () -> shipRepository.deleteById(ship.getId()));
    }
}
