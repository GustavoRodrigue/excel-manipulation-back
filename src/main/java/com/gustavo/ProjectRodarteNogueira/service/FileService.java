package com.gustavo.ProjectRodarteNogueira.service;

import com.gustavo.ProjectRodarteNogueira.dto.StudentDTO;
import com.gustavo.ProjectRodarteNogueira.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
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

    public byte[] exportStudents(List<StudentDTO> students) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Alunos");

            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle centerStyle = createCenterAlignedStyle(workbook);

            createHeaderRow(sheet, headerStyle);

            int rowIndex = 2;
            for (StudentDTO student : students) {
                Row row = sheet.createRow(rowIndex++);
                createStudentRow(row, student, centerStyle);
            }

            autoSizeColumns(sheet, 5);

            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle createCenterAlignedStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private void createHeaderRow(Sheet sheet, CellStyle style) {
        sheet.createRow(0);
        Row header = sheet.createRow(1);

        String[] titles = {"Identificação", "Nome", "Idade", "Média das Notas"};

        for (int i = 0; i < titles.length; i++) {
            Cell cell = header.createCell(i + 1);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style);
        }
    }

    private void createStudentRow(Row row, StudentDTO student, CellStyle style) {
        Cell cellId = row.createCell(1);
        cellId.setCellValue(student.getId());
        cellId.setCellStyle(style);

        Cell cellName = row.createCell(2);
        cellName.setCellValue(student.getName());
        cellName.setCellStyle(style);

        Cell cellAge = row.createCell(3);
        cellAge.setCellValue(student.getAge());
        cellAge.setCellStyle(style);

        Cell cellAverage = row.createCell(4);
        cellAverage.setCellValue(student.getAverage());
        cellAverage.setCellStyle(style);
    }

    private void autoSizeColumns(Sheet sheet, int totalColumns) {
        for (int i = 1; i < totalColumns; i++) {
            sheet.autoSizeColumn(i);
        }
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
