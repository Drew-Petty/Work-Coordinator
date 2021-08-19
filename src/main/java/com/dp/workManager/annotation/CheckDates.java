package com.dp.workManager.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.dp.workManager.validators.CheckDatesValidator;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {CheckDatesValidator.class})
public @interface CheckDates {
	String message();
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};
}
