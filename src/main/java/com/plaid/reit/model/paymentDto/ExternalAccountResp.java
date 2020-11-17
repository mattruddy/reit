package com.plaid.reit.model.paymentDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalAccountResp {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("data")
    private ExternalAccountDataResp data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ExternalAccountDataResp getData() {
        return data;
    }

    public void setData(ExternalAccountDataResp data) {
        this.data = data;
    }
}
