package com.example.demo.schedule;

import com.example.demo.service.ExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExampleScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(ExampleScheduler.class);

    private final ExampleService exampleService;

    public ExampleScheduler(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    /**
     * Execute every 1 minute from Asia/Seoul (GMT+0900)
     */
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void schedule() {
        exampleService.execute();
    }
}