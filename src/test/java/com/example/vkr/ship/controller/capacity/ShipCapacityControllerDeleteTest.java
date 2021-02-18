package com.example.vkr.ship.controller.capacity;

import com.example.vkr.ship.model.ShipCapacity;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShipCapacityControllerDeleteTest extends ShipCapacityControllerTest {

    @Test
    @WithMockUser
    void deleteSuccess() throws Exception {

        mvc.perform(delete("/shipCapacity/{id}", ship.getId()))
                .andDo(print())
                .andExpect(status().isOk());

        ShipCapacity deletedCapacity = shipCapacityService.findById(ship.getId());
        assertNull(deletedCapacity.getDedv());
        assertNull(deletedCapacity.getGt());
    }

    @Test
    @WithMockUser
    void deleteFailedNotExists() throws Exception {

        mvc.perform(delete("/shipCapacity/{id}", 2))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteFailedUnauthorized() throws Exception {

        mvc.perform(delete("/shipCapacity/{id}", anyInt()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
