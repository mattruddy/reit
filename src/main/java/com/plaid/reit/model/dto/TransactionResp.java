package com.plaid.reit.model.dto;

import com.plaid.reit.util.TransactionType;
import com.plaid.reit.util.TransferStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionResp {

    private long id;
    private TransactionType transactionType;
    private BigDecimal amount;
    private Timestamp createdAt;
    private TransferStatus transferStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }
}
