package com.example.demo.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Session Management Configuration
 *
 * @author mambo
 */
@PropertySource({"classpath:/application.properties"})
//@EnableSpringHttpSession
@EnableRedisHttpSession
@Configuration
public class HttpSessionConfig implements EnvironmentAware {
    private Environment environment;

//    @Bean
//    public MapSessionRepository sessionRepository() {
//        return new MapSessionRepository(new ConcurrentHashMap<>());
//    }

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        String hostName = environment.getProperty("spring.redis.host", String.class, "localhost");
        String password = environment.getProperty("spring.redis.password", String.class);
        int port = environment.getProperty("spring.redis.port", Integer.class, 6379);

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
        RedisStandaloneConfiguration standaloneConfiguration = connectionFactory.getStandaloneConfiguration();
        standaloneConfiguration.setHostName(hostName);
        standaloneConfiguration.setPassword(password);
        standaloneConfiguration.setPort(port);
        return connectionFactory;
    }

    /**
     * for REST API.
     */
//    @Bean
//    public HttpSessionIdResolver httpSessionIdResolver() {
//        return HeaderHttpSessionIdResolver.xAuthToken();
//    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
