package com.gustavo.ExcelManipulationBack.controller;

import com.gustavo.ExcelManipulationBack.dto.ErrorResposeDto;
import com.gustavo.ExcelManipulationBack.dto.StudentDTO;
import com.gustavo.ExcelManipulationBack.model.Student;
import com.gustavo.ExcelManipulationBack.service.StudentService;
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
    public ResponseEntity<String> saveList(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo não enviado ou vazio.");
        }
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".xlsx")) {
            return ResponseEntity.badRequest().body("Somente arquivos '.xlsx' são aceitos.");
        }
        try {
            List<Student> students = studentService.saveList(file);
            return ResponseEntity.ok().body("Arquivo enviado com sucesso!");
        } catch (Exception e) {
            throw new ErrorResposeDto(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> searchAll(){
        return ResponseEntity.ok(this.studentService.findAllSortAge());
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> generateReport() throws IOException {
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=alunos.xlsx")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(this.studentService.generateReport());
    }

}
