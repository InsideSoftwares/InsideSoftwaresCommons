package br.com.insidesoftwares.audit.domain.repository;

import br.com.insidesoftwares.audit.domain.entity.InsideAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InsideAuditLogRepository extends JpaRepository<InsideAuditLog, UUID> {
}
