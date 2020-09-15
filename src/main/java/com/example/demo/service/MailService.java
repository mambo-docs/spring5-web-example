package com.example.demo.service;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService implements EnvironmentAware {

    private final MailSender mailSender;
    private Environment environment;

    public MailService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
