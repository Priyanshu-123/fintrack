package com.fintrack.transaction.service;

import com.fintrack.transaction.dto.TransactionRequestDTO;
import com.fintrack.transaction.dto.TransactionResponseDTO;
import com.fintrack.transaction.kafka.TransactionEventProducer;
import com.fintrack.transaction.model.Transaction;
import com.fintrack.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository repository;
    private final TransactionEventProducer eventProducer;

    public TransactionResponseDTO createTransaction (TransactionRequestDTO request) {

        // Step 1 — map DTO → Entity
        Transaction transaction = Transaction.builder()
                .userId(request.getUserId())
                .amount(request.getAmount())
                .category(request.getCategory())
                .type(request.getType())
                .createdAt(LocalDateTime.now())
                .build();

        //Step 2 Save to DB
        Transaction saved = repository.save(transaction);
        log.info("Transaction created with Id: {}", saved.getId());

        //Step 3 Publish event to Kafka
        String event = String.format(
                "{\"transactionId\":%d, \"userId\":\"%s\", \"amount\":%.2f, \"type\":\"%s\",\"category\":\"%s\" }",
                saved.getId(), saved.getUserId(), saved.getAmount(), saved.getType(), saved.getCategory()
        );
        eventProducer.publishTransactionEvent(event);

        //Step 4: Returning Response DTO
        return TransactionResponseDTO.builder()
                .id(saved.getId())
                .userId(saved.getUserId())
                .amount(saved.getAmount())
                .category(saved.getCategory())
                .type(saved.getType())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    public List<TransactionResponseDTO> getAllTransactions() {
        return repository.findAll()
                .stream()
                .map(t -> TransactionResponseDTO.builder()
                        .id(t.getId())
                        .userId(t.getUserId())
                        .amount(t.getAmount())
                        .category(t.getCategory())
                        .type(t.getType())
                        .createdAt(t.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
