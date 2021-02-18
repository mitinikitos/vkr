package com.example.vkr.ship.controller.capacity;

import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.ShipCapacity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.thymeleaf.spring5.expression.Mvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShipCapacityControllerPutTest extends ShipCapacityControllerTest {

    @Test
    @WithMockUser("nikita")
    void putSuccess() throws Exception {
        ShipCapacity capacity = new ShipCapacity(12, 13, 14, 15, 16);
        capacity.setRegNum(ship.getId());
        MvcResult mvcResult = mvc.perform(put("/shipCapacity/put")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(capacity)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ShipCapacity updateCapacity = mapFromJson(content, ShipCapacity.class);
        ShipCapacity savedCapacity = shipCapacityService.findById(ship.getId());
    }

    @Test
    @WithMockUser("nikita")
    void putFailedNullId() throws Exception {
        ShipCapacity capacity = new ShipCapacity(12, 13, 14, 15, 16);
        mvc.perform(put("/shipCapacity/put")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(capacity)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertTrue(result.getResolvedException() instanceof BindingException);
                    assertEquals("Id must not be null", ((BindingException) result.getResolvedException()).getReason());
                });
    }

    @Test
    @WithMockUser
    void putFailedNotFound() throws Exception {
        ShipCapacity capacity = new ShipCapacity(12, 13, 14, 15, 16);
        capacity.setRegNum(2);
        mvc.perform(put("/shipCapacity/put")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(capacity)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }
}
