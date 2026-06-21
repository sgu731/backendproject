package org.example.audit.dto;

import java.time.LocalDateTime;

public record AuditLogResponse(

        Long id,

        String actor,

        String action,

        String beforeValue,

        String afterValue,

        LocalDateTime createdAt

) {
}