package org.example.common.dto;

public record ErrorResponse(
        String code,
        String message
) {
}