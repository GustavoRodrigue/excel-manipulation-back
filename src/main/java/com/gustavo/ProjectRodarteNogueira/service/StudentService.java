package com.gustavo.ProjectRodarteNogueira.service;

import com.gustavo.ProjectRodarteNogueira.dto.ErrorDto;
import com.gustavo.ProjectRodarteNogueira.dto.StudentDTO;
import com.gustavo.ProjectRodarteNogueira.model.Student;
import com.gustavo.ProjectRodarteNogueira.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public List<StudentDTO> findAllSortAge() {
        return StudentDTO.map(this.studentRepository.findAll(
            Sort.by(Sort.Direction.DESC, "dateOfBirth")
        ));
    }

    public List<Student> saveList(MultipartFile file) {
        return this.studentRepository.saveAll(this.fileService.mapStudents(file));
    }

    public byte[] generateReport() throws IOException {
        return this.fileService.exportStudents(this.findAllSortAge());
    }


}
