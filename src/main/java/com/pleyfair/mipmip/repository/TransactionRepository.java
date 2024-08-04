package com.pleyfair.mipmip.repository;

import com.pleyfair.mipmip.model.dto.process.Transaction;
import com.pleyfair.mipmip.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByStatus(Status status);
}