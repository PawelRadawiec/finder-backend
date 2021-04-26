package com.info.finder.controller;

import com.info.finder.model.LoginRequest;
import com.info.finder.model.MessageResponse;
import com.info.finder.model.User;
import com.info.finder.service.AuthorizationService;
import com.info.finder.service.UserRegistrationService;
import com.info.finder.service.UserService;
import com.info.finder.service.validation.authorization.LoginValidator;
import com.info.finder.service.validation.user.UserValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/start/")
public class AuthorizationController {

    private UserService userService;
    private UserRegistrationService registrationService;
    private UserValidator userValidator;
    private LoginValidator loginValidator;
    private AuthorizationService authorizationService;

    public AuthorizationController(UserService userService, UserRegistrationService registrationService, UserValidator userValidator, LoginValidator loginValidator, AuthorizationService authorizationService) {
        this.userService = userService;
        this.registrationService = registrationService;
        this.userValidator = userValidator;
        this.loginValidator = loginValidator;
        this.authorizationService = authorizationService;
    }

    @InitBinder("user")
    private void bindUserValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userValidator);
    }

    @InitBinder("login")
    private void bindLoginValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userValidator);
    }

    @PermitAll
    @PostMapping(value = "login")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authorizationService.authorization(request));
    }

    @PermitAll
    @PostMapping(value = "signup")
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        return ResponseEntity.ok(registrationService.register(user));
    }

    @PutMapping(value = "/activate/{id}")
    public ResponseEntity<MessageResponse> activate(@PathVariable String id) {
        return ResponseEntity.ok(userService.activate(id));
    }

}
