package com.plaid.reit.model.dto;

public class LinkTokenResp {
    String token;

    public LinkTokenResp(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
