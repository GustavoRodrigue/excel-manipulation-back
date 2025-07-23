package com.gustavo.ProjectRodarteNogueira.service;

import com.gustavo.ProjectRodarteNogueira.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FileService {
    public List<Student> mapStudents(MultipartFile file) {
        List<Student> students = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Student student = parseStudentFromRow(row);
                    students.add(student);
                }
            }
        } catch (IOException e) {
            log.error("Erro ao ler o arquivo Excel: {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("Erro inesperado ao processar o Excel: {}", e.getMessage(), e);
        }

        return students;
    }

    private Student parseStudentFromRow(Row row) {
        return Student.builder()
            .id(getLongValue(row.getCell(0)))
            .name(getStringValue(row.getCell(1)))
            .gender(getCharValue(row.getCell(2)))
            .dateOfBirth(getLocalDateValue(row.getCell(3)))
            .quarterlyNote1(getDoubleValue(row.getCell(4)))
            .quarterlyNote2(getDoubleValue(row.getCell(5)))
            .quarterlyNote3(getDoubleValue(row.getCell(6)))
        .build();
    }

    private Long getLongValue(Cell cell) {
        return (cell != null && cell.getCellType() == CellType.NUMERIC)
                ? (long) cell.getNumericCellValue()
                : null;
    }

    private String getStringValue(Cell cell) {
        return (cell != null && cell.getCellType() == CellType.STRING)
                ? cell.getStringCellValue()
                : null;
    }

    private Character getCharValue(Cell cell) {
        String value = getStringValue(cell);
        return (value != null && !value.isEmpty()) ? value.charAt(0) : null;
    }

    private Double getDoubleValue(Cell cell) {
        return (cell != null && cell.getCellType() == CellType.NUMERIC)
                ? cell.getNumericCellValue()
                : null;
    }

    private LocalDate getLocalDateValue(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC) return null;

        Date date = cell.getDateCellValue();
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
