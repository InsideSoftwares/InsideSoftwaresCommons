package br.com.insidesoftwares.audit.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

public interface InsideAuditAspect {
    @Around(value = "@annotation(br.com.insidesoftwares.audit.annotation.InsideAudit)")
    Object execute(ProceedingJoinPoint proceedingJoinPoint) throws Throwable;
}
