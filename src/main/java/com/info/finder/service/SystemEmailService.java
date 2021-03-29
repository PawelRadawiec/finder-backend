package com.info.finder.service;

import com.info.finder.model.SystemEmail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SystemEmailService {

    private JavaMailSender mailSender;
    private TemplateEngine templateEngine;

    public SystemEmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void send(SystemEmail email, String templateName, Context context) {
        MimeMessageHelper message;
        MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        String htmlContent = this.templateEngine.process(templateName, context);
        try {
            message = prepareMessage(mimeMessage, email);
            message.setText(htmlContent, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        this.mailSender.send(mimeMessage);
        this.templateEngine.clearTemplateCache();
    }

    private MimeMessageHelper prepareMessage(MimeMessage mimeMessage, SystemEmail systemEmail) throws MessagingException {
        MimeMessageHelper message = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                "UTF-8"
        );
        message.setSubject(systemEmail.getSubject());
        message.setFrom(systemEmail.getFrom());
        message.setTo(systemEmail.getSendTo());
        return message;
    }

}
