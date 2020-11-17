package com.plaid.reit.model;

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

    @OneToOne
    @JoinColumn(name = "end_user_id")
    private EndUser endUser;

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

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
