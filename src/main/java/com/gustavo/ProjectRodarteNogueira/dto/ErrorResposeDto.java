package com.gustavo.ProjectRodarteNogueira.dto;

public class ErrorResposeDto extends RuntimeException {
    public ErrorResposeDto(String message) {
        super(message);
    }
}
