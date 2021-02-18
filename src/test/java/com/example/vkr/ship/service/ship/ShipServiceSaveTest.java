package com.example.vkr.ship.service.ship;

import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.ship.model.Ship;
import com.example.vkr.ship.service.ShipService;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Test {@link ShipService#save}
 */
public class ShipServiceSaveTest extends ShipServiceTest {


    /**
     * Success saved
     */
    @Test
    void saveShipSuccess() {
        try {
            Ship created = shipService.save(ship);

            assertEquals(ship.getName(), created.getName());
            assertEquals(ship.getId(), created.getId());
        } catch (Exception ignored) {}
    }

    /**
     * Failed save. Saved {@link Ship} already exists
     */
    @Test
    void saveShipFailed() {
        assertThrows(EntityExistsException.class, () -> {
            shipService.save(ship2);
        });
    }

    /**
     * Failed save. Saved {@link Ship} is {@literal null}
     */
    @Test
    void saveShipFailedNullEntity() {
        assertThrows(BindingException.class, () -> shipService.save(null));
    }
}