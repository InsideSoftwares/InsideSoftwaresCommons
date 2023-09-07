package br.com.insidesoftwares.audit.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class InsideAuditLogDTO {

    private String user;
    private String method;
    private String description;
    private LocalDateTime startDateChange;
    private LocalDateTime endDateChange;
    private boolean success;
    private String messageError;
    private List<Object> parameter;
    private Object response;

}
