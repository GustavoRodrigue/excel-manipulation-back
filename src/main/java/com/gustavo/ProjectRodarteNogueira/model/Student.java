package com.gustavo.ProjectRodarteNogueira.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Student {
    @Id
    private Long id;
    private String name;
    private String gender;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "quarterly_note_1")
    private Double quarterlyNote1;
    @Column(name = "quarterly_note_2")
    private Double quarterlyNote2;
    @Column(name = "quarterly_note_3")
    private Double quarterlyNote3;
}
