package br.com.insidesoftwares.audit;

import br.com.insidesoftwares.audit.aspect.InsideAuditAspectImplementation;
import br.com.insidesoftwares.audit.domain.entity.InsideAuditLog;
import br.com.insidesoftwares.audit.domain.repository.InsideAuditLogRepository;
import br.com.insidesoftwares.audit.service.InsideAuditExampleService;
import br.com.insidesoftwares.audit.service.InsideSoftwaresUserAuthenticationBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = { AuditTestApplication.class, InsideAuditExampleService.class, InsideAuditAspectImplementation.class, InsideSoftwaresUserAuthenticationBean.class })
@Testcontainers
@ActiveProfiles("mysql")
public class AuditMysqlTest {

    @Container
    static MySQLContainer container = new MySQLContainer("mysql:latest");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.audit.url", container::getJdbcUrl);
        registry.add("spring.datasource.audit.username", container::getUsername);
        registry.add("spring.datasource.audit.password", container::getPassword);
    }

    @Autowired
    private InsideAuditExampleService insideAuditExampleService;

    @Autowired
    private InsideAuditLogRepository insideAuditLogRepository;

    @BeforeEach
    public void before() {
        insideAuditLogRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Test", roles="ADMIN")
    public void checksIfTheLogWasSuccessfullySavedFromTheMultiplyMethod() throws InterruptedException {

        BigDecimal response = insideAuditExampleService.multiplication(BigDecimal.valueOf(20L), BigDecimal.valueOf(14));
        sleep(1000);
        assertEquals(BigDecimal.valueOf(280), response);

        List<InsideAuditLog> insideAuditLogs = insideAuditLogRepository.findAll();
        assertEquals(1, insideAuditLogs.size());
        InsideAuditLog insideAuditLog = insideAuditLogs.get(0);
        assertEquals("Test", insideAuditLog.getUser());
        assertEquals("INSIDE_AUDIT", insideAuditLog.getOriginSystem());
        assertTrue(insideAuditLog.isSuccess());
        assertNull(insideAuditLog.getMessageError());
        assertNotNull(insideAuditLog.getParameter());
        assertNotNull(insideAuditLog.getResponse());

    }

    @Test
    @WithMockUser(username = "Test", roles="ADMIN")
    public void checksIfTheLogWasSuccessfullySavedFromTheDivideMethod() throws InterruptedException {

        assertThrows(
                ArithmeticException.class,
                () -> insideAuditExampleService.divide(BigDecimal.valueOf(20L), BigDecimal.valueOf(0))
        );
        sleep(1000);

        List<InsideAuditLog> insideAuditLogs = insideAuditLogRepository.findAll();
        assertEquals(1, insideAuditLogs.size());
        InsideAuditLog insideAuditLog = insideAuditLogs.get(0);
        assertEquals("Test", insideAuditLog.getUser());
        assertEquals("INSIDE_AUDIT", insideAuditLog.getOriginSystem());
        assertFalse(insideAuditLog.isSuccess());
        assertNotNull(insideAuditLog.getMessageError());
        assertNotNull(insideAuditLog.getParameter());
        assertNull(insideAuditLog.getResponse());

    }

    @Test
    @WithMockUser(username = "Test", roles="ADMIN")
    public void checksIfTheLogWasSuccessfullySavedFromTheShowLogMethod() throws InterruptedException {

        insideAuditExampleService.showLog();
        sleep(1000);

        List<InsideAuditLog> insideAuditLogs = insideAuditLogRepository.findAll();
        assertEquals(1, insideAuditLogs.size());
        InsideAuditLog insideAuditLog = insideAuditLogs.get(0);
        assertEquals("Test", insideAuditLog.getUser());
        assertEquals("INSIDE_AUDIT", insideAuditLog.getOriginSystem());
        assertTrue(insideAuditLog.isSuccess());
        assertNull(insideAuditLog.getMessageError());
        assertNull(insideAuditLog.getParameter());
        assertNull(insideAuditLog.getResponse());

    }

}
