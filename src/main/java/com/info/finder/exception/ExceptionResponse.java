package com.info.finder.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ExceptionResponse {

    private String status;
    private String message;
    private Map<String, String> errorMap;

    public Map<String, String> getErrorMap() {
        if (errorMap == null) {
            errorMap = new HashMap<>();
        }
        return errorMap;
    }
}
