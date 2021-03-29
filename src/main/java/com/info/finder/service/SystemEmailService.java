package com.info.finder.service;

import com.info.finder.model.SystemEmail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service
public class SystemEmailService {

    private JavaMailSender mailSender;
    private TemplateEngine templateEngine;

    public SystemEmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void send(SystemEmail email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getSendTo());
        message.setSubject(email.getSubject());
        message.setText(email.getMessage());
        mailSender.send(message);
    }

}
