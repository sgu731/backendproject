package org.example.audit.controller;

import org.example.audit.dto.AuditLogResponse;
import org.example.audit.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
@Tag(name = "Audit Log")
public class AuditLogController {

    private final AuditLogService service;

    @Operation(summary = "Get audit logs")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AuditLogResponse> getLogs() {

        return service.getLogs();

    }

}