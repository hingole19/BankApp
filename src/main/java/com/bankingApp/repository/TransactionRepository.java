package com.bankingApp.repository;

import com.bankingApp.model.Transaction;
import com.bankingApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}