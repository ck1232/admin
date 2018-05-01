package com.admin.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.admin.validator.impl.ValidDateValidator;

@Documented
//Note: We use here already a validator which we will add in a sec too
@Constraint(validatedBy = ValidDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate{
	// used to get later in the resource bundle the i18n text
    String message() default "{typeMismatch}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String dateFormat() default "dd/MM/yyyy";
    boolean nullable() default false;
	
}