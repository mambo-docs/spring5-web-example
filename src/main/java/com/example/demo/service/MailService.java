package com.example.demo.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Service
public class MailService {

    private static final Logger LOG = LoggerFactory.getLogger(MailService.class);
    private static final String DEFAULT_TEMPLATE = "template";

    private final JavaMailSender javaMailSender;
    private final Configuration configuration;

    public MailService(JavaMailSender javaMailSender,
                       Configuration configuration) {
        this.javaMailSender = javaMailSender;
        this.configuration = configuration;
    }

    public boolean sendMail(MimeMessage mimeMessage) {
        try {
            javaMailSender.send(mimeMessage);
        } catch (MailException e) {
            LOG.warn(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean sendMail(MimeMessage... mimeMessages) {
        try {
            javaMailSender.send(mimeMessages);
        } catch (MailException e) {
            LOG.warn(e.getMessage());
            return false;
        }
        return true;
    }

    public MimeMessage getMimeMessage(String to, String subject, Map<String, Object> variables) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setSentDate(new Date());
        mimeMessageHelper.setText(getDefaultTemplate(variables), true);
        return mimeMessage;
    }

    private String getDefaultTemplate(Map<String, Object> variables) throws IOException, TemplateException {
        return getTemplate(DEFAULT_TEMPLATE, variables);
    }

    private String getTemplate(String templateName, Map<String, Object> variables) throws IOException, TemplateException {
        String templatePath = String.format("%s.html", templateName);
        Template template = configuration.getTemplate(templatePath, Locale.getDefault(), StandardCharsets.UTF_8.name(), true, true);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, variables);
    }
}
