package com.plaid.reit.model;

import com.plaid.reit.util.BankType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "investor")
public class Investor implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private Timestamp memberDate;

    @Column
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(nullable = false)
    private String accountNumber;

    @Column
    private String profileId;

    @Column
    private String accountId;

    @Column
    private String lastFourAccountNumber;

    @Enumerated(EnumType.STRING)
    @Column
    private BankType bankType;

    @OneToOne
    @JoinColumn(name = "end_user_id")
    private EndUser endUser;

    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL)
    private List<Dividend> dividends = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public EndUser getEndUser() {
        return endUser;
    }

    public void setEndUser(EndUser endUser) {
        this.endUser = endUser;
    }

    public List<Dividend> getDividends() {
        return dividends;
    }

    public void setDividends(List<Dividend> dividends) {
        this.dividends = dividends;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getLastFourAccountNumber() {
        return lastFourAccountNumber;
    }

    public void setLastFourAccountNumber(String lastFourAccountNumber) {
        this.lastFourAccountNumber = lastFourAccountNumber;
    }

    public BankType getBankType() {
        return bankType;
    }

    public void setBankType(BankType bankType) {
        this.bankType = bankType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
