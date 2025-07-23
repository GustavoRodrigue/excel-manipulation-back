package com.gustavo.ProjectRodarteNogueira.controller;

import com.gustavo.ProjectRodarteNogueira.model.Student;
import com.gustavo.ProjectRodarteNogueira.service.ExcelService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    private final ExcelService excelService;


    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Student>> extract(@RequestParam MultipartFile file) {
        return ResponseEntity.ok(excelService.extract(file));
    }

}
