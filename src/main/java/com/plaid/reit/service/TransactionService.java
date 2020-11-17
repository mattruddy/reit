package com.plaid.reit.service;

import com.plaid.reit.exception.ServiceException;
import com.plaid.reit.model.Investor;
import com.plaid.reit.model.Transaction;
import com.plaid.reit.model.dto.TransferRequest;
import com.plaid.reit.repository.InvestorRepo;
import com.plaid.reit.security.UserIdentity;
import com.plaid.reit.util.TransactionType;
import com.plaid.reit.util.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Component
public class TransactionService {

    @Autowired private UserIdentity userIdentity;
    @Autowired private InvestorRepo investorRepo;

    @Transactional
    public Transaction createTransaction(TransferRequest transferRequest) {
        Investor investor = investorRepo.findByEndUserId(userIdentity.getEndUser().getId());

        if (investor == null) {
            throw new ServiceException("No Account Created");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(transferRequest.getAmount()));
        transaction.setCreatedAt(Timestamp.from(Instant.now()));
        transaction.setScheduleDate(Timestamp.from(transferRequest.getTransferDate().toInstant()));
        transaction.setPaymentScheduleId(UUID.randomUUID().toString());
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

}
