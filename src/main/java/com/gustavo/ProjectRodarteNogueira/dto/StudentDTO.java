package com.gustavo.ProjectRodarteNogueira.dto;

import com.gustavo.ProjectRodarteNogueira.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private Integer age;
    private Double average;

    public static List<StudentDTO> map(List<Student> students){
        return students.stream().map(student -> {
            return StudentDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .age(StudentDTO.calcAge(student.getDateOfBirth()))
                .average(StudentDTO.calcAverage(
                    student.getQuarterlyNote1(),
                    student.getQuarterlyNote2(),
                    student.getQuarterlyNote3()
                ))
            .build();
        }).toList();
    }

    public static int calcAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public static double calcAverage(double quarterlyNote1, double quarterlyNote2, double quarterlyNote3) {
        BigDecimal average = BigDecimal.valueOf((quarterlyNote1 + quarterlyNote2 + quarterlyNote3)/3);
        return average.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
