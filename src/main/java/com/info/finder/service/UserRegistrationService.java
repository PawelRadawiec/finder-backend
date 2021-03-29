package com.info.finder.service;

import com.info.finder.model.RegistrationResponse;
import com.info.finder.model.SystemEmail;
import com.info.finder.model.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
        response.setMessage(getRegistrationMessage(user));
        SystemEmail systemEmail = new SystemEmail();
        systemEmail.setSendTo(user.getEmail());
        systemEmail.setSubject("Test subject");
        systemEmail.setMessage(getRegistrationMessage(user));
        emailService.send(systemEmail);
        return response;
    }

    private String getRegistrationMessage(User user) {
        return "Dear " + user.getFirstName() + " " + user.getLastName() + " click in verification link which you recieve";
    }

}
