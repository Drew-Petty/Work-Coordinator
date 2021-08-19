package com.dp.workManager.validators;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.dp.workManager.annotation.UniqueEmail;
import com.dp.workManager.models.User;
import com.dp.workManager.repos.UserRepo;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
	
	@Autowired
	private UserRepo userRepo;
	@Override
	public void initialize(UniqueEmail constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		if(userRepo == null) {
			return true;
		}
		Optional<User> optionalUser = userRepo.findByEmail(email);
		if(optionalUser.isPresent()) {
			return false;
		}else {
			return true;
		}
	}

}
