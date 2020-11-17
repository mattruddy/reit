package com.plaid.reit.repository;

import com.plaid.reit.model.Transaction;
import com.plaid.reit.util.TransferStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByInvestorId(long investorId);
    List<Transaction> findAllByTransferStatus(TransferStatus transferStatus);
}
