package br.com.insidesoftwares.audit.service;

import br.com.insidesoftwares.audit.domain.entity.InsideAuditLog;
import br.com.insidesoftwares.audit.domain.repository.InsideAuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InsideAuditLogService {

    private final InsideAuditLogRepository insideAuditLogRepository;

    @Transactional("InsideAuditLogTransactionManager")
    public void saveLog(final InsideAuditLog insideAuditLog) {
        insideAuditLogRepository.saveAndFlush(insideAuditLog);
    }


}
