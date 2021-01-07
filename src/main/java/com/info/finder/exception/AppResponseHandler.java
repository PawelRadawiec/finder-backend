package com.info.finder.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;
import java.util.*;

@ControllerAdvice
public class AppResponseHandler extends ResponseEntityExceptionHandler {

    @Override
    @NotNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request
    ) {
        ExceptionResponse response = new ExceptionResponse();
        ex.getBindingResult().getAllErrors().forEach(error -> appendResponse(error, response.getErrorMap()));

        response.setStatus(String.valueOf(status.value()));
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private void appendResponse(ObjectError error, Map<String, String> response) {
        response.put(((FieldError) error).getField(), error.getDefaultMessage());
    }

}
