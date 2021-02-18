package com.example.vkr.excel.controller;

import com.example.vkr.ControllerTest;
import com.example.vkr.ship.model.OwnOperator;
import com.example.vkr.ship.model.Ship;
import com.example.vkr.ship.service.OwnOperatorService;
import com.example.vkr.ship.service.ShipService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExcelControllerTest extends ControllerTest {

    private static final String TEST_FILE_NAME = "/home/nikita/Documents/ВКР/бд2.xlsx";
    @Autowired
    private ShipService shipService;
    @Autowired
    private OwnOperatorService ownOperatorService;

    @Override
    @AfterEach
    protected void reset() throws Exception {
        List<OwnOperator> ownOperators = ownOperatorService.findAll();
        for (OwnOperator ownOperator : ownOperators) {
            ownOperatorService.delete(ownOperator);
        }
    }

    @Test
    void saveToDB() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "file.xlsx", "multipart/form-data", new FileInputStream(TEST_FILE_NAME));

        mvc.perform(multipart("/excel/save")
                .file(file))
                .andDo(print())
                .andExpect(status().isCreated());

        List<Ship> res = shipService.findAll();
        assertEquals(3, res.size());
    }
}