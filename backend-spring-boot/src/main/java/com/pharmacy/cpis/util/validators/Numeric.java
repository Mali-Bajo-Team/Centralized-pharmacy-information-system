package com.pharmacy.cpis.util.validators;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = StringIsNumericValidator.class)
@Retention(RUNTIME)
@Target(FIELD)
public @interface Numeric {

	String message() default "Field must be numeric.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
