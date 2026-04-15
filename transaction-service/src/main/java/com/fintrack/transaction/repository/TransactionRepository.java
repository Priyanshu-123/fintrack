package com.fintrack.transaction.repository;

import com.fintrack.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // JpaRepository already gives as a gift:
    // save(transaction)       → INSERT or UPDATE
    // findById(id)            → SELECT WHERE id = ?
    // findAll()               → SELECT * FROM transactions
    // deleteById(id)          → DELETE WHERE id = ?
    // count()                 → SELECT COUNT(*)

    // Custom query — Spring generates the SQL from the method name
    List<Transaction> findByUserId(String userId);
    List<Transaction> findByType(String type);
}
