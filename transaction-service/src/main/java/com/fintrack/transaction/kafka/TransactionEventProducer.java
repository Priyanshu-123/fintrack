package com.fintrack.transaction.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionEventProducer {
    private static final String TOPIC = "transactions.events";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publishTransactionEvent(String message) {
        log.info("Publishing to Kafka topic {}: {}", TOPIC, message);
        kafkaTemplate.send(TOPIC, message);
    }
}
