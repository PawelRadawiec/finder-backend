package com.info.finder.service;

import com.info.finder.model.RegistrationResponse;
import com.info.finder.model.SystemEmail;
import com.info.finder.model.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

@Component
@Transactional
public class UserRegistrationService {

    private UserService userService;
    private SystemEmailService emailService;

    public UserRegistrationService(UserService userService, SystemEmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    public RegistrationResponse register(User user) {
        RegistrationResponse response = new RegistrationResponse();
        User userDb = userService.create(user);
        response.setUserId(user.getId());
        response.setMessage("Registration success");
        emailService.send(prepareSystemEmail(user), "registration", registrationContext(userDb));
        return response;
    }

    private SystemEmail prepareSystemEmail(User user) {
        SystemEmail email = new SystemEmail();
        email.setSendTo(user.getEmail());
        email.setSubject("Finder app - registration");
        email.setFrom("finder@app");
        return email;
    }

    private Context registrationContext(User user) {
        Context context = new Context();
        context.setVariable("header", String.format("Welcome %s %s", user.getFirstName(), user.getLastName()));
        context.setVariable("activationLink", String.format("http://localhost:4200/activation/%s", user.getId()));
        return context;
    }

}
