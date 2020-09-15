package com.example.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

/**
 * Web ServletContext Configuration Metadata
 *
 * @author mambo
 */
@Import({
    WebViewConfig.class
})
@ComponentScan({
    "com.example.demo.controller"
})
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**", "/css/**", "/js/**")
                .addResourceLocations("classpath:/static/images/", "classpath:/static/css/", "classpath:/static/js/")
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(1)));
    }
}