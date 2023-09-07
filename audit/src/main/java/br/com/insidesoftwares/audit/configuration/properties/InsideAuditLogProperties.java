package br.com.insidesoftwares.audit.configuration.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(
        prefix="insidesoftwares.audit", name = "enable",
        havingValue = "true",
        matchIfMissing = true
)
@AutoConfiguration
@Data
public class InsideAuditLogProperties {

    @Value("${insidesoftwares.audit.system}")
    private String system;
}
