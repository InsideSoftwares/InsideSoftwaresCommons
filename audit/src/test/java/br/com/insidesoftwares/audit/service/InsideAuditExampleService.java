package br.com.insidesoftwares.audit.service;

import br.com.insidesoftwares.audit.annotation.InsideAudit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class InsideAuditExampleService {

    @InsideAudit(description = "Method that performs the Multiplication of 2 values")
    public BigDecimal multiplication(BigDecimal valueOne, BigDecimal valueTwo) {
        return valueOne.multiply(valueTwo);
    }

    @InsideAudit(description = "Method that performs the Divide of 2 values")
    public BigDecimal divide(BigDecimal valueOne, BigDecimal valueTwo) {
        return valueOne.divide(valueTwo);
    }

    @InsideAudit(description = "Method show Log")
    public void showLog() {
        log.info("Show Log");
    }

}
