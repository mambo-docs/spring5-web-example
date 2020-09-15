package com.example.demo.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Root Application Configuration Metadata
 *
 * @author mambo
 */
@ComponentScan(basePackages = {"com.example.demo"})
@PropertySource({"classpath:application.properties"})
@Configuration
public class AppConfig {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/messages");
        return messageSource;
    }
}
