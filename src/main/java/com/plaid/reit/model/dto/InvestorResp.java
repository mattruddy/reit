package com.plaid.reit.model.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class InvestorResp {

    private String accountNumber;
    private Timestamp memberDate;
    private BigDecimal amount;
    private List<DividendResp> dividends;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public List<DividendResp> getDividends() {
        return dividends;
    }

    public void setDividends(List<DividendResp> dividends) {
        this.dividends = dividends;
    }
}
