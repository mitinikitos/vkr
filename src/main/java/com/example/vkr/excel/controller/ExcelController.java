package com.example.vkr.excel.controller;

import com.example.vkr.excel.service.ExcelService;
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

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    ExcelService excelService;

    @GetMapping("/save")
    public ResponseEntity<?> parseExcel() {
        String fileName = "/home/nikita/Documents/ВКР/бд.xls";
        File file = new File(fileName);

        final long start = LocalTime.now().toNanoOfDay();
        String resError = excelService.parse(file);
        final long end = LocalTime.now().minusNanos(start).toNanoOfDay();
        System.out.println(end);
        return ResponseEntity.status(201).body(resError);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @PostMapping("/save")
    public ResponseEntity<String> parseExcel(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                InputStream inputStream = file.getInputStream();
                String filename = "/home/nikita/IdeaProjects/excel/saveFile.xlsx";
                File saveFile = new File(filename);
                OutputStream outputStream = new FileOutputStream(saveFile);


                byte[] bytes = new byte[inputStream.available()];

                file = null;
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
                return new ResponseEntity("server errors", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else
            return ResponseEntity.badRequest().body("");
    }

//    @GetMapping("/download")
//    public ResponseEntity<ByteArrayResource> downloadExcel() {
//        try {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            Workbook workbook = excelService.downloadExcel();
//
//            HttpHeaders header = new HttpHeaders();
//            header.setContentType(new MediaType("application", "force-download"));
//            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ProductTemplate.xlsx");
//
////            response.setContentType("application/force-download");
////            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=simple.xlsx");
//            workbook.write(byteArrayOutputStream);
//            workbook.close();
//            return new ResponseEntity<>(new ByteArrayResource(byteArrayOutputStream.toByteArray()),
//                    header, HttpStatus.CREATED);
////            response.flushBuffer();
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
