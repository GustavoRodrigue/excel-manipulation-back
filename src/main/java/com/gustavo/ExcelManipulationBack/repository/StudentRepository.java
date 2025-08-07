package com.gustavo.ExcelManipulationBack.repository;

import com.gustavo.ExcelManipulationBack.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
