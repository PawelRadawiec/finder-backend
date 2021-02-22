package com.info.finder.controller;

import com.info.finder.model.LoginRequest;
import com.info.finder.model.MessageResponse;
import com.info.finder.model.User;
import com.info.finder.service.AuthorizationService;
import com.info.finder.service.UserService;
import com.info.finder.service.validation.authorization.LoginValidator;
import com.info.finder.service.validation.user.UserValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/auth/")
public class AuthorizationController {

    private UserService userService;
    private UserValidator userValidator;
    private LoginValidator loginValidator;
    private AuthorizationService authorizationService;

    public AuthorizationController(UserService userService, UserValidator userValidator, LoginValidator loginValidator, AuthorizationService authorizationService) {
        this.userService = userService;
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

    @PostMapping(value = "login")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authorizationService.authorization(request));
    }

    @PostMapping(value = "signup")
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        userService.create(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
