package com.plaid.reit.model.dto;

public class ProfileResp {

    private String email;

    private InvestorResp investor;

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

}
