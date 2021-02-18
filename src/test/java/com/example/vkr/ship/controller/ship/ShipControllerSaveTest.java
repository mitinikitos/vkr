package com.example.vkr.ship.controller.ship;


import com.example.vkr.ship.model.Ship;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShipControllerSaveTest extends ShipControllerTest {

    @Test
    @WithMockUser("nikita")
    void saveShipSuccess() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/ship/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(ship)))
                .andDo(print())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);

        String content = mvcResult.getResponse().getContentAsString();
        Ship savedShip = mapFromJson(content, Ship.class);
        assertEquals(ship.getName(), savedShip.getName());
    }

    @Test
    @WithMockUser("nikita")
    void saveShipFailedExists() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/ship/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(ship2)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
    }

    @Test
    void saveShipFailedUnauthorized() throws Exception {

        mvc.perform(post("/ship/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(ship)))
                .andDo(print());
    }
}
