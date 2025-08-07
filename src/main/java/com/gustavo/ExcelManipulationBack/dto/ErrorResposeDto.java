package com.gustavo.ExcelManipulationBack.dto;

public class ErrorResposeDto extends RuntimeException {
    public ErrorResposeDto(String message) {
        super(message);
    }
}
