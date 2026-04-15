package com.fintrack.transaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {

    @NotBlank(message = "userId is required.")
    private String userId;

    @NotNull(message = "amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotBlank(message = "Category must be specified")
    private String category;

    @NotBlank
    @Pattern(regexp = "INCOME|EXPENSE", message = "type must be income/expense")
    private String type;

}
