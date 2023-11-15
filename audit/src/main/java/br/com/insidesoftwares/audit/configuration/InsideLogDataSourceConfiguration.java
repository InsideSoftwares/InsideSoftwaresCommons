package br.com.insidesoftwares.audit.configuration;

import br.com.insidesoftwares.audit.configuration.properties.InsideAuditLogProperties;
import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@ConditionalOnProperty(prefix="insidesoftwares.audit", name = "enable", havingValue = "true")
@AutoConfiguration
@EnableJpaRepositories(
        basePackages = "br.com.insidesoftwares.audit.domain.repository",
        entityManagerFactoryRef = "InsideAuditLogEntityManager",
        transactionManagerRef = "InsideAuditLogTransactionManager"
)
@EnableTransactionManagement
@RequiredArgsConstructor
public class InsideLogDataSourceConfiguration {

    private final InsideAuditLogProperties insideAuditLogProperties;

    @Bean(name="InsideAuditLogDataSourceProps")
    @ConfigurationProperties("spring.datasource.audit")
    public DataSourceProperties insideAuditLogDataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean(name="InsideAuditLogDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.audit")
    public DataSource insideAuditLogDataSource(@Qualifier("InsideAuditLogDataSourceProps") DataSourceProperties properties){
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name="InsideAuditLogEntityManager")
    public LocalContainerEntityManagerFactoryBean insideAuditLogEntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("InsideAuditLogDataSource") DataSource dataSource
    ){
        return builder.dataSource(dataSource)
                .packages("br.com.insidesoftwares.audit.domain.entity")
                .persistenceUnit("InsideAuditLog")
                .build();
    }

    @Bean(name = "InsideAuditLogTransactionManager")
    @ConfigurationProperties("spring.jpa")
    public PlatformTransactionManager insideAuditLogTransactionManager(
            @Qualifier("InsideAuditLogEntityManager") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean("InsideAuditLogLiquibase")
    public SpringLiquibase insideAuditLogLiquibase(@Qualifier("InsideAuditLogDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setBeanName("InsideAuditLogLiquibase");
        liquibase.setChangeLog("classpath:db/changelog/db_audit.changelog-master.yaml");
        liquibase.setDatabaseChangeLogLockTable("INS_DATABASECHANGELOGLOCK");
        liquibase.setDatabaseChangeLogTable("INS_DATABASECHANGELOG");
        liquibase.setDatabaseChangeLogTable("INS_DATABASECHANGELOG");
        liquibase.setShouldRun(insideAuditLogProperties.isEnableLiquibase());
        return liquibase;
    }
}
