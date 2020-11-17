package com.plaid.reit.model.paymentDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConnectResp {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("session_id")
    private String session_id;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
