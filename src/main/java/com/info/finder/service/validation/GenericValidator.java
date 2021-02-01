package com.info.finder.service.validation;

import org.springframework.validation.Errors;

public class GenericValidator  {

    public enum ValidationCode {
        REQUIRED("Must be set"), UNIQUE("Must be unique");

        private String value;

        ValidationCode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    protected void validateIfTrue(Boolean statement, String field, String code, Errors errors) {
        if (statement) {
            errors.rejectValue(field, code, code);
        }
    }

}
