package com.fintrack.transaction.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String category;    // e.g. "Food", "Rent", "Salary"

    @Column(nullable = false)
    private String type;    //Income or expense

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
