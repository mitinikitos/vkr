package com.example.vkr.ship.controller.ship;

import com.example.vkr.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShipControllerDeleteTest extends ShipControllerTest {

    /**
     *
     */
    @Test
    @WithMockUser("nikita")
    void deleteShipSuccess() throws Exception {

        mvc.perform(delete("/ship/{id}", ship2.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Delete entity with id %s success", ship2.getId())));

    }

    /**
     *
     */
    @Test
    @WithMockUser("nikita")
    void deleteShipFailedNotExists() throws Exception {

        mvc.perform(delete("/ship/{id}", ship.getId()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));

    }

    /**
     *
     */
    @Test
    void deleteShipFailedUnauthorized() throws Exception {
        mvc.perform(delete("/ship/{id}", ship2.getId()))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }
}
