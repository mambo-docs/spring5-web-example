package com.example.demo;

import com.example.demo.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApplication {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        boolean active = applicationContext.isActive();
        System.out.println("applicationContext is active " + active);
    }
}
