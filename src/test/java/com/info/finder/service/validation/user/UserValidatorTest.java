package com.info.finder.service.validation.user;

import com.info.finder.helper.ErrorFieldHelper;
import com.info.finder.model.User;
import com.info.finder.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserValidatorTest {

    @Mock
    private UserRepository userRepository;

    private Errors errors;

    @InjectMocks
    private UserValidator userValidator;


    @Test
    @DisplayName("Expected Must be set for all user fields, except id")
    void testEmptyFields() {
        User user = new User();
        Errors errors = new BeanPropertyBindingResult(user, "user");
        userValidator.validate(user, errors);

        assertEquals("Must be set", ErrorFieldHelper.getFieldErrorCode("firstName", errors));
        assertEquals("Must be set", ErrorFieldHelper.getFieldErrorCode("lastName", errors));
        assertEquals("Must be set", ErrorFieldHelper.getFieldErrorCode("password", errors));
        assertEquals("Must be set", ErrorFieldHelper.getFieldErrorCode("email", errors));
        assertEquals("Must be set", ErrorFieldHelper.getFieldErrorCode("username", errors));
    }

    @Test
    @DisplayName("Email should be unique")
    void testEmailUniqueness() {
        User user = new User();
        user.setEmail("email@email.com");
        User userDb = new User();
        userDb.setEmail("email@email.com");
        Errors errors = new BeanPropertyBindingResult(user, "user");

        when(userRepository.findByEmail("email@email.com")).thenReturn(Optional.of(userDb));
        userValidator.validate(user, errors);
        assertEquals("Must be unique", ErrorFieldHelper.getFieldErrorCode("email", errors));
    }

    @Test
    @DisplayName("Username should be unique")
    void testUsernameUniqueness() {
        User user = new User();
        user.setUsername("User123");
        User userDb = new User();
        userDb.setEmail("User123");
        Errors errors = new BeanPropertyBindingResult(user, "username");

        when(userRepository.findByUsername("User123")).thenReturn(Optional.of(userDb));
        userValidator.validate(user, errors);
        assertEquals("Must be set", ErrorFieldHelper.getFieldErrorCode("username", errors));
    }


}