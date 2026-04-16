package com.fintrack.notification.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionEventConsumer {

    @KafkaListener(
        topics="transactions.events",
        groupId="notification-group"
    )
    public void consumeTransactionEvent(String message){
        log.info("Notification service received event: {}", message);
        //To add AWS SNS code
        log.info("Alert sent for transaction event: {}", message);
    }
}
