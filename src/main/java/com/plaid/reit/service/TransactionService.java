package com.plaid.reit.service;

import com.plaid.reit.exception.ServiceException;
import com.plaid.reit.model.Investor;
import com.plaid.reit.model.Transaction;
import com.plaid.reit.model.dto.TransactionResp;
import com.plaid.reit.model.dto.TransferRequest;
import com.plaid.reit.repository.InvestorRepo;
import com.plaid.reit.repository.TransactionRepo;
import com.plaid.reit.security.UserIdentity;
import com.plaid.reit.util.Mapper;
import com.plaid.reit.util.TransactionType;
import com.plaid.reit.util.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionService {

    @Autowired private UserIdentity userIdentity;
    @Autowired private InvestorRepo investorRepo;
    @Autowired private TransactionRepo transactionRepo;

    @Transactional
    public Transaction createTransaction(TransferRequest transferRequest, String paymentScheduleId) {
        Investor investor = investorRepo.findByEndUserId(userIdentity.getEndUser().getId());

        if (investor == null) {
            throw new ServiceException("No Account Created");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(transferRequest.getAmount()));
        transaction.setCreatedAt(Timestamp.from(Instant.now()));
        transaction.setScheduleDate(Timestamp.from(transferRequest.getTransferDate().toInstant()));
        transaction.setPaymentScheduleId(paymentScheduleId);
        transaction.setInvestor(investor);
        transaction.setTransferStatus(TransferStatus.PENDING);

        if (investor.getTrossAccountNumber().equalsIgnoreCase(transferRequest.getTo())
                && investor.getLastFourAccountNumber().equalsIgnoreCase(transferRequest.getFrom())) {
            transaction.setTransactionType(TransactionType.DEBIT);
        } else if (investor.getTrossAccountNumber().equalsIgnoreCase(transferRequest.getFrom())
                && investor.getLastFourAccountNumber().equalsIgnoreCase(transferRequest.getTo())) {
            transaction.setTransactionType(TransactionType.CREDIT);
        } else {
            throw new ServiceException("Invalid Accounts");
        }

        investor.getTransactions().add(transaction);
        investorRepo.save(investor);
        return transaction;
    }

    @Transactional
    public void updateTransaction(long transactionId) {
        Transaction transaction = transactionRepo.getOne(transactionId);
        Investor investor = transaction.getInvestor();
        transaction.setTransferStatus(TransferStatus.COMPLETE);
        if (transaction.getTransactionType().equals(TransactionType.DEBIT)) {
            investor.setAmount(investor.getAmount().add(transaction.getAmount()));
        } else {
            investor.setAmount(investor.getAmount().subtract(transaction.getAmount()));
        }

        investorRepo.save(investor);
        transactionRepo.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionResp> getPendingTransactions() {
        List<Transaction> transactions = transactionRepo.findAllByTransferStatus(TransferStatus.PENDING);

        return transactions.stream()
                .map(Mapper::entityToDto)
                .collect(Collectors.toList());
    }

}
