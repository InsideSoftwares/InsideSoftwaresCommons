package br.com.insidesoftwares.audit.aspect;

import br.com.insidesoftwares.audit.annotation.InsideAudit;
import br.com.insidesoftwares.audit.domain.dto.InsideAuditLogDTO;
import br.com.insidesoftwares.audit.service.InsideAuditService;
import br.com.insidesoftwares.commons.specification.InsideSoftwaresUserAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Aspect
@Service
@RequiredArgsConstructor
@Slf4j
public class InsideAuditAspectImplementation implements InsideAuditAspect {

    private final InsideAuditService insideAuditService;
    private final InsideSoftwaresUserAuthentication insideSoftwaresUserAuthentication;

    public Object execute(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Starting logging process");
        MethodSignature methodSignature = getMethodSignature(proceedingJoinPoint);

        String user = insideSoftwaresUserAuthentication.findUserAuthentication();
        String method = getMethodName(methodSignature);
        String description = getDescriptionMethod(methodSignature);
        List<Object> parameters = List.of(proceedingJoinPoint.getArgs());
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

    private String getMethodName(MethodSignature methodSignature) {
        log.debug("get Method Name");
        String method = methodSignature.getMethod().getAnnotation(InsideAudit.class).method();
        return StringUtils.isNotEmpty(method) ? method : methodSignature.getMethod().getName();
    }

    private String getDescriptionMethod(MethodSignature methodSignature) {
        log.debug("get Description Method");
        return methodSignature.getMethod().getAnnotation(InsideAudit.class).description();
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
