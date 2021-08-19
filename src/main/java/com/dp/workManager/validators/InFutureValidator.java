package com.dp.workManager.validators;

import java.util.Calendar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.dp.workManager.annotation.InFuture;

public class InFutureValidator implements ConstraintValidator<InFuture, Calendar> {

	@Override
	public void initialize(InFuture constraintAnnotation) {
	}

	@Override
	public boolean isValid(Calendar startDate, ConstraintValidatorContext context) {
		Calendar now = Calendar.getInstance();
		Calendar compare = new Calendar.Builder().setCalendarType("iso8601").setDate(now.get(1), now.get(2), now.get(5)).build();
		if(startDate.compareTo(compare)>=0) {
			return true;
		}else {
			return false;			
		}
	}
}
