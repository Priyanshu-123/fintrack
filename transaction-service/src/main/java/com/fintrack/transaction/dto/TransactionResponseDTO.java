package com.fintrack.transaction.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDTO {
    private Long id;
    private String userId;
    private Double amount;
    private String category;
    private String type;
    private LocalDateTime createdAt;
}
