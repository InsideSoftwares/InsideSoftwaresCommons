package br.com.insidesoftwares.audit.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@AutoConfiguration
@EnableAspectJAutoProxy
@EnableAsync
public class InsideAuditConfiguration {
}
