package dev.icet.edp.validation;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class ValidationErrorsHelper {
	public Map<String, String> getValidationErrors (BindingResult result) {
		final Map<String, String> errors = new HashMap<>();

		result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return errors;
	}
}
