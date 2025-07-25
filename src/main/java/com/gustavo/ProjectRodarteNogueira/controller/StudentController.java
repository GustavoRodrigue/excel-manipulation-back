package com.gustavo.ProjectRodarteNogueira.controller;

import com.gustavo.ProjectRodarteNogueira.dto.StudentDTO;
import com.gustavo.ProjectRodarteNogueira.model.Student;
import com.gustavo.ProjectRodarteNogueira.service.StudentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Student>> saveList(@RequestParam MultipartFile file) {
        return ResponseEntity.ok(this.studentService.saveList(file));
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> searchAll(){
        return ResponseEntity.ok(this.studentService.findAllSortAge());
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> generateReport() throws IOException {
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_rodarte.xlsx")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(this.studentService.generateReport());
    }

}
