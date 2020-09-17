package com.example.demo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionEventHandler {
    private static final Logger LOG = LoggerFactory.getLogger(SessionEventHandler.class);

    @EventListener
    public void sessionCreated(SessionCreatedEvent createdEvent) {
        LOG.info("SessionCreatedEvent : {}", createdEvent.getSource());
    }

    @EventListener
    public void sessionDestroyed(SessionDestroyedEvent destroyedEvent) {
        LOG.info("SessionDestroyedEvent : {}", destroyedEvent.getSource());
    }
}
