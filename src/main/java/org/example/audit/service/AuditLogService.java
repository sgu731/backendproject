package org.example.audit.service;

import lombok.RequiredArgsConstructor;
import org.example.AuditAction;
import org.example.audit.AuditLogRepository;
import org.example.audit.dto.AuditLogResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository repository;

    public void log(
            String actor,
            AuditAction action,
            String beforeValue,
            String afterValue) {

        repository.save(
                actor,
                action.name(),
                beforeValue,
                afterValue);
    }

    public List<AuditLogResponse> getLogs() {

        return repository.findAll();

    }
}