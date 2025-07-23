package com.gustavo.ProjectRodarteNogueira.repository;

import com.gustavo.ProjectRodarteNogueira.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
