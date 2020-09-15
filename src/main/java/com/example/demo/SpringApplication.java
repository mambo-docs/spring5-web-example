package com.example.demo;

import com.example.demo.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Locale;

public class SpringApplication {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        boolean active = applicationContext.isActive();
        System.out.println("applicationContext is active " + active);

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String applicationName = environment.getProperty("spring.application.name", String.class);
        System.out.println("spring.application.name : " + applicationName);

        String message = applicationContext.getMessage("argument.required", new Object[]{"message"}, Locale.getDefault());
        System.out.println(message);
    }
}
