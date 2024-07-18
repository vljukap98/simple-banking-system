package com.ljakovic.simplebankingsystem.transaction.repo;

import com.ljakovic.simplebankingsystem.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
