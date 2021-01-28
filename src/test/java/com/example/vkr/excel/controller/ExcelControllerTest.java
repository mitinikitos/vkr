package com.example.vkr.excel.controller;

import com.example.vkr.excel.service.ExcelService;
import com.example.vkr.ship.service.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest//(classes = {VkrApplication.class, TestConfig.class})
//@ActiveProfiles("test")
class ExcelControllerTest {

    @Autowired
    private ExcelController excelController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExcelService excelService;
    @MockBean
    ShipService shipService;
    @MockBean
    EngineService engineService;
    @MockBean
    ShipEngineService shipEngineService;
    @MockBean
    OwnOperatorService ownOperatorService;
    @MockBean
    ShipCapacityService shipCapacityService;
    @MockBean
    ShipDimensionsService shipDimensionsService;

    @Test
    void parseExcel() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.xlsx",
                "multipart/form-data",
                new FileInputStream("/home/nikita/IdeaProjects/vkr2/src/test/resources/excel/test.xlsx")
        );

        MockHttpServletRequestBuilder multipart = multipart("/excel/save").file(file);

        mockMvc.perform(multipart)
                .andDo(print());
    }

    @Test
    void downloadExcel() {
    }
}