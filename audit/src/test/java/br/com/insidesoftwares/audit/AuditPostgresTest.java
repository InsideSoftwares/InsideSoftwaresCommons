package br.com.insidesoftwares.audit;

import br.com.insidesoftwares.audit.aspect.InsideAuditAspectImplementation;
import br.com.insidesoftwares.audit.service.InsideAuditExampleService;
import br.com.insidesoftwares.audit.service.InsideSoftwaresUserAuthenticationBean;
import br.com.insidesoftwares.audit.service.LocaleUtilsBean;
import br.com.insidesoftwares.commons.lgpd.DataMaskingService;
import br.com.insidesoftwares.commons.utils.filter.SanitizationBodyUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = {
        AuditTestApplication.class,
        InsideAuditExampleService.class,
        InsideAuditAspectImplementation.class,
        InsideSoftwaresUserAuthenticationBean.class,
        LocaleUtilsBean.class,
        SanitizationBodyUtil.class,
        DataMaskingService.class
})
@Testcontainers
@ActiveProfiles("postgres")
public class AuditPostgresTest extends InsideAuditAspectTest {
    @Container
    static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest");

    @DynamicPropertySource
    private static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.audit.url", container::getJdbcUrl);
        registry.add("spring.datasource.audit.username", container::getUsername);
        registry.add("spring.datasource.audit.password", container::getPassword);
        registry.add("spring.datasource.audit.driver-class-name", container::getDriverClassName);
    }
}
