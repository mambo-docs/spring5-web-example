package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Async Execution Configuration
 *
 * @author mambo
 */
@EnableAsync
@ComponentScan({"com.example.demo.handler"})
@Configuration
public class AsyncConfig implements AsyncConfigurer {
    @Bean
    public TaskExecutor asyncThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(25);
        return taskExecutor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return asyncThreadPoolTaskExecutor();
    }
}
