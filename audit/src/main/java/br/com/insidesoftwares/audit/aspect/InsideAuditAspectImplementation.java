package br.com.insidesoftwares.audit.aspect;

import br.com.insidesoftwares.audit.annotation.InsideAudit;
import br.com.insidesoftwares.audit.domain.dto.InsideAuditLogDTO;
import br.com.insidesoftwares.audit.service.InsideAuditService;
import br.com.insidesoftwares.commons.specification.InsideSoftwaresUserAuthentication;
import br.com.insidesoftwares.commons.specification.LocaleService;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ConditionalOnProperty(prefix="insidesoftwares.audit", name = "enable", havingValue = "true")
@Aspect
@Service
@RequiredArgsConstructor
@Slf4j
public class InsideAuditAspectImplementation implements InsideAuditAspect {

    private final InsideAuditService insideAuditService;
    private final InsideSoftwaresUserAuthentication insideSoftwaresUserAuthentication;
    private final LocaleService localeService;

    public Object execute(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Starting logging process");
        MethodSignature methodSignature = getMethodSignature(proceedingJoinPoint);

        String user = insideSoftwaresUserAuthentication.findUserAuthentication();
        String method = getMethodName(methodSignature);
        String description = getDescriptionMethod(methodSignature);
        List<Object> parameters = getParameters(proceedingJoinPoint);
        Object response = null;
        LocalDateTime startDateChange = null;
        LocalDateTime endDateChange;

        boolean success = false;
        String messageError = null;
        try {
            startDateChange = LocalDateTime.now();
            log.debug("Calling the Method: {} - {}", method, startDateChange);
            response = proceedingJoinPoint.proceed();
            success = true;
        } catch (InsideSoftwaresException insideSoftwaresException) {
            log.error("Error Inside Audit Method: {} ", methodSignature.getMethod(), insideSoftwaresException);
            messageError = getMessageError(insideSoftwaresException);
            throw insideSoftwaresException;
        } catch (Throwable exception) {
            log.error("Error Inside Audit Method: {} ", methodSignature.getMethod(), exception);
            messageError = exception.getMessage();
            throw exception;
        } finally {
            endDateChange = LocalDateTime.now();
            log.debug("Method Return: {} - {}", method, endDateChange);
            saveLog(user, method, description, startDateChange, endDateChange, success, messageError, parameters, response);
            log.info("Ending logging process");
        }

        return response;
    }

    private MethodSignature getMethodSignature(ProceedingJoinPoint proceedingJoinPoint) {
        return (MethodSignature) proceedingJoinPoint.getSignature();
    }

    private String getMessageError(InsideSoftwaresException insideSoftwaresException) {
        log.debug("Init get Message Error");
        String message = StringUtils.EMPTY;
        try {
            message = localeService.getMessage(insideSoftwaresException.getCode(), insideSoftwaresException.getArgs());
        } catch (Exception exception) {
            log.error("Error get message", exception);
        } finally {
            log.debug("Ending get Message Error");
        }
        return String.format("""
                %s
                %s
                """, insideSoftwaresException, message);
    }
    
    private InsideAuditLogDTO createInsideAuditLogDTO(
        String user, 
        String method, 
        String description,
        LocalDateTime startDateChange,
        LocalDateTime endDateChange,
        boolean success,
        String messageError,
        List<Object> parameters,
        Object response
    ) {
        return InsideAuditLogDTO.builder()
                .user(user)
                .method(method)
                .description(description)
                .startDateChange(startDateChange)
                .endDateChange(endDateChange)
                .success(success)
                .messageError(messageError)
                .parameter(parameters)
                .response(response)
                .build();
    }

    private String getMethodName(final MethodSignature methodSignature) {
        log.debug("get Method Name");
        String method = methodSignature.getMethod().getAnnotation(InsideAudit.class).method();
        return StringUtils.isNotEmpty(method) ? method : methodSignature.getMethod().getName();
    }

    private String getDescriptionMethod(final MethodSignature methodSignature) {
        log.debug("get Description Method");
        return methodSignature.getMethod().getAnnotation(InsideAudit.class).description();
    }

    private List<Object> getParameters(final ProceedingJoinPoint proceedingJoinPoint) {
        return Arrays.stream(proceedingJoinPoint.getArgs()).toList();
    }

    private void saveLog(
            String user,
            String method,
            String description,
            LocalDateTime startDateChange,
            LocalDateTime endDateChange,
            boolean success,
            String messageError,
            List<Object> parameters,
            Object response
    ) {
        log.debug("Create InsideAuditLogDTO");
        InsideAuditLogDTO insideAuditLogDTO = createInsideAuditLogDTO(user, method, description, startDateChange,
                endDateChange, success, messageError, parameters, response);

        log.info("Save Method Execution Log");
        insideAuditService.saveAuditLog(insideAuditLogDTO);
    }
}
