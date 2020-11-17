package com.plaid.reit.model.paymentDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResp {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("data")
    private PaymentDataResp data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public PaymentDataResp getData() {
        return data;
    }

    public void setData(PaymentDataResp data) {
        this.data = data;
    }
}
