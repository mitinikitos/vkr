package com.example.vkr.ship.controller.ship;

import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.controller.ShipController;
import com.example.vkr.ship.model.Ship;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShipControllerGetByIdTest extends ShipControllerTest {

    @Test
    void getByIdSuccess() throws Exception {

        mvc.perform(get("/ship/{id}", ship2.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ship2.getId()))
                .andExpect(jsonPath("$.name").value(ship2.getName()));
    }

    @Test
    void getByIdFailureTypeId() throws Exception {

        mvc.perform(get("/ship/{id}", "qwerty"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException));

    }

    @Test
    void getByIdFailureNotFound() throws Exception {

        mvc.perform(get("/ship/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));

    }

    @Test
    void getCountPageTest() throws Exception {

        mvc.perform(get("/ship/count?size=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> Assert.assertEquals("1", result.getResponse().getContentAsString()));
    }
}