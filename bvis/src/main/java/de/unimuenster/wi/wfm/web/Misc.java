package de.unimuenster.wi.wfm.web;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public class Misc {
	
	public static <T> String ValidateBean(T item) {
		//boolean valid = true;
		String output = "";
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(item);
        for (ConstraintViolation<T> violation : violations) {
        	output += violation.getMessage() + "; ";
        	//valid = false;
        }
        return output;
	}
	
}
