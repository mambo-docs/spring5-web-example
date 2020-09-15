package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Root Application Configuration Metadata
 *
 * @author mambo
 */
@PropertySource({"classpath:application.properties"})
@Configuration
public class AppConfig {

}
