package com.plaid.reit.model.dto;

import com.plaid.client.response.Account;

import java.util.List;

public class ProfileResp {

    private String email;

    private String institution;

    private List<Account> account;

    private InvestorResp investor;

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }

    public InvestorResp getInvestor() {
        return investor;
    }

    public void setInvestor(InvestorResp investor) {
        this.investor = investor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }
}
