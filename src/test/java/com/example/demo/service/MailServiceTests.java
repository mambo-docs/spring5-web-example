package com.example.demo.service;

import com.example.demo.config.AppConfig;
import com.example.demo.config.MailConfig;
import freemarker.template.TemplateException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MailConfig.class, MailService.class})
@ActiveProfiles({"test"})
@TestPropertySource(properties = { "mail.username= username@gmail.com", "mail.password= password" })
public class MailServiceTests {

    @Autowired
    private MailService mailService;

    @Test
    public void test() throws MessagingException, IOException, TemplateException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "Mambo");
        variables.put("content", "This mail is sent for testing.");
        MimeMessage mimeMessage = mailService.getMimeMessage("username@gmail.com", "Send test mail", variables);
        boolean status = mailService.sendMail(mimeMessage);
        Assert.assertTrue(status);
    }
}
