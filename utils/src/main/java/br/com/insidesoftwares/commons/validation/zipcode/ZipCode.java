package br.com.insidesoftwares.commons.validation.zipcode;

import br.com.insidesoftwares.commons.validation.zipcode.constraint.ZipCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ZipCodeValidator.class)
public @interface ZipCode {

    String message() default "ISV-ZIPCODE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
