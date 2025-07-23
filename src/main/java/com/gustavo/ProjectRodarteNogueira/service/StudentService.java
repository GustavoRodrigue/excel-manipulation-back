package com.gustavo.ProjectRodarteNogueira.service;

import com.gustavo.ProjectRodarteNogueira.model.Student;
import com.gustavo.ProjectRodarteNogueira.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FileService fileService;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> searchAll() {
        return studentRepository.findAll();
    }

    public List<Student> saveList(MultipartFile file) {
        return studentRepository.saveAll(fileService.mapStudents(file));
    }
}
