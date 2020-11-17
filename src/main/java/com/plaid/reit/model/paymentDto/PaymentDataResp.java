package com.plaid.reit.model.paymentDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDataResp {

    @JsonProperty("payment_schedule_id")
    private String payment_schedule_id;

    public String getPayment_schedule_id() {
        return payment_schedule_id;
    }

    public void setPayment_schedule_id(String payment_schedule_id) {
        this.payment_schedule_id = payment_schedule_id;
    }
}
