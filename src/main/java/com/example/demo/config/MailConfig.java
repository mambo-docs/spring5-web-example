package com.example.demo.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

@PropertySource({"classpath:/mail.properties"})
@Configuration
public class MailConfig implements EnvironmentAware {
    private static final String MAIL_HOST = "mail.host";
    private static final String MAIL_PORT = "mail.port";
    private static final String MAIL_PROTOCOL = "mail.protocol";
    private static final String MAIL_DEFAULT_ENCODING = "mail.default-encoding";
    private static final String MAIL_USERNAME = "mail.username";
    private static final String MAIL_PASSWORD = "mail.password";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_SSL_ENABLE = "mail.smtp.ssl.enable";
    private static final String MAIL_SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public MailSender mailSender() {
        String host = environment.getProperty(MAIL_HOST, String.class);
        int port = environment.getProperty(MAIL_PORT, Integer.class, -1);
        String protocol = environment.getProperty(MAIL_PROTOCOL, String.class);
        String defaultEncoding = environment.getProperty(MAIL_DEFAULT_ENCODING, String.class, StandardCharsets.UTF_8.displayName());
        String username = environment.getProperty(MAIL_USERNAME, String.class);
        String password = environment.getProperty(MAIL_PASSWORD, String.class);

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setProtocol(protocol);
        javaMailSender.setDefaultEncoding(defaultEncoding);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        boolean auth = environment.getProperty(MAIL_SMTP_AUTH, Boolean.class, false);
        boolean sslEnable = environment.getProperty(MAIL_SMTP_SSL_ENABLE, Boolean.class, false);
        boolean startTlsRequired = environment.getProperty(MAIL_SMTP_STARTTLS_REQUIRED, Boolean.class, false);
        boolean startTlsEnable = environment.getProperty(MAIL_SMTP_STARTTLS_ENABLE, Boolean.class, false);

        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty(MAIL_SMTP_AUTH, Boolean.toString(auth));
        javaMailProperties.setProperty(MAIL_SMTP_SSL_ENABLE, Boolean.toString(sslEnable));
        javaMailProperties.setProperty(MAIL_SMTP_STARTTLS_ENABLE, Boolean.toString(startTlsEnable));
        javaMailProperties.setProperty(MAIL_SMTP_STARTTLS_REQUIRED, Boolean.toString(startTlsRequired));
        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }
}
