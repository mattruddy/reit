package com.plaid.reit.repository;

import com.plaid.reit.model.Investor;
import com.plaid.reit.model.Transaction;
import com.plaid.reit.security.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Component
public class TransactionService {

    @Autowired private UserIdentity userIdentity;
    @Autowired private InvestorRepo investorRepo;

    @Transactional
    public void createTransaction(BigDecimal amount, String paymentScheduleId) {
        Investor investor = userIdentity.getEndUser().getInvestor();
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setCreatedAt(Timestamp.from(Instant.now()));
        transaction.setPaymentScheduleId(paymentScheduleId);
        transaction.setInvestor(investor);
        investor.getTransactions().add(transaction);
        investorRepo.save(investor);
    }

}
