package com.fintrack.notification.kafka;


import com.fintrack.notification.service.SnsNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionEventConsumer {

    private final SnsNotificationService snsNotificationService;

    @KafkaListener(
        topics="transactions.events",
        groupId="notification-group"
    )
    public void consumeTransactionEvent(String message){
        log.info("Notification service received event: {}", message);
        snsNotificationService.sendNotification(message);
    }
}
