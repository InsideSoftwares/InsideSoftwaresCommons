package br.com.insidesoftwares.audit.service;

import br.com.insidesoftwares.audit.configuration.properties.InsideAuditLogProperties;
import br.com.insidesoftwares.audit.domain.dto.InsideAuditLogDTO;
import br.com.insidesoftwares.audit.domain.entity.InsideAuditLog;
import br.com.insidesoftwares.audit.domain.mapper.InsideAuditLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(prefix="insidesoftwares.audit", name = "enable", havingValue = "true")
@Service
@RequiredArgsConstructor
@Slf4j
public class InsideAuditService {

    private final InsideAuditLogService insideAuditLogService;
    private final InsideAuditLogMapper insideAuditLogMapper;
    private final InsideAuditLogProperties insideAuditLogProperties;

    @Async
    public void saveAuditLog(final InsideAuditLogDTO insideAuditLogDTO) {
        log.info("Init save Log Audit: {}", insideAuditLogDTO.method());

        InsideAuditLog insideAuditLog = insideAuditLogMapper.toEntity(insideAuditLogDTO, insideAuditLogProperties.getSystem());
        insideAuditLogService.saveLog(insideAuditLog);

        log.info("End save Log Audit: {}", insideAuditLogDTO.method());
    }

}
