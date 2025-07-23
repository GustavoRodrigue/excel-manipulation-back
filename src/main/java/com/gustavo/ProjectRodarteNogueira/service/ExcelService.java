package com.gustavo.ProjectRodarteNogueira.service;

import com.gustavo.ProjectRodarteNogueira.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ExcelService {

    public List<Student> extract(MultipartFile file) {
        List<Student> studentList = new ArrayList<>();

        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++ ){
                Row row = sheet.getRow(rowIndex);

                if (row != null) {
                    Student student = new Student();

                    Cell idCell = row.getCell(0);
                    if(idCell != null) {
                        student.setId(Long.valueOf((long) idCell.getNumericCellValue()));
                    }

                    Cell nameCell = row.getCell(1);
                    if(nameCell != null) {
                        student.setName(nameCell.getStringCellValue());
                    }

                    Cell genderCell = row.getCell(2);
                    if(genderCell != null) {
                        student.setGender(genderCell.getStringCellValue());
                    }

                    Cell dateOfBirthCell = row.getCell(3);
                    if(dateOfBirthCell != null) {
//                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                        student.setDateOfBirth(dateOfBirthCell.getDateCellValue());
                    }

                    Cell quarterlyNote1Cell = row.getCell(4);
                    if(quarterlyNote1Cell != null) {
                        student.setQuarterlyNote1(quarterlyNote1Cell.getNumericCellValue());
                    }

                    Cell quarterlyNote2Cell = row.getCell(5);
                    if(quarterlyNote2Cell != null) {
                        student.setQuarterlyNote2(quarterlyNote2Cell.getNumericCellValue());
                    }

                    Cell quarterlyNote3Cell = row.getCell(6);
                    if(quarterlyNote3Cell != null) {
                        student.setQuarterlyNote3(quarterlyNote3Cell.getNumericCellValue());
                    }


                    studentList.add(student);
                }
            }

        } catch (Exception exception) {
            log.error("Error ao extrair excel: {}", exception.getMessage());
        }

        return studentList;

    }


}
