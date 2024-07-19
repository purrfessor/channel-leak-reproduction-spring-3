package com.example.rabbitmqtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class MessageSendingTask implements Runnable {
    private final static Logger log = LoggerFactory.getLogger(MessageSendingTask.class);

    private final RabbitTemplate rabbitTemplate;

    public MessageSendingTask(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            sendMessage();
        }
    }

    private void sendMessage() {
        try {
            Thread.sleep(100);
            String response = (String) rabbitTemplate.convertSendAndReceive("exchange", "routingKey", "message");
            log.info("Response: " + response);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
        }
    }
}