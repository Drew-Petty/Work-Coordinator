package com.dp.workManager.validators;

import java.util.Calendar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.dp.workManager.annotation.CheckDates;
import com.dp.workManager.models.Job;

public class CheckDatesValidator implements ConstraintValidator<CheckDates, Job> {
	
	@Override
	public void initialize(CheckDates constraintAnnotation) {
	}

	@Override
	public boolean isValid(Job job, ConstraintValidatorContext context) {
		Calendar startDate = job.getStartDate();
		Calendar endDate = job.getEndDate();
		if(startDate.compareTo(endDate)>0) {
			return false;
		}else {
			return true;
		}
	}
}
