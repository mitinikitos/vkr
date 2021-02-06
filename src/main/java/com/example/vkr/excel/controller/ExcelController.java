package com.example.vkr.excel.controller;

import com.example.vkr.excel.service.ExcelService;
import com.example.vkr.ship.model.Ship;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    ExcelService excelService;

    /**
     * Parse given {@link File} to db
     * @param file must not be {@literal null}. {@link MediaType#MULTIPART_FORM_DATA}
     * @return if parsed success: {@link String} errors during parser
     * Else: {@link String server error}
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> parseExcel(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                InputStream inputStream = file.getInputStream();
                String filename = "/home/nikita/IdeaProjects/excel/saveFile.xlsx";
                File saveFile = new File(filename);
                OutputStream outputStream = new FileOutputStream(saveFile);


                byte[] bytes = new byte[inputStream.available()];

                inputStream.read(bytes);
                outputStream.write(bytes);
                inputStream.close();
                outputStream.close();
                final long start = LocalTime.now().toNanoOfDay();
                String resError = excelService.parser(saveFile);
                final long end = LocalTime.now().minusNanos(start).toNanoOfDay();
                System.out.println(end);
                return ResponseEntity.status(201).body(resError);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>("server errors", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else
            return ResponseEntity.badRequest().body("");
    }

    /**
     * Download {@link Workbook} for the given {@link List} {@link Ship} id
     * @param idsShip must not be {@literal null}. List Ship id
     * @param type can be {@literal null}. Format saved file, {@link String xslx, xslx}.
     * DefaultValue {@link String xslx}.
     * @return {@link ByteArrayResource}
     */
    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadExcel(
            @RequestBody List<Integer> idsShip,
            @RequestParam(value = "type", required = false, defaultValue = "xslx") String type) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Workbook workbook = excelService.downloadExcel(idsShip);

            HttpHeaders header = new HttpHeaders();
            String fileName = "SaveFile." + type;
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", fileName));

            workbook.write(byteArrayOutputStream);
            workbook.close();
            return new ResponseEntity<>(new ByteArrayResource(byteArrayOutputStream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
