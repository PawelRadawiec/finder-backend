package com.info.finder.service.validation.user;


import com.info.finder.model.User;
import com.info.finder.repository.UserRepository;
import com.info.finder.service.validation.GenericValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator extends GenericValidator implements Validator {

    private UserRepository userRepository;
    private SystemPasswordValidator systemPasswordValidator;

    public UserValidator(UserRepository userRepository, SystemPasswordValidator systemPasswordValidator) {
        this.userRepository = userRepository;
        this.systemPasswordValidator = systemPasswordValidator;
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
        validatePassword(user.getPassword(), errors);
    }

    private void validateRequired(User user, Errors errors) {
        validateIfTrue(StringUtils.isEmpty(user.getFirstName()), "firstName", ValidationCode.REQUIRED.getValue(), errors);
        validateIfTrue(StringUtils.isEmpty(user.getLastName()), "lastName", ValidationCode.REQUIRED.getValue(), errors);
        validateIfTrue(StringUtils.isEmpty(user.getUsername()), "username", ValidationCode.REQUIRED.getValue(), errors);
        validateIfTrue(StringUtils.isEmpty(user.getEmail()), "email", ValidationCode.REQUIRED.getValue(), errors);
    }

    private void validatePassword(String password, Errors errors) {
        if (StringUtils.isEmpty(password)) {
            validateIfTrue(true, "password", ValidationCode.REQUIRED.getValue(), errors);
        } else {
            PasswordValid passwordValid = systemPasswordValidator.validate(password);
            validateIfTrue(!passwordValid.isValid(), "password", passwordValid.getMessage(), errors);
        }

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
        User dbUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + user.getUsername()));
        if (dbUser != null && user.getUsername().equals(dbUser.getUsername())) {
            validateIfTrue(StringUtils.isEmpty(user.getPassword()), "username", ValidationCode.UNIQUE.getValue(), errors);
        }
    }

}
