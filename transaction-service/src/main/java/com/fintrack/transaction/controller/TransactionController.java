package com.fintrack.transaction.controller;


import com.fintrack.transaction.dto.TransactionRequestDTO;
import com.fintrack.transaction.dto.TransactionResponseDTO;
import com.fintrack.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {

        log.info("Received request to create transaction for user: {}", transactionRequestDTO.getUserId());
        TransactionResponseDTO response = transactionService.createTransaction(transactionRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions(){

        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getAllTransactions());
    }
}
