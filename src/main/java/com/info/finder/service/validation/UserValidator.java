package com.info.finder.service.validation;


import com.info.finder.model.User;
import com.info.finder.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator extends GenericValidator implements Validator {

    private UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        validateRequired(user, errors);
        validateEmailUniqueness(user, errors);
        validateUsernameUniqueness(user, errors);
    }

    private void validateRequired(User user, Errors errors) {
        validateIfTrue(StringUtils.isEmpty(user.getFirstName()), "firstName", ValidationCode.REQUIRED.getValue(), errors);
        validateIfTrue(StringUtils.isEmpty(user.getLastName()), "lastName", ValidationCode.REQUIRED.getValue(), errors);
        validateIfTrue(StringUtils.isEmpty(user.getUsername()), "username", ValidationCode.REQUIRED.getValue(), errors);
        validateIfTrue(StringUtils.isEmpty(user.getEmail()), "email", ValidationCode.REQUIRED.getValue(), errors);
        validateIfTrue(StringUtils.isEmpty(user.getPassword()), "password", ValidationCode.REQUIRED.getValue(), errors);
    }

    private void validateEmailUniqueness(User user, Errors errors) {
        if (StringUtils.isEmpty(user.getEmail())) {
            return;
        }
        User dbUser = userRepository.findByEmail(user.getEmail());
        if (dbUser != null && user.getEmail().equals(dbUser.getEmail())) {
            validateIfTrue(StringUtils.isEmpty(user.getPassword()), "email", ValidationCode.UNIQUE.getValue(), errors);
        }
    }

    private void validateUsernameUniqueness(User user, Errors errors) {
        if (StringUtils.isEmpty(user.getUsername())) {
            return;
        }
        User dbUser = userRepository.findByUsername(user.getUsername());
        if (dbUser != null && user.getUsername().equals(dbUser.getUsername())) {
            validateIfTrue(StringUtils.isEmpty(user.getPassword()), "username", ValidationCode.UNIQUE.getValue(), errors);
        }
    }

}