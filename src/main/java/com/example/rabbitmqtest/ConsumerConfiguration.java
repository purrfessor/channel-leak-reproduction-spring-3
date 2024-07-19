package com.example.rabbitmqtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("consumer")
public class ConsumerConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ConsumerConfiguration.class);

    @RabbitListener(queues = "queue")
    public String receiveMessage(String message) {
        log.info(String.format("Received a message %s", message));
        return "Processed " + message;
    }
}
