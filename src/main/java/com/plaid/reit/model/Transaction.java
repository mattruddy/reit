package com.plaid.reit.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private String paymentScheduleId;

    @ManyToOne
    @JoinColumn(name = "investor_id", nullable = false)
    private Investor investor;

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

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getPaymentScheduleId() {
        return paymentScheduleId;
    }

    public void setPaymentScheduleId(String paymentScheduleId) {
        this.paymentScheduleId = paymentScheduleId;
    }

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }
}
