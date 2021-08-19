package com.dp.workManager.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


import com.dp.workManager.annotation.MatchPassword;
import com.dp.workManager.models.User;

public class UserValidator implements ConstraintValidator<MatchPassword, User>{
	
	@Override
	public void initialize(MatchPassword constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(User user, ConstraintValidatorContext context) {
		if(user.getPasswordConfirmation()==null) {
			return true;
		}
		return user.getPassword().equals(user.getPasswordConfirmation());
	}
}
