package br.com.insidesoftwares.audit.domain.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record InsideAuditLogDTO(
        String user,
        String method,
        String description,
        LocalDateTime startDateChange,
        LocalDateTime endDateChange,
        boolean success,
        String messageError,
        List<Object> parameter,
        Object response
) {}
