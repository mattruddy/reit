package com.plaid.reit.model.dto;

import com.plaid.reit.util.BankType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class InvestorResp {

    private Timestamp memberDate;
    private BigDecimal amount;
    private String bankName;
    private BankType bankType;
    private String trossAccount;
    private String lastFourAccountNumber;
    private List<DividendResp> dividends;
    private List<TransactionResp> transactions;

    public Timestamp getMemberDate() {
        return memberDate;
    }

    public void setMemberDate(Timestamp memberDate) {
        this.memberDate = memberDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<DividendResp> getDividends() {
        return dividends;
    }

    public void setDividends(List<DividendResp> dividends) {
        this.dividends = dividends;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTrossAccount() {
        return trossAccount;
    }

    public void setTrossAccount(String trossAccount) {
        this.trossAccount = trossAccount;
    }

    public BankType getBankType() {
        return bankType;
    }

    public void setBankType(BankType bankType) {
        this.bankType = bankType;
    }

    public String getLastFourAccountNumber() {
        return lastFourAccountNumber;
    }

    public List<TransactionResp> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionResp> transactions) {
        this.transactions = transactions;
    }

    public void setLastFourAccountNumber(String lastFourAccountNumber) {
        this.lastFourAccountNumber = lastFourAccountNumber;
    }
}
