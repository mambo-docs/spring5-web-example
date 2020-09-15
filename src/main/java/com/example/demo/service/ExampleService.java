package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {
    private static final Logger LOG = LoggerFactory.getLogger(ExampleService.class);

    public ExampleService() {}

    @Async
    public void execute() {
        LOG.debug("executed");
    }
}
