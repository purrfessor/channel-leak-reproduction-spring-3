package com.example.rabbitmqtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
@Profile("producer")
public class ProducerConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ProducerConfiguration.class);
    private final Integer THREADS = 20;

    @Bean
    public CommandLineRunner runner(RabbitTemplate rabbitTemplate) {
        return args -> {
            ExecutorService executorService = Executors.newFixedThreadPool(THREADS);
            for (int i = 0; i < THREADS; i++) {
                executorService.submit(new MessageSendingTask(rabbitTemplate));
            }
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                log.info("Shutdown initiated...");
                executorService.shutdown();
                try {
                    if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                        executorService.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    executorService.shutdownNow();
                }
                log.info("Shutdown complete.");
            }));
        };
    }
}
