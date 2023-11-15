package br.com.insidesoftwares.audit.configuration.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;

@AutoConfiguration
@Data
public class InsideAuditLogProperties {

    @Value("${insidesoftwares.audit.system:INS_AUDIT}")
    private String system;

    @Value("${insidesoftwares.audit.enable:false}")
    private boolean enable;

    @Value("${insidesoftwares.audit.liquibase.enable:true}")
    private boolean enableLiquibase;
}
