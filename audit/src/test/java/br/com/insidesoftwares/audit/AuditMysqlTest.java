package br.com.insidesoftwares.audit;

import br.com.insidesoftwares.audit.aspect.InsideAuditAspectImplementation;
import br.com.insidesoftwares.audit.service.InsideAuditExampleService;
import br.com.insidesoftwares.audit.service.InsideSoftwaresUserAuthenticationBean;
import br.com.insidesoftwares.audit.service.LocaleUtilsBean;
import br.com.insidesoftwares.commons.lgpd.DataMaskingService;
import br.com.insidesoftwares.commons.utils.filter.FormatBodyUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = {
        AuditTestApplication.class,
        InsideAuditExampleService.class,
        InsideAuditAspectImplementation.class,
        InsideSoftwaresUserAuthenticationBean.class,
        LocaleUtilsBean.class,
        FormatBodyUtil.class,
        DataMaskingService.class
})
@Testcontainers
@ActiveProfiles("mysql")
public class AuditMysqlTest extends InsideAuditAspectTest {

    @Container
    static MySQLContainer container = new MySQLContainer("mysql:latest");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.audit.url", container::getJdbcUrl);
        registry.add("spring.datasource.audit.username", container::getUsername);
        registry.add("spring.datasource.audit.password", container::getPassword);
    }
}
