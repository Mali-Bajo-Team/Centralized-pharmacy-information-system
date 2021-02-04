package com.pharmacy.cpis.util.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringIsNumericValidator implements ConstraintValidator<Numeric, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null)
			return false;
		return value.matches("[0-9]+");
	}

}
