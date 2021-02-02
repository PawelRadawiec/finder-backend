package com.info.finder.helper;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class ErrorFieldHelper {

    public static String getFieldErrorCode(String field, Errors errors) {
        FieldError fieldError = errors.getFieldError(field);
        return fieldError != null ? fieldError.getCode() : null;
    }

}
