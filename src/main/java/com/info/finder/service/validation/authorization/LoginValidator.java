package com.info.finder.service.validation.authorization;

import com.info.finder.model.LoginRequest;
import com.info.finder.service.validation.GenericValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LoginValidator extends GenericValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return LoginRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        LoginRequest loginRequest = (LoginRequest) o;
        validateIfTrue(StringUtils.isEmpty(loginRequest.getUsername()), "username", ValidationCode.REQUIRED.getValue(), errors);
        validateIfTrue(StringUtils.isEmpty(loginRequest.getPassword()), "password", ValidationCode.REQUIRED.getValue(), errors);
    }
}
