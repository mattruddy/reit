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

    @Column(nullable = false)
    private String trossAccountNumber;

    @Column
    private BigDecimal amount = BigDecimal.ZERO;

    @Column
    private String profileId;

    @Column
    private String externalAccountId;

    @Column
    private String lastFourAccountNumber;

    @Column
    private String bankName;

    @Column
    private Boolean isLinked;

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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public Boolean getLinked() {
        return isLinked;
    }

    public void setLinked(Boolean linked) {
        isLinked = linked;
    }

    public void setDividends(List<Dividend> dividends) {
        this.dividends = dividends;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getExternalAccountId() {
        return externalAccountId;
    }

    public void setExternalAccountId(String externalAccountId) {
        this.externalAccountId = externalAccountId;
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

    public String getTrossAccountNumber() {
        return trossAccountNumber;
    }

    public void setTrossAccountNumber(String trossAccountNumber) {
        this.trossAccountNumber = trossAccountNumber;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
