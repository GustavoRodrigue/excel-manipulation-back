package com.gustavo.ProjectRodarteNogueira.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Builder
public class Student {
    @Id
    private Long id;
    private String name;
    private Character gender;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "quarterly_note_1")
    private Double quarterlyNote1;
    @Column(name = "quarterly_note_2")
    private Double quarterlyNote2;
    @Column(name = "quarterly_note_3")
    private Double quarterlyNote3;
}
